package nl.lukasmiedema.telegrambotapi.telegram.message;

import nl.lukasmiedema.telegrambotapi.telegram.chat.TelegramChat;
import nl.lukasmiedema.telegrambotapi.telegram.TelegramUser;

/**
 * TelegramMessage sent when a new group chat has been created and this bot is an initial member of it.
 * @author Lukas Miedema
 */
public class TelegramNewGroupChatMessage extends TelegramMessage {

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
     */
    public TelegramNewGroupChatMessage(int messageId, TelegramUser from, long timestampMillis, TelegramChat chat,
                                       TelegramUser forwardedFrom, long forwardedTimestampMillis,
                                       TelegramMessage replyToMessage) {
        super(messageId, from, timestampMillis, chat, forwardedFrom, forwardedTimestampMillis, replyToMessage);
    }
}
