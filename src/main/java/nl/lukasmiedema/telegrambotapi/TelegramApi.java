package nl.lukasmiedema.telegrambotapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.sun.net.httpserver.HttpServer;
import nl.lukasmiedema.telegrambotapi.telegram.TelegramUser;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramMessage;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramParseMode;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramTextMessage;
import nl.lukasmiedema.telegrambotapi.telegram.response.*;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.net.URI;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * @author Lukas Miedema
 */
@Singleton
@Path("/")
public class TelegramApi {

    // The API url
    public static final String API_URL = "https://api.telegram.org/bot";

    private final WebTarget api;
    private final Client client;
    private final HttpServer server;
    private final Consumer<TelegramUpdate> callback;

    /**
     * Construct a new TelegramApi with the provided bot token
     * @param token    the bot token
     * @param server   the uri to bind to for callbacks
     * @param callback the callback to invoke when a request to the webhook has been made
     *                 note that the callback may be invoked multiple times from different threads at the same time.
     */
    public TelegramApi(String token, URI server, Consumer<TelegramUpdate> callback) {

        // Set the callback
        this.callback = callback;

        // Create JSON provider
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Create a rest client with JSON support
        this.client = ClientBuilder.newBuilder().register(MultiPartFeature.class).register(provider).build();

        // Create target
        this.api = client.target(API_URL + token);

        // Start the server
        System.out.println("Expecting callback on " + server);
        ResourceConfig config = new ResourceConfig();
        config.register(this);

        this.server = JdkHttpServerFactory.createHttpServer(server, config);
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
    public TelegramResponse<TelegramTextMessage>
    sendTextMessage(long chatId, String text, TelegramParseMode mode, boolean disableWebPagePreview, boolean isReplyTo,
                    int replyToMessage) {

        // Create the message
        ObjectNode message = new ObjectNode(JsonNodeFactory.instance);
        message.put("chat_id", chatId);
        message.put("text", text);
        message.put("parse_mode", mode.toString());
        message.put("disable_web_page_preview", disableWebPagePreview);
        if (isReplyTo) {
            message.put("reply_to_message_id", replyToMessage);
        }

        return (TelegramResponse<TelegramTextMessage>) requestResource("sendMessage", message, TelegramMessage.GENERIC_TYPE);
    }

    /**
     * Sends a text message
     * Shortcut for return this.sendTextMessage(chatId, text, TelegramParseMode.MARKDOWN, false, false, 0);<br>
     * See {@link #sendTextMessage(long, String, TelegramParseMode, boolean, boolean, int)}
     * @param chatId the chat to send it to
     * @param text the text of the message
     * @return
     */
    public TelegramResponse<TelegramTextMessage> sendTextMessage(long chatId, String text) {
        return this.sendTextMessage(chatId, text, TelegramParseMode.MARKDOWN, false, false, 0);
    }

    /**
     * Sets the webhook
     * @return
     */
    public String setWebhook(String url, File cert) {

        // Here we have to do things a bit differently because of the file
        MultiPart entity = new MultiPart().bodyPart(new FileDataBodyPart("certificate", cert));
        entity.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
        System.out.println("Type: " + entity.getMediaType());
        return api.path("setWebhook").queryParam("url", url).request().post(Entity.entity(entity, entity.getMediaType()), String.class);
    }


    /**
     * Calls the requested url and returns the expected data
     * @param method
     * @param requestEntity the data to submit
     * @param responseType the expected return type
     * @return
     */
    private<T> T requestResource(String method, Object requestEntity, GenericType<T> responseType) {
        return api.path(method).request().post(Entity.json(requestEntity), responseType);
    }

    /**
     * Handles the callback. Very scary
     * @param update
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void onCallback(TelegramUpdate update) {
        try {
            // Invoke the callback
            this.callback.accept(update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
