package nl.lukasmiedema.telegrambotapi.telegram.media;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a TelegramDocument.
 * @author Lukas Miedema
 */
public class TelegramDocument extends TelegramFile {

    private final TelegramPicture thumbnail;
    private final String fileName;
    private final String mimeType;

    /**
     * Construct a new TelegramFile.
     * @param fileId
     * @param fileSize in bytes
     * @param thumbnail
     * @param fileName
     * @param mimeType
     */
    @JsonCreator
    public TelegramDocument(@JsonProperty("file_id") String fileId, @JsonProperty("file_size") int fileSize,
                            @JsonProperty("thumb") TelegramPicture thumbnail,
                            @JsonProperty("file_name") String fileName, @JsonProperty("mime_type") String mimeType) {
        super(fileId, fileSize);
        this.thumbnail = thumbnail;
        this.fileName = fileName;
        this.mimeType = mimeType;
    }

    public TelegramPicture getThumbnail() {
        return thumbnail;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mimeType;
    }
}
