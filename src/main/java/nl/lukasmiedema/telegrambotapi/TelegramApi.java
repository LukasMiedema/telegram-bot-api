package nl.lukasmiedema.telegrambotapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import nl.lukasmiedema.telegrambotapi.telegram.TelegramUser;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramMessage;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramParseMode;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramTextMessage;
import nl.lukasmiedema.telegrambotapi.telegram.response.*;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.File;

/**
 * Via a TelegramApi instance Telegram API methods can be invoked.
 * @author Lukas Miedema
 */
public abstract class TelegramApi {

    // The API url
    public static final String API_URL = "https://api.telegram.org/bot";

    private final WebTarget api;

    /**
     * Construct a new TelegramApi with the provided bot token
     * @param token    the bot token
     */
    public TelegramApi(String token) {

        // Create JSON provider
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Create a rest client with JSON support
        Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).register(provider).build();
        //client.register(new LoggingFilter(Logger.getAnonymousLogger(), true));

        // Create target
        this.api = client.target(API_URL + token);
    }

    /**
     * A simple method for testing your bot's auth token. Requires no parameters.
     * Returns basic information about the bot in form of a TelegramUser object.
     * @return
     */
    public TelegramResponse<TelegramUser> getMe() {
        return requestResource("getMe", null, TelegramUser.GENERIC_TYPE);
    }


    /**
     * Sends a text message
     * @param chatId the chat to send it to
     * @param text the text
     * @param mode the parse mode
     * @param disableWebPagePreview true to load a web page preview for the first url in the text
     * @param isReplyTo is this a reply to a message? If so, provide a replyToMessage
     * @param replyToMessage the id of the message to quote. If isReplyTo is false, this is ignored.
     * @return
     */
    @SuppressWarnings("unchecked")
    public TelegramResponse<TelegramTextMessage>
    sendText(long chatId, String text, TelegramParseMode mode, boolean disableWebPagePreview, boolean isReplyTo,
             long replyToMessage) {

        // Create the message
        ObjectNode message = new ObjectNode(JsonNodeFactory.instance);
        message.put("chat_id", chatId);
        message.put("text", text);
        if (mode.toString() != null) {
            message.put("parse_mode", mode.toString());
        }
        message.put("disable_web_page_preview", disableWebPagePreview);
        if (isReplyTo) {
            message.put("reply_to_message_id", replyToMessage);
        }

        return (TelegramResponse<TelegramTextMessage>) requestResource("sendMessage", message, TelegramMessage.GENERIC_TYPE);
    }

    /**
     * Sets the webhook. Note that this method is automatically invoked as part of the web hook build process.
     * @param url the url the telegram server should make requests to. Leave null to unregister
     * @param cert the certificate to use. Leave null if you have a normal (not self-signed) certificate and you do
     *             not wish to use it for certificate pinning.
     * @return
     */
    public String setWebhook(String url, File cert) {

        // Check if we should sent a certificate
        if (cert == null) {

            // Send the url if its set, otherwise send an empty object
            ObjectNode message = new ObjectNode(JsonNodeFactory.instance);
            if (url != null ) {
                message.put("url", url);
            }
            return api.path("setWebhook").request().post(Entity.json(message), String.class);

        } else {

            // Here we have to do things a bit differently because of the file
            MultiPart entity = new MultiPart().bodyPart(new FileDataBodyPart("certificate", cert));
            entity.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
            return api.path("setWebhook").queryParam("url", url).request().post(Entity.entity(entity, entity.getMediaType()), String.class);
        }

    }

    /**
     * Sends a text message
     * Shortcut for return this.sendText(chatId, text, TelegramParseMode.NORMAL, false, false, 0);<br>
     * See {@link #sendText(long, String, TelegramParseMode, boolean, boolean, long)}
     * @param chatId the chat to send it to
     * @param text the text of the message
     * @return
     */
    public TelegramResponse<TelegramTextMessage> sendText(long chatId, String text) {
        return this.sendText(chatId, text, TelegramParseMode.NORMAL, false, false, 0);
    }

    /**
     * Sends a text message as a reply to someone else
     * Shortcut for return this.sendText(chatId, text, TelegramParseMode.NORMAL, false, true, replyToMessage);<br>
     * See {@link #sendText(long, String, TelegramParseMode, boolean, boolean, long)}
     * @param chatId the chat to send it to
     * @param text the text of the message
     * @param replyToMessage the ID of the message to reply to
     * @return
     */
    public TelegramResponse<TelegramTextMessage> sendText(long chatId, String text, long replyToMessage) {
        return this.sendText(chatId, text, TelegramParseMode.NORMAL, false, true, replyToMessage);
    }


    /**
     * Calls the requested url and returns the expected data
     * @param method
     * @param requestEntity the data to submit
     * @param responseType the expected return type
     * @return
     */
    protected<T> T requestResource(String method, Object requestEntity, GenericType<T> responseType) {
        return api.path(method).request().post(Entity.json(requestEntity), responseType);
    }
}
