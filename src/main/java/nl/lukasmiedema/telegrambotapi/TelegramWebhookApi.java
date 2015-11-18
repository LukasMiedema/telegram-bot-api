package nl.lukasmiedema.telegrambotapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.sun.net.httpserver.HttpServer;
import nl.lukasmiedema.telegrambotapi.telegram.response.TelegramUpdate;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.net.URI;
import java.util.function.Consumer;

/**
 * TelegramApi using webhooks for callbacks. It sets up an internal web server.
 * Instances should be constructed via {@link nl.lukasmiedema.telegrambotapi.TelegramBot.Builder}
 * @author Lukas Miedema
 */
@Singleton @Path("/")
public class TelegramWebhookApi extends TelegramApi {

    private final HttpServer server;
    private final Consumer<TelegramUpdate> callback;

    /**
     * Construct a new TelegramApi with the provided bot token
     * @param token    the bot token
     * @param server   the uri to bind to for callbacks
     * @param callback the callback to invoke when a request to the webhook has been made
     *                 note that the callback may be invoked multiple times from different threads at the same time.
     */
    public TelegramWebhookApi(String token, URI server, Consumer<TelegramUpdate> callback) {
        super(token);

        this.callback = callback;

        // Start the server for web hooks
        System.out.println("Expecting callback on " + server);
        ResourceConfig config = new ResourceConfig();
        config.register(this);

        this.server = JdkHttpServerFactory.createHttpServer(server, config);
    }

    /**
     * Handles calls to the webhook API.
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
