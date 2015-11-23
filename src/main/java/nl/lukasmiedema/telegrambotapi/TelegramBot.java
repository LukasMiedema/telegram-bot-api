package nl.lukasmiedema.telegrambotapi;

import nl.lukasmiedema.telegrambotapi.handler.MessageEvent;
import nl.lukasmiedema.telegrambotapi.handler.MessageHandler;
import nl.lukasmiedema.telegrambotapi.handler.MessageType;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramMediaMessage;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramMessage;
import nl.lukasmiedema.telegrambotapi.telegram.response.TelegramUpdate;

import java.io.File;
import java.net.URI;
import java.util.*;

/**
 * @author Lukas Miedema
 */
public class TelegramBot {

    private final TelegramApi api;
    private final Map<MessageType, List<MessageHandler>> messageHandlers;

    /**
     * Constructs a new TelegramBot with web hooks.
     * @param token the bot token
     * @param serverLocal the url to bind to for callbacks
     * @param serverRemote the remote view of this url (ie global IP)
     * @param cert location of the self-signed SSL certificate
     */
    private TelegramBot(String token, URI serverLocal, String serverRemote, File cert,
                        Map<MessageType, List<MessageHandler>> messageHandlers) {

        this.messageHandlers = messageHandlers;

        // Create the API
        this.api = new TelegramWebhookApi(token, serverLocal, this::callback);

        // Send the web hook
        api.setWebhook(serverRemote, cert);
    }

    /**
     * Constructs a new TelegramBot with long polling.
     * @param token the bot token
     */
    private TelegramBot(String token, Map<MessageType, List<MessageHandler>> messageHandlers) {

        this.messageHandlers = messageHandlers;

        // Create the API
        this.api = new TelegramPollingApi(token, this::callback);
    }

    /**
     * Constructor without builder. Generally you wanna use the builder.
     * @param api
     * @param messageHandlers
     */
    public TelegramBot(TelegramApi api, Map<MessageType, List<MessageHandler>> messageHandlers) {
        this.api = api;
        this.messageHandlers = messageHandlers;
    }


    /**
     * Invoked by the API when a callback comes in
     * @param update
     */
    public void callback(TelegramUpdate update) {

        // Create a MessageEvent
        MessageEvent<?> event = new MessageEvent<>(update.getMessage(), false);
        dispatch(event);
    }

    /**
     * Dispatches a MessageEvent to all registered listeners.
     * @param event
     */
    @SuppressWarnings("unchecked")
    public void dispatch(MessageEvent<?> event) {
        for (Map.Entry<MessageType, List<MessageHandler>> e: messageHandlers.entrySet()) {

            // Check the type
            if (e.getKey().typeOf(event.getMessage())) {

                // Send the event to all handlers
                for (MessageHandler h: e.getValue()) {

                    // Stop as soon as the event is cancelled
                    if (event.isCancelled()) {
                        return;
                    }

                    h.handle(event, this.api);
                }
            }
        }
    }

    /**
     * Returns the TelegramApi instance. This instance can be used to send messages to chats.
     * @return
     */
    public TelegramApi getAPI() {
        return this.api;
    }

    /**
     * Builder for a Telegram Bot
     */
    public static class Builder {

        private final String token;

        // Web hooks related
        private String serverRemote;
        private URI serverLocal;
        private File cert;
        private boolean webhooks = false; // true for webhooks, false for long polling

        private final Map<MessageType, List<MessageHandler>> messageHandlers = new LinkedHashMap<>();

        /**
         * Constructs a new Builder
         * @param botToken the API token of the bot. This can be obtained from the "BotFather" Telegram bot.
         */
        public Builder(String botToken) {
            this.token = botToken;
        }

        /**
         * Registers a MessageHandler. If the MessageHandler has already been registered, it will be registered
         * again and invoked twice during an event, unless the first handler cancels the event.
         * @param type
         * @param handler
         */
        public <T extends TelegramMessage> Builder register(MessageType<T> type, MessageHandler<T> handler) {
            List<MessageHandler> handlers = this.messageHandlers.get(type);
            if (handlers == null) {
                handlers = new LinkedList<>();
                this.messageHandlers.put(type, handlers);
            }
            handlers.add(handler);
            return this;
        }

        /**
         * Enables webhooks instead of long polling (which is default)
         * @param serverRemote the callback url to send to the telegram servers
         * @param serverLocal the callback url to bind to
         * @param cert the certificate (can be self-signed)
         * @return
         */
        public Builder webhooks(String serverRemote, URI serverLocal, File cert) {
            this.serverRemote = serverRemote;
            this.serverLocal = serverLocal;
            this.cert = cert;
            webhooks = true;
            return this;
        }

        /**
         * Builds the TelegramBot
         * @return
         */
        public TelegramBot build() {

            // Make a defensive copy of the messages
            Map<MessageType, List<MessageHandler>> handlersCopy = new LinkedHashMap<>(messageHandlers.size());
            for (Map.Entry<MessageType, List<MessageHandler>> e: messageHandlers.entrySet()) {
                handlersCopy.put(e.getKey(), new ArrayList<>(e.getValue()));
            }

            // Make a TelegramBot
            if (webhooks) {
                return new TelegramBot(token, serverLocal, serverRemote, cert, handlersCopy);
            } else {
                return new TelegramBot(token, handlersCopy);
            }
        }
    }
}
