package nl.lukasmiedema.telegrambotapi.telegram.media;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a sticker.
 * @author Lukas Miedema
 */
public class TelegramSticker extends TelegramFile {

    private final TelegramPicture thumbnail;
    private final int width;
    private final int height;

    @JsonCreator
    public TelegramSticker(@JsonProperty("file_id") String fileId, @JsonProperty("file_size") int fileSize,
                           @JsonProperty("width") int width, @JsonProperty("height") int height,
                           @JsonProperty("thumb") TelegramPicture thumbnail) {
        super(fileId, fileSize);
        this.thumbnail = thumbnail;
        this.width = width;
        this.height = height;
    }

    public TelegramPicture getThumbnail() {
        return thumbnail;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
