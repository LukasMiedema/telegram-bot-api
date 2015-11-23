package nl.lukasmiedema.telegrambotapi.telegram.message;

import nl.lukasmiedema.telegrambotapi.telegram.*;
import nl.lukasmiedema.telegrambotapi.telegram.chat.TelegramChat;
import nl.lukasmiedema.telegrambotapi.telegram.media.*;

/**
 * TelegramMessage with some kind of media attached to it. Options are:
 * <ul>
 *     <li>{@link TelegramMedia}</li>
 *     <li>{@link TelegramFile}</li>
 *     <li>{@link TelegramDocument}</li>
 *     <li>{@link TelegramVoice}</li>
 *     <li>{@link TelegramVideo}</li>
 *     <li>{@link TelegramAudio}</li>
 *     <li>{@link TelegramLocation}</li>
 *     <li>{@link TelegramContact}</li>
 *     <li>{@link TelegramPictureSet}</li>
 *     <li>{@link TelegramSticker}</li>
 * </ul>
 * In other words: any subtype of {@link TelegramMedia}<br>
 * <br>
 * <b>Note:</b> TelegramPicture is never sent as media type in a message. Instead the special
 * {@link TelegramPictureSet} is used, which contains an array of pictures instead of one.
 *
 * @author Lukas Miedema
 */
public class TelegramMediaMessage<M extends TelegramMedia> extends TelegramMessage {

    private final M media;
    private final String caption;

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
     * @param media                    the media file attached to this message
     * @param caption                  the caption of this video, or null if there's no caption or this isn't a video.
     */
    public TelegramMediaMessage(long messageId, TelegramUser from, long timestampMillis, TelegramChat chat,
                                TelegramUser forwardedFrom, long forwardedTimestampMillis,
                                TelegramMessage replyToMessage, M media, String caption) {
        super(messageId, from, timestampMillis, chat, forwardedFrom, forwardedTimestampMillis, replyToMessage);
        this.media = media;
        this.caption = caption;
    }

    /**
     * Returns the attached media item.
     * @return
     */
    public M getMedia() {
        return media;
    }

    /**
     * Returns the caption. This will be null if TelegramMedia is not a video.
     * @return
     */
    public String getCaption() {
        return this.caption;
    }
}
