package nl.lukasmiedema.telegrambotapi.telegram.message;

import nl.lukasmiedema.telegrambotapi.telegram.chat.TelegramChat;
import nl.lukasmiedema.telegrambotapi.telegram.TelegramUser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a normal message with text.
 * @author Lukas Miedema
 */
public class TelegramTextMessage extends TelegramMessage {

    private final String message;
    private final static Set<Character> MARKDOWN_SPECIAL = new HashSet<>(
            Arrays.asList('`','*','_', '['));
    private final static char MARKDOWN_ESCAPE_CHAR = '\\';

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
    public TelegramTextMessage(long messageId, TelegramUser from, long timestampMillis, TelegramChat chat,
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


    /**
     * Escapes Telegram Markdown so user input can safely be used in a {@link TelegramParseMode#MARKDOWN} message.
     * @param unescaped
     * @return
     */
    public static String escapeMarkdown(String unescaped) {
        StringBuilder escaped = new StringBuilder();
        for (char c: unescaped.toCharArray()) {
            if (MARKDOWN_SPECIAL.contains(c)) {
                escaped.append(MARKDOWN_ESCAPE_CHAR);
            }
            escaped.append(c);
        }
        return escaped.toString();
    }
}
