package nl.lukasmiedema.telegrambotapi.handler;

import nl.lukasmiedema.telegrambotapi.telegram.media.*;
import nl.lukasmiedema.telegrambotapi.telegram.message.*;

/**
 * @author Lukas Miedema
 */
public class MessageType<M extends TelegramMessage> {

    private final Class<M> type;
    private MessageType(Class<M> type) {
        this.type = type;
    }

    /**
     * Returns true if the provided message is of the type represented by this message type.
     * @param message
     * @return
     */
    public boolean typeOf(TelegramMessage message) {
        return this.type.isInstance(message);
    }


    public static final MessageType<TelegramMessage> ANY =
            new MessageType<>(TelegramMessage.class);

    public static final MessageType<TelegramCommandMessage> COMMAND =
            new MessageType<>(TelegramCommandMessage.class);

    public static final MessageType<TelegramDelChatPictureMessage> DEL_CHAT_PICTURE =
            new MessageType<>(TelegramDelChatPictureMessage.class);

    public static final MessageType<TelegramNewChatPictureMessage> NEW_CHAT_PICTURE =
            new MessageType<>(TelegramNewChatPictureMessage.class);

    public static final MessageType<TelegramNewChatTitleMessage> NEW_CHAT_TITLE =
            new MessageType<>(TelegramNewChatTitleMessage.class);

    public static final MessageType<TelegramNewGroupChatMessage> NEW_GROUP_CHAT =
            new MessageType<>(TelegramNewGroupChatMessage.class);

    public static final MessageType<TelegramTextMessage> TEXT =
            new MessageType<>(TelegramTextMessage.class);

    public static final MessageType<TelegramUserJoinMessage> USER_JOIN =
            new MessageType<>(TelegramUserJoinMessage.class);

    public static final MessageType<TelegramUserLeaveMessage> USER_LEAVE =
            new MessageType<>(TelegramUserLeaveMessage.class);

    public static final MessageType<TelegramMediaMessage<TelegramMedia>> MEDIA_ANY =
            new MediaMessageType<>(TelegramMedia.class);

    public static final MediaMessageType<TelegramContact> MEDIA_CONTACT  =
            new MediaMessageType<>(TelegramContact.class);

    public static final MediaMessageType<TelegramLocation> MEDIA_LOCATION =
            new MediaMessageType<>(TelegramLocation.class);

    public static final MediaMessageType<TelegramPictureSet> MEDIA_PICTURE_SET =
            new MediaMessageType<>(TelegramPictureSet.class);

    public static final MediaFileMessageType<TelegramFile> MEDIA_FILE_ANY =
            new MediaFileMessageType<>(TelegramFile.class);

    public static final MediaFileMessageType<TelegramAudio> MEDIA_FILE_AUDIO =
            new MediaFileMessageType<>(TelegramAudio.class);

    public static final MediaFileMessageType<TelegramDocument> MEDIA_FILE_DOCUMENT =
            new MediaFileMessageType<>(TelegramDocument.class);

    public static final MediaFileMessageType<TelegramPicture> MEDIA_FILE_PICTURE =
            new MediaFileMessageType<>(TelegramPicture.class);

    public static final MediaFileMessageType<TelegramSticker> MEDIA_FILE_STICKER =
            new MediaFileMessageType<>(TelegramSticker.class);

    public static final MediaFileMessageType<TelegramVideo> MEDIA_FILE_VIDEO =
            new MediaFileMessageType<>(TelegramVideo.class);

    public static final MediaFileMessageType<TelegramVoice> MEDIA_FILE_VOICE =
            new MediaFileMessageType<>(TelegramVoice.class);

    /**
     * Media type inner class
     * @param <M>
     */
    private static class MediaMessageType<M extends TelegramMedia> extends MessageType<TelegramMediaMessage<M>> {

        private final Class<M> mediaType;

        @SuppressWarnings("unchecked")
        private MediaMessageType(Class<M> mediaType) {

            // Java generics at it again
            super((Class<TelegramMediaMessage<M>>) (Class<?>) TelegramMediaMessage.class);
            this.mediaType = mediaType;
        }

        @Override
        public boolean typeOf(TelegramMessage message) {
            return super.typeOf(message) && mediaType.isInstance(((TelegramMediaMessage) message).getMedia());
        }
    }

    /**
     * File type inner class
     * @param <F>
     */
    private static class MediaFileMessageType<F extends TelegramFile> extends MediaMessageType<F> {

        private final Class<F> fileType;

        @SuppressWarnings("unchecked")
        private MediaFileMessageType(Class<F> fileType) {
            super((Class<F>) TelegramFile.class);
            this.fileType = fileType;
        }

        @Override
        public boolean typeOf(TelegramMessage message) {
            return super.typeOf(message) && fileType.isInstance(((TelegramMediaMessage) message).getMedia());
        }
    }
}
