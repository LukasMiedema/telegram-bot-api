package nl.lukasmiedema.telegrambotapi.telegram.chat;

/**
 * TelegramChat with "PRIVATE" as type.
 * Private chats are one-to-one, where we are one party.
 * Private chats have a username, first and last name of the other party.
 * @author Lukas Miedema
 */
public class TelegramPrivateChat extends TelegramChat {

    private final String username;
    private final String firstName;
    private final String lastName;

    /**
     * Constructs a new TelegramPrivateChat
     * @param id the chat id
     * @param username the username of the other party, or null if not set.
     * @param firstName the first name of the other party, or null if not set.
     * @param lastName the last name of the other party, or null if not set.
     */
    public TelegramPrivateChat(int id, String username, String firstName, String lastName) {
        super(id);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Returns the user name of the other party, or null if not set.
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the first name of the other party, or null if not set.
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the other party, or null if not set.
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns PRIVATE.
     * @return
     */
    @Override
    public TelegramChatType getType() {
        return TelegramChatType.PRIVATE;
    }

    @Override
    public String toString() {
        return "TelegramPrivateChat{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "} " + super.toString();
    }
}
