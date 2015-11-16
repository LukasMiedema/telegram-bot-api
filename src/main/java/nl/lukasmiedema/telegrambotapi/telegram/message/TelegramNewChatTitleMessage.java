package nl.lukasmiedema.telegrambotapi.telegram.message;

import nl.lukasmiedema.telegrambotapi.telegram.chat.TelegramChat;
import nl.lukasmiedema.telegrambotapi.telegram.TelegramUser;

/**
 * Message sent when the chat title changes.
 * @author Lukas Miedema
 */
public class TelegramNewChatTitleMessage extends TelegramMessage {

    private final String chatTitle;

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
     * @param chatTitle                the new chat title.
     */
    public TelegramNewChatTitleMessage(int messageId, TelegramUser from, long timestampMillis, TelegramChat chat,
                                       TelegramUser forwardedFrom, long forwardedTimestampMillis,
                                       TelegramMessage replyToMessage, String chatTitle) {
        super(messageId, from, timestampMillis, chat, forwardedFrom, forwardedTimestampMillis, replyToMessage);
        this.chatTitle = chatTitle;
    }

    /**
     * Returns the new chat title.
     * @return
     */
    public String getChatTitle() {
        return chatTitle;
    }
}
