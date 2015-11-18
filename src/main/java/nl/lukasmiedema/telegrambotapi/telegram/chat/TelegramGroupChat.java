package nl.lukasmiedema.telegrambotapi.telegram.chat;

/**
 * Represents a TelegramChat with GROUP as type.
 * @author Lukas Miedema
 */
public class TelegramGroupChat extends TelegramChat {

    private final String title;

    /**
     * Construct a new TelegramGroupChat
     * @param id the telegram chat id.
     * @param title
     */
    public TelegramGroupChat(long id, String title) {
        super(id);
        this.title = title;
    }

    /**
     * Returns GROUP.
     * @return
     */
    @Override
    public TelegramChatType getType() {
        return TelegramChatType.GROUP;
    }

    /**
     * Returns the title.
     * @return
     */
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "TelegramGroupChat{" +
                "title='" + title + '\'' +
                "} " + super.toString();
    }
}
