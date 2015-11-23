package nl.lukasmiedema.telegrambotapi;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nl.lukasmiedema.telegrambotapi.TelegramApi;
import nl.lukasmiedema.telegrambotapi.telegram.TelegramUser;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramMessage;
import nl.lukasmiedema.telegrambotapi.telegram.response.TelegramResponse;
import nl.lukasmiedema.telegrambotapi.telegram.response.TelegramUpdate;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * TelegramPollingApi uses long polling to obtain updates. Note that this method may prove to be inefficient
 * if your bot receives few messages. In almost all use cases, the {@link nl.lukasmiedema.telegrambotapi.TelegramWebhookApi}
 * is prefered, but it is more work to setup. Both implementations offer the same functionality.
 * @author Lukas Miedema
 */
public class TelegramPollingApi extends TelegramApi {

    /**
     * Generic type for deserialization with Jackson
     */
    public final static GenericType<TelegramResponse<TelegramUpdate[]>> UPDATE_LIST_TYPE = new GenericType<TelegramResponse<TelegramUpdate[]>>(){};

    // How long should we poll for updates before trying again (in seconds)?
    private final static int LONG_POLL_TIMEOUT = 120;

    /**
     * Construct a new TelegramPollingApi
     * @param token     the bot token
     * @param callback  the callback
     */
    public TelegramPollingApi(String token, Consumer<TelegramUpdate> callback) {
        super(token);

        // Unregister webhooks
        System.out.println(this.setWebhook(null, null));

        // Create a thread
        Thread updateThread = new Thread(() -> {

            // The update id
            long lowestUpdateId = 0;

            while (true) {
                TelegramResponse<TelegramUpdate[]> updates = getUpdates(LONG_POLL_TIMEOUT, lowestUpdateId);
                System.out.println("Received update: " + updates.isOk());
                if (updates.isOk() && updates.getResult().length != 0) {

                    TelegramUpdate[] res = updates.getResult();

                    // Handle all updates
                    Arrays.stream(res).forEach(callback);

                    // Set lowestUpdateId
                    lowestUpdateId = res[res.length - 1].getUpdateId() + 1;

                }
            }

        }, "telegram-bot-api poll thread");
        updateThread.start();
    }

    /**
     * Polls for updates from the server
     * @param timeout   Timeouts in seconds for long polling. 0 is short polling (the server won't block for updates,
     *                  but will return updates that were already available).
     * @param lowestUpdateId offset in the telgram api. The lowest update id we're interested in.
     *                       When 0, it will not be included in the request.
     *                       The telegram server will mark all updates with a lower id as read and not send them.
     * @return
     */
    public TelegramResponse<TelegramUpdate[]> getUpdates(int timeout, long lowestUpdateId) {

        // Create JSON object
        ObjectNode message = new ObjectNode(JsonNodeFactory.instance);
        message.put("timeout", timeout);
        if (lowestUpdateId != 0) {
            message.put("offset", lowestUpdateId);
        }

        // Poll for updates
        return requestResource("getUpdates", message, UPDATE_LIST_TYPE);
    }
}
