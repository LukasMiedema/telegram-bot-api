package nl.lukasmiedema.telegrambotapi.telegram.message;

import nl.lukasmiedema.telegrambotapi.telegram.chat.TelegramChat;
import nl.lukasmiedema.telegrambotapi.telegram.TelegramUser;

/**
 * TelegramMessage sent when a user leaves a chat. If the user is this bot, sending a message to the chat will cause the
 * message to be rejected as the bot is no longer in the chat.
 * @author Lukas Miedema
 */
public class TelegramUserLeaveMessage extends TelegramMessage {

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
     * @param leavingUser              the user who left this chat
     */
    public TelegramUserLeaveMessage(int messageId, TelegramUser from, long timestampMillis, TelegramChat chat,
                                    TelegramUser forwardedFrom, long forwardedTimestampMillis,
                                    TelegramMessage replyToMessage, TelegramUser leavingUser) {
        super(messageId, from, timestampMillis, chat, forwardedFrom, forwardedTimestampMillis, replyToMessage);
        this.user = leavingUser;
    }

    /**
     * Returns the one who got away.
     * @return
     */
    public TelegramUser getUser() {
        return this.user;
    }
}
