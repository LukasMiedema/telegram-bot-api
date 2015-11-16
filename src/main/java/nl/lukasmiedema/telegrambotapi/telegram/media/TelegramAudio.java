package nl.lukasmiedema.telegrambotapi.telegram.media;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains audio metadata, but not the actual file.
 * @author Lukas Miedema
 */
public class TelegramAudio extends TelegramFile {

    private final int duration;
    private final String performer;
    private final String title;
    private final String mimeType;

    /**
     * Construct a new TelegramMedia instance.
     * @param fileId the telegram file id
     * @param fileSize the file size in bytes
     * @param duration the duration in seconds
     * @param performer the performer
     * @param title the title
     * @param mimeType the mime type
     */
    @JsonCreator
    public TelegramAudio(@JsonProperty("file_id") String fileId, @JsonProperty("file_size") int fileSize,
                         @JsonProperty("duration") int duration, @JsonProperty("performer") String performer,
                         @JsonProperty("title") String title, @JsonProperty("mime_type") String mimeType) {
        super(fileId, fileSize);
        this.duration = duration;
        this.performer = performer;
        this.title = title;
        this.mimeType = mimeType;
    }

    /**
     * Returns the duration in seconds as reported by the sender.
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the performer, or null.
     * @return
     */
    public String getPerformer() {
        return performer;
    }

    /**
     * Returns the title, or null.
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the MIME type, or null.
     * @return
     */
    public String getMimeType() {
        return mimeType;
    }
}
