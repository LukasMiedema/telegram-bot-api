package nl.lukasmiedema.telegrambotapi.telegram.message;

import nl.lukasmiedema.telegrambotapi.telegram.chat.TelegramChat;
import nl.lukasmiedema.telegrambotapi.telegram.TelegramUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a telegram command.
 * @author Lukas Miedema
 */
public class TelegramCommandMessage extends TelegramMessage {

    private final String rawMessage;
    private final String command;
    private final String target;
    private final List<String> args;

    /**
     * Constructs a new TelegramMessage.
     * @param messageId the rawMessage id
     * @param from the sending user
     * @param timestampMillis the timestamp
     * @param chat the chat this rawMessage was sent in
     * @param forwardedFrom the user this was forwarded from, or null
     * @param forwardedTimestampMillis the timestamp of the original rawMessage, or 0
     * @param replyToMessage the rawMessage this was a reply to, or null.
     * @param message the text rawMessage, with leading /
     */
    public TelegramCommandMessage(int messageId, TelegramUser from, long timestampMillis, TelegramChat chat,
                                  TelegramUser forwardedFrom, long forwardedTimestampMillis,
                                  TelegramMessage replyToMessage, String message) {
        super(messageId, from, timestampMillis, chat, forwardedFrom, forwardedTimestampMillis, replyToMessage);
        this.rawMessage = message;

        // Split the message
        String[] split = message.substring(1).split(" ");

        // Set the args
        String[] argsArr = new String[split.length - 1];
        System.arraycopy(split, 1, argsArr, 0, argsArr.length);
        this.args = Collections.unmodifiableList(new ArrayList<>(Arrays.asList(argsArr)));

        // Get the command and target (if any)
        String cmdAndTarget = split[0];
        int cmdLength = cmdAndTarget.indexOf('@');
        String target = null;
        String command = null;

        // The command was either send like this: /command@Target
        // or like this: /command
        // In the last case there's no target
        if (cmdLength == -1) {
            // There's no target
            command = cmdAndTarget;
        } else {
            command = cmdAndTarget.substring(0, cmdLength);
            target = cmdAndTarget.substring(cmdLength + 1, cmdAndTarget.length());
        }
        this.command = command;
        this.target = target;
    }



    /**
     * Returns the raw message as it was received, usually in the format /msg arg0 arg1 arg2...
     * @return
     */
    public String getRawMessage() {
        return this.rawMessage;
    }

    /**
     * Returns an unmodifiable list of arguments.
     * @return
     */
    public List<String> getArguments() {
        return this.args;
    }

    /**
     * Returns the command.
     * @return
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * Returns the target, or null if there was no target.
     * The target is the value after the @ in the command name.
     * /command@TestBot arg0 arg1 ...
     * TestBot would be the target.
     * @return
     */
    public String getTarget() {
        return this.target;
    }
}
