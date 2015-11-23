package nl.lukasmiedema.telegrambotapi.telegram.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.lukasmiedema.telegrambotapi.telegram.response.TelegramResponse;
import nl.lukasmiedema.telegrambotapi.telegram.TelegramUser;
import nl.lukasmiedema.telegrambotapi.telegram.chat.TelegramChat;
import nl.lukasmiedema.telegrambotapi.telegram.media.*;

import javax.ws.rs.core.GenericType;

/**
 * Base class for all Telegram messages.
 * TelegramMessage is immutable.
 * @author Lukas Miedema
 */
public class TelegramMessage {

    /**
     * Generic type used for deserialization
     */
    public final static GenericType<TelegramResponse<? extends TelegramMessage>> GENERIC_TYPE =
            new GenericType<TelegramResponse<? extends TelegramMessage>>(){};

    private final long messageId;
    private final TelegramUser from;
    private final long timestampMillis;
    private final TelegramChat chat;
    private final TelegramUser forwardedFrom;
    private final long forwardedTimestampMillis;
    private final TelegramMessage replyToMessage;

    /**
     * Constructs a new TelegramMessage.
     * @param messageId the message id
     * @param from the sending user
     * @param timestampMillis the timestamp
     * @param chat the chat this message was sent in
     * @param forwardedFrom the user this was forwarded from, or null
     * @param forwardedTimestampMillis the timestamp of the original message, or 0
     * @param replyToMessage the message this was a reply to, or null
     */
    public TelegramMessage(long messageId, TelegramUser from, long timestampMillis, TelegramChat chat, TelegramUser forwardedFrom, long forwardedTimestampMillis, TelegramMessage replyToMessage) {
        this.messageId = messageId;
        this.from = from;
        this.timestampMillis = timestampMillis;
        this.chat = chat;
        this.forwardedFrom = forwardedFrom;
        this.forwardedTimestampMillis = forwardedTimestampMillis;
        this.replyToMessage = replyToMessage;
    }

    /**
     * Returns the message id.
     * @return
     */
    public long getMessageId() {
        return messageId;
    }

    /**
     * Returns the sender of this message.
     * @return
     */
    public TelegramUser getFrom() {
        return from;
    }

    /**
     * Returns the timestamp of submission.
     * @return
     */
    public long getTimestampMillis() {
        return timestampMillis;
    }

    /**
     * Returns the chat this message was submitted in.
     * @return
     */
    public TelegramChat getChat() {
        return chat;
    }

    /**
     * Returns the original user, or null if this message wasn't forwarded.
     * @return
     */
    public TelegramUser getForwardedFrom() {
        return forwardedFrom;
    }

    /**
     * Returns the timestamp of the forwarded message, or 0 if this message
     * wasn't forwarded.
     * @return
     */
    public long getForwardedTimestampMillis() {
        return forwardedTimestampMillis;
    }

    /**
     * Returns the message this message was a reply to, or nul if this message
     * wasn't a reply.
     * @return
     */
    public TelegramMessage getReplyToMessage() {
        return replyToMessage;
    }

    /**
     * Returns true if this message was a reply, false otherwise.
     * {@link #getReplyToMessage()} can be used to obtain the original message.
     * @return
     */
    public boolean isReply() {
        return replyToMessage != null;
    }

    /**
     * Returns true if this message was forwarded, false otherwise.
     * {@link #getForwardedFrom()} can be used to obtain the original sender.
     * @return
     */
    public boolean isForwarded() {
        return forwardedFrom != null;
    }

