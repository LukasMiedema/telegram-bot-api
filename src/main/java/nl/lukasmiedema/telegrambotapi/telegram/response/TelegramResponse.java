package nl.lukasmiedema.telegrambotapi.telegram.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Telegram API Response
 * @author Lukas Miedema
 */
public class TelegramResponse<R> {

    private final boolean ok;
    private final R result;

    @JsonCreator
    public TelegramResponse(@JsonProperty("ok") boolean ok,
                            @JsonProperty(value = "result", required = false) R result) {
        this.ok = ok;
        this.result = result;
    }

    /**
     * True if the request was successful, false otherwise.
     * @return
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * The result, if any
     * @return
     */
    public R getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "TelegramResponse{" +
                "ok=" + ok +
                ", result=" + result +
                '}';
    }
}
