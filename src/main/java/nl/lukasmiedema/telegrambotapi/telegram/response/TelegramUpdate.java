package nl.lukasmiedema.telegrambotapi.telegram.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramMessage;

/**
 * The format of updates received via the web hook.
 * @author Lukas Miedema
 */
public class TelegramUpdate {

    private final long updateId;
    private final TelegramMessage message;

    /**
     * Construct a new TelegramUpdate
     * @param updateId the update id
     * @param message the TelegramMessage
     */
    @JsonCreator
    public TelegramUpdate(@JsonProperty("update_id") long updateId, @JsonProperty("message") TelegramMessage message) {
        this.updateId = updateId;
        this.message = message;
    }

    /**
     * Returns the telegram update id
     * @return
     */
    public long getUpdateId() {
        return updateId;
    }

    /**
     * Returns the TelegramMessage associated with this update.
     * @return
     */
    public TelegramMessage getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "TelegramUpdate{" +
                "updateId=" + updateId +
                ", message=" + message +
                '}';
    }
}
