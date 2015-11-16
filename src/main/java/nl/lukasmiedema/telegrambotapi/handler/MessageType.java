package nl.lukasmiedema.telegrambotapi.handler;

import nl.lukasmiedema.telegrambotapi.telegram.media.*;
import nl.lukasmiedema.telegrambotapi.telegram.message.*;

/**
 * @author Lukas Miedema
 */
public class MessageType<M extends TelegramMessage> {

    private final Class<M> type;
    protected MessageType(Class<M> type) {
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

    /**
     * Media type inner class
     * @param <M>
     */
    public static class MEDIA<M extends TelegramMedia> extends MessageType<TelegramMediaMessage> {

        private final Class<M> mediaType;
        protected MEDIA(Class<M> mediaType) {
            super(TelegramMediaMessage.class);
            this.mediaType = mediaType;
        }

        @Override
        public boolean typeOf(TelegramMessage message) {
            return super.typeOf(message) && mediaType.isInstance(((TelegramMediaMessage) message).getMedia());
        }

        public static final MEDIA<TelegramMedia> ANY =
                new MEDIA<>(TelegramMedia.class);

        public static final MEDIA<TelegramContact> CONTACT  =
                new MEDIA<>(TelegramContact.class);

        public static final MEDIA<TelegramLocation> LOCATION =
                new MEDIA<>(TelegramLocation.class);

        public static final MEDIA<TelegramPictureSet> PICTURE_SET =
                new MEDIA<>(TelegramPictureSet.class);

        /**
         * File type inner class
         * @param <F>
         */
        public static class FILE<F extends TelegramFile> extends MEDIA<TelegramFile> {


            private final Class<F> fileType;
            protected FILE(Class<F> fileType) {
                super(TelegramFile.class);
                this.fileType = fileType;
            }

            @Override
            public boolean typeOf(TelegramMessage message) {
                return super.typeOf(message) && fileType.isInstance(((TelegramMediaMessage) message).getMedia());
            }

            public static final FILE<TelegramFile> ANY =
                    new FILE<>(TelegramFile.class);

            public static final FILE<TelegramAudio> AUDIO =
                    new FILE<>(TelegramAudio.class);

            public static final FILE<TelegramDocument> DOCUMENT =
                    new FILE<>(TelegramDocument.class);

            public static final FILE<TelegramPicture> PICTURE =
                    new FILE<>(TelegramPicture.class);

            public static final FILE<TelegramSticker> STICKER =
                    new FILE<>(TelegramSticker.class);

            public static final FILE<TelegramVideo> VIDEO =
                    new FILE<>(TelegramVideo.class);

            public static final FILE<TelegramVoice> VOICE =
                    new FILE<>(TelegramVoice.class);
        }
    }
}
