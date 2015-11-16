package nl.lukasmiedema.telegrambotapi.telegram.message;

import nl.lukasmiedema.telegrambotapi.telegram.chat.TelegramChat;
import nl.lukasmiedema.telegrambotapi.telegram.media.TelegramPictureSet;
import nl.lukasmiedema.telegrambotapi.telegram.TelegramUser;

/**
 * TelegramMessage sent when the chat picture changes.
 * @author Lukas Miedema
 */
public class TelegramNewChatPictureMessage extends TelegramMessage {

    private final TelegramPictureSet picture;


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
     * @param chatPicture              the new chat picture
     */
    public TelegramNewChatPictureMessage(int messageId, TelegramUser from, long timestampMillis, TelegramChat chat,
                                         TelegramUser forwardedFrom, long forwardedTimestampMillis,
                                         TelegramMessage replyToMessage, TelegramPictureSet chatPicture) {
        super(messageId, from, timestampMillis, chat, forwardedFrom, forwardedTimestampMillis, replyToMessage);
        this.picture = chatPicture;
    }

    /**
     * Returns the new chat picture.
     * @return
     */
    public TelegramPictureSet getPicture() {
        return picture;
    }
}
