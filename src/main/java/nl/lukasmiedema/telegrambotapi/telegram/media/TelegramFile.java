package nl.lukasmiedema.telegrambotapi.telegram.media;

/**
 * Base class for all Telegram files.
 * @author Lukas Miedema
 */
public abstract class TelegramFile implements TelegramMedia {

    private final String fileId;
    private final int fileSize;

    /**
     * Construct a new TelegramMedia instance.
     * @param fileId the telegram file id
     * @param fileSize the file size in bytes.
     */
    public TelegramFile(String fileId, int fileSize) {
        this.fileId = fileId;
        this.fileSize = fileSize;
    }

    /**
     * Returns the telegram file id.
     * @return
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * Returns the file size (in bytes).
     * @return
     */
    public int getFileSize() {
        return fileSize;
    }
}
