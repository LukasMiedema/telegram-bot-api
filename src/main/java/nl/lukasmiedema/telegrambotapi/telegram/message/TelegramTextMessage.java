package nl.lukasmiedema.telegrambotapi.telegram.message;

import nl.lukasmiedema.telegrambotapi.telegram.chat.TelegramChat;
import nl.lukasmiedema.telegrambotapi.telegram.TelegramUser;

/**
 * Represents a normal message with text.
 * @author Lukas Miedema
 */
public class TelegramTextMessage extends TelegramMessage {

    private final String message;

    /**
     * Constructs a new TelegramMessage.
     * @param messageId the message id
     * @param from the sending user
     * @param timestampMillis the timestamp
     * @param chat the chat this message was sent in
     * @param forwardedFrom the user this was forwarded from, or null
     * @param forwardedTimestampMillis the timestamp of the original message, or 0
     * @param replyToMessage the message this was a reply to, or null.
     * @param message the text message
     */
    public TelegramTextMessage(int messageId, TelegramUser from, long timestampMillis, TelegramChat chat,
                               TelegramUser forwardedFrom, long forwardedTimestampMillis,
                               TelegramMessage replyToMessage, String message) {
        super(messageId, from, timestampMillis, chat, forwardedFrom, forwardedTimestampMillis, replyToMessage);
        this.message = message;
    }

    /**
     * Returns the written text message.
     * @return
     */
    public String getMessage() {
        return this.message;
    }
}
