package nl.lukasmiedema.telegrambotapi.telegram.media;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Lukas Miedema
 */
public class TelegramVideo extends TelegramFile {

    private final int width;
    private final int height;
    private final int duration;
    private final TelegramPicture thumbnail;
    private final String mimeType;

    /**
     * Construct a new TelegramVideo.
     * @param fileId
     * @param fileSize
     * @param width
     * @param height
     * @param duration
     * @param thumbnail
     * @param mimeType
     */
    @JsonCreator
    public TelegramVideo(@JsonProperty("file_id") String fileId, @JsonProperty("file_size") int fileSize,
                         @JsonProperty("width") int width, @JsonProperty("height") int height,
                         @JsonProperty("duration") int duration, @JsonProperty("thumb") TelegramPicture thumbnail,
                         @JsonProperty("mime_type") String mimeType) {
        super(fileId, fileSize);
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.thumbnail = thumbnail;
        this.mimeType = mimeType;
    }

    /**
     * The width in pixels of the video.
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * The height in pixels of the video.
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * The duration in seconds.
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     * The thumbnail media.
     * @return
     */
    public TelegramPicture getThumbnail() {
        return thumbnail;
    }

    /**
     * The MIME type.
     * @return
     */
    public String getMimeType() {
        return mimeType;
    }
}