    /**
     * Static factory method for Jackson. Returns a subtype of TelegramMessage.
     * @return
     */
    @JsonCreator
    public static TelegramMessage create(
            @JsonProperty(value = "message_id", required =true) long messageId,
            @JsonProperty(value = "from", required = true) TelegramUser from,
            @JsonProperty(value = "date", required = true) long timestampMillis,
            @JsonProperty(value = "chat", required = true) TelegramChat chat,
            @JsonProperty("forward_from") TelegramUser forwardedFrom,
            @JsonProperty("forward_date") long forwardTimestampMillis,
            @JsonProperty("reply_to_message") TelegramMessage replyTo,
            @JsonProperty("text") String text,
            @JsonProperty("audio") TelegramAudio audio,
            @JsonProperty("document")TelegramDocument document,
            @JsonProperty("photo") TelegramPictureSet photo,
            @JsonProperty("sticker") TelegramSticker sticker,
            @JsonProperty("video") TelegramVideo video,
            @JsonProperty("voice") TelegramVoice voice,
            @JsonProperty("caption") String caption,
            @JsonProperty("contact") TelegramContact contact,
            @JsonProperty("location") TelegramLocation location,
            @JsonProperty("new_chat_participant") TelegramUser newChatParticipant,
            @JsonProperty("left_chat_participant") TelegramUser leftChatParticipant,
            @JsonProperty("new_chat_title") String newChatTitle,
            @JsonProperty("new_chat_photo") TelegramPictureSet newChatPhoto,
            @JsonProperty("delete_chat_photo") boolean deleteChatPhoto,
            @JsonProperty("group_chat_created") boolean groupChatCreated) {

        // Determine the type
        if (text != null) {
            if (text.startsWith("/")) {

                // Command
                return new TelegramCommandMessage(
                        messageId,
                        from,
                        timestampMillis,
                        chat,
                        forwardedFrom,
                        forwardTimestampMillis,
                        replyTo,
                        text
                );

            } else {
                // Text
                return new TelegramTextMessage(
                        messageId,
                        from,
                        timestampMillis,
                        chat,
                        forwardedFrom,
                        forwardTimestampMillis,
                        replyTo,
                        text
                );
            }
        }
        if (audio != null) {
            // Media / File / Audio
            return new TelegramMediaMessage<>(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo,
                    audio,
                    null
            );
        }
        if (video != null) {
            // Media / File / Video
            return new TelegramMediaMessage<>(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo,
                    video,
                    caption
            );
        }
        if (document != null) {
            // Media / File / Document
            return new TelegramMediaMessage<>(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo,
                    document,
                    null
            );
        }
        if (photo != null) {
            // Media / File / PhotoSet
            return new TelegramMediaMessage<>(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo,
                    photo,
                    caption
            );
        }
        if (sticker != null) {
            // Media / File / Sticker
            return new TelegramMediaMessage<>(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo,
                    sticker,
                    null
            );
        }
        if (voice != null) {
            // Media / File / Voice
            return new TelegramMediaMessage<>(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo,
                    voice,
                    null
            );
        }
        if (contact != null) {
            // Media / Contact
            return new TelegramMediaMessage<>(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo,
                    contact,
                    null
            );
        }
        if (location != null) {
            // Media / Location
            return new TelegramMediaMessage<>(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo,
                    location,
                    null
            );
        }
        if (newChatParticipant != null) {
            // UserJoin
            return new TelegramUserJoinMessage(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo,
                    newChatParticipant
            );
        }
        if (leftChatParticipant != null) {
            // UserLeave
            return new TelegramUserLeaveMessage(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo,
                    leftChatParticipant
            );
        }
        if (newChatTitle != null) {
            // NewChatTitle
            return new TelegramNewChatTitleMessage(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo,
                    newChatTitle
            );
        }
        if (newChatPhoto != null) {
            // NewChatPicture
            return new TelegramNewChatPictureMessage(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo,
                    newChatPhoto
            );
        }
        if (deleteChatPhoto) {
            // DelChatPicture
            return new TelegramDelChatPictureMessage(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo
            );
        }
        if (groupChatCreated) {
            // NewGroupChat
            return new TelegramNewGroupChatMessage(
                    messageId,
                    from,
                    timestampMillis,
                    chat,
                    forwardedFrom,
                    forwardTimestampMillis,
                    replyTo
            );
        }
        return null;
    }
}
