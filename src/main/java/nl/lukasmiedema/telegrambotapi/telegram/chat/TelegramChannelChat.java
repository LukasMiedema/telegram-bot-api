package nl.lukasmiedema.telegrambotapi.telegram.chat;

/**
 * Not sure what the point is of a bot in a channel, but anyways:
 * a TelegramChat with CHANNEL as type.
 * @author Lukas Miedema
 */
public class TelegramChannelChat extends TelegramChat {

    private final String title;

    /**
     * Construct a new TelegramChannelChat.
     * @param id the telegram chat id.
     */
    public TelegramChannelChat(long id, String title) {
        super(id);
        this.title = title;
    }

    /**
     * Returns the chat type.
     * @return
     */
    @Override
    public TelegramChatType getType() {
        return TelegramChatType.CHANNEL;
    }

    /**
     * Returns the title of this channel.
     * @return
     */
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "TelegramChannelChat{" +
                "title='" + title + '\'' +
                "} " + super.toString();
    }
}
