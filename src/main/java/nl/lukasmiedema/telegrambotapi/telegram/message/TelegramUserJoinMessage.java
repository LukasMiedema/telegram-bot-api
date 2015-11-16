package nl.lukasmiedema.telegrambotapi.telegram.message;

import nl.lukasmiedema.telegrambotapi.telegram.chat.TelegramChat;
import nl.lukasmiedema.telegrambotapi.telegram.TelegramUser;

/**
 * TelegramMessage sent when a user joins a chat. This message also gets submitted when this bot gets added to a chat.
 * @author Lukas Miedema
 */
public class TelegramUserJoinMessage extends TelegramMessage {

    private final TelegramUser user;

    /**
     * Constructs a new TelegramMessage.
     *
     * @param messageId                the message id
     * @param from                     the sending user
     * @param timestampMillis          the timestamp
     * @param chat                     the chat this message was sent in
     * @param forwardedFrom            the user this was forwarded from, or null
     * @param forwardedTimestampMillis the timestamp of the original message, or 0
     * @param replyToMessage           the message this was a reply to, or null
     * @param joiningUser              the user who per this message joins this chat
     */
    public TelegramUserJoinMessage(int messageId, TelegramUser from, long timestampMillis, TelegramChat chat,
                                   TelegramUser forwardedFrom, long forwardedTimestampMillis,
                                   TelegramMessage replyToMessage, TelegramUser joiningUser) {
        super(messageId, from, timestampMillis, chat, forwardedFrom, forwardedTimestampMillis, replyToMessage);
        this.user = joiningUser;
    }

    /**
     * Returns the just joined user.
     * @return
     */
    public TelegramUser getUser() {
        return this.user;
    }
}
