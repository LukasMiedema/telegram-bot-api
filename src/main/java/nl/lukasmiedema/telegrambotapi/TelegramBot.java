package nl.lukasmiedema.telegrambotapi;

import nl.lukasmiedema.telegrambotapi.handler.MessageEvent;
import nl.lukasmiedema.telegrambotapi.handler.MessageHandler;
import nl.lukasmiedema.telegrambotapi.handler.MessageType;
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
     * Constructs a new TelegramBot.
     * @param token the bot token
     * @param serverLocal the url to bind to for callbacks
     * @param serverRemote the remote view of this url (ie global IP)
     * @param cert location of the self-signed SSL certificate
     */
    private TelegramBot(String token, URI serverLocal, String serverRemote, File cert,
                        Map<MessageType, List<MessageHandler>> messageHandlers) {

        // Create the API
        this.api = new TelegramApi(token, serverLocal, this::callback);

        // Send the web hook
        this.api.setWebhook(serverRemote, cert);

        // Make a defensive copy
        this.messageHandlers = new LinkedHashMap<>(messageHandlers.size());
        for (Map.Entry<MessageType, List<MessageHandler>> e: messageHandlers.entrySet()) {

            // Use array list as we now know the size
            this.messageHandlers.put(e.getKey(), new ArrayList<>(e.getValue()));
        }

    }

    /**
     * Invoked by the API when a callback comes in
     * @param update
     */
    private void callback(TelegramUpdate update) {

        // Create a MessageEvent
        MessageEvent<?> event = new MessageEvent<>(update.getMessage(), false);
        dispatch(event);
    }

    /**
     * Dispatches a MessageEvent to all registered listeners.
     * @param event
     */
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

        private final String token, serverRemote;
        private final URI serverLocal;
        private final File cert;
        private final Map<MessageType, List<MessageHandler>> messageHandlers = new LinkedHashMap<>();

        public Builder(String token, String serverRemote, URI serverLocal, File cert) {
            this.token = token;
            this.serverRemote = serverRemote;
            this.serverLocal = serverLocal;
            this.cert = cert;
        }

        /**
         * Registers a MessageHandler. If the MessageHandler has already been registered, it will be registered
         * again and invoked twice during an event, unless the first handler cancels the event.
         * @param type
         * @param handler
         */
        public Builder register(MessageType type, MessageHandler handler) {
            List<MessageHandler> handlers = this.messageHandlers.get(type);
            if (handlers == null) {
                handlers = new LinkedList<MessageHandler>();
                this.messageHandlers.put(type, handlers);
            }
            handlers.add(handler);
            return this;
        }

        /**
         * Builds the TelegramBot
         * @return
         */
        public TelegramBot build() {
            if (token == null || serverLocal == null || serverRemote == null || cert == null) {
                throw new IllegalStateException("Not all properties have been set");
            }
            return new TelegramBot(token, serverLocal, serverRemote, cert, messageHandlers);
        }
    }
}
