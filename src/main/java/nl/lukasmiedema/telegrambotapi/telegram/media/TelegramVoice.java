package nl.lukasmiedema.telegrambotapi.telegram.media;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Lukas Miedema
 */
public class TelegramVoice extends TelegramFile {

    private final int duration;
    private final String mimeType;

    /**
     * Construct new TelegramVoice
     * @param fileId
     * @param fileSize
     * @param duration in seconds
     */
    @JsonCreator
    public TelegramVoice(@JsonProperty("file_id") String fileId, @JsonProperty("file_size") int fileSize,
                         @JsonProperty("duration") int duration, @JsonProperty("mime_type") String mimeType) {
        super(fileId, fileSize);
        this.duration = duration;
        this.mimeType = mimeType;
    }

    /**
     * Returns duration in seconds.
     * @return
     */
    public int getDuration() {
        return duration;
    }

    public String getMimeType() {
        return mimeType;
    }
}
