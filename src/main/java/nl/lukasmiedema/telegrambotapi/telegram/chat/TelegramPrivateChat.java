package nl.lukasmiedema.telegrambotapi.telegram.chat;

import nl.lukasmiedema.telegrambotapi.telegram.TelegramUser;

/**
 * TelegramChat with "PRIVATE" as type.
 * Private chats are one-to-one, where we are one party.
 * Private chats have a username, first and last name of the other party.
 * @author Lukas Miedema
 */
public class TelegramPrivateChat extends TelegramChat {

    private final TelegramUser user;

    /**
     * Constructs a new TelegramPrivateChat using the user id as chat id
     * @param user the other party
     */
    public TelegramPrivateChat(TelegramUser user) {
        super(user.getId());
        this.user = user;
    }

    /**
     * Returns the other party in this conversation.
     * @return
     */
    public TelegramUser getUser() {
        return user;
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
                "user=" + user +
                "} " + super.toString();
    }
}
