package nl.lukasmiedema.telegrambotapi.telegram.media;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Telegram image.
 * @author Lukas Miedema
 */
public class TelegramPicture extends TelegramFile {

    private final int width;
    private final int height;

    /**
     * Constructs a new TelegramPicture.
     * @param fileId
     * @param fileSize
     * @param width
     * @param height
     */
    @JsonCreator
    public TelegramPicture(@JsonProperty("file_id") String fileId, @JsonProperty("file_size") int fileSize,
                           @JsonProperty("width") int width, @JsonProperty("height") int height) {
        super(fileId, fileSize);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
