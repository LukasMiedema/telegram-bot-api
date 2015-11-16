package nl.lukasmiedema.telegrambotapi.telegram.chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Telegram Chat
 * @author Lukas Miedema
 */
public abstract class TelegramChat {

    private final int id;

    /**
     * Construct a new TelegramChat.
     * @param id the telegram chat id.
     */
    public TelegramChat(int id) {
        this.id = id;
    }

    /**
     * Returns the unique chat id.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the chat type.
     * @return
     */
    public abstract TelegramChatType getType();

    /**
     * Represents the telegram chat types.
     */
    public static enum TelegramChatType {
        PRIVATE,
        GROUP,
        CHANNEL
    }

    @Override
    public String toString() {
        return "TelegramChat{" +
                "id=" + id +
                '}';
    }

    /**
     * Static factory method for Jackson. Returns one of the three subtypes of chat:
     * <ul>
     *     <li>{@link TelegramPrivateChat}</li>
     *     <li>{@link TelegramGroupChat}</li>
     *     <li>{@link TelegramChannelChat}</li>
     * </ul>
     * @return
     */
    @JsonCreator
    public static TelegramChat create(
            @JsonProperty("id") int id,
            @JsonProperty("type") String typeRaw,
            @JsonProperty("title") String title,
            @JsonProperty("username") String username,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName
    ) {
        switch(typeRaw) {
            case "group":
                return new TelegramGroupChat(id, title);
            case "private":
                return new TelegramPrivateChat(id, username, firstName, lastName);
            case "channel":
                return new TelegramChannelChat(id, title);
            default:
                return null;
        }
    }
}
