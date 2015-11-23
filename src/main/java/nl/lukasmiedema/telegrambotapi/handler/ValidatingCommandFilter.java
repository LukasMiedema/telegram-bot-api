package nl.lukasmiedema.telegrambotapi.handler;

import nl.lukasmiedema.telegrambotapi.TelegramApi;
import nl.lukasmiedema.telegrambotapi.handler.CommandFilter;
import nl.lukasmiedema.telegrambotapi.handler.MessageEvent;
import nl.lukasmiedema.telegrambotapi.handler.MessageHandler;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramCommandMessage;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A CommandFilter just like {@link CommandFilter} with additional validation capabilities.
 * It checks if the right number of commands are present, and if those arguments are of the right type.
 * It returns a message to the sender if that's not the case and does not invoke the handler.
 * @author Lukas Miedema
 */
public class ValidatingCommandFilter implements MessageHandler<TelegramCommandMessage> {

    private final String command;
    private final String description;
    private final MessageHandler<TelegramCommandMessage> delegate;
    private final Argument[] required;
    private final Argument[] optional;

    /**
     * Constructs a new ValidatingCommandFilter
     * @param command the command to watch for (case insensitive)
     * @param delegate the handler to invoke when the command comes across
     * @param args   the arguments
     */
    public ValidatingCommandFilter(String command, String desc,
                                   MessageHandler<TelegramCommandMessage> delegate, Argument... args) {
        this.command = command;
        this.description = desc;
        this.delegate = delegate;
        this.required = Arrays.stream(args).filter(Argument::isRequired).toArray(Argument[]::new);
        this.optional = Arrays.stream(args).filter(a -> !a.isRequired()).toArray(Argument[]::new);
    }

    /**
     * Returns the name of the command, i.e. "help" for /help
     * @return
     */
    public String getCommand() {
        return command;
    }

    /**
     * Returns the required arguments.
     * @return
     */
    public Argument[] getRequired() {
        return required;
    }

    /**
     * Returns the optional arguments.
     * @return
     */
    public Argument[] getOptional() {
        return optional;
    }

    /**
     * The description
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Handle the message.
     *
     * @param event the message event
     * @param api   the TelegramApi which can be used to send a response
     */
    @Override
    public void handle(MessageEvent<TelegramCommandMessage> event, TelegramApi api) {

        // Check the command
        TelegramCommandMessage cmd = event.getMessage();
        List<String> cmdArgs = cmd.getArguments();

        // Is it for us?
        if (cmd.getCommand().equalsIgnoreCase(command)) {

            // Check the length
            if (cmdArgs.size() < required.length || cmdArgs.size() > required.length + optional.length) {

                // Found a mismatch
                // Cancel the event and print a help message citing this message
                event.setCancelled(true);

                // Basic description
                String text = "Usage: /" + command;

                // The required args
                if (required.length > 0) {
                    text += " ";
                    text += Arrays.stream(required).map(Argument::getName).
                            collect(Collectors.joining(" "));
                }

                // The optional args
                if (optional.length > 0) {
                    text += " ";
                    text += Arrays.stream(optional).map(Argument::getName).
                            collect(Collectors.joining(" "));
                }

                // Bake em away toys
                api.sendText(cmd.getChat().getId(), text, cmd.getMessageId());
            } else {

                // Invoke the command
                delegate.handle(event, api);
            }
        }
    }


    /**
     * Represents a required argument.
     */
    public static class Argument {

        /**
         * Creates a required Argument with no validator
         */
        public static Argument required(String name) {
            return new Argument(name, true);
        }


        /**
         * Returns an optional Argument with no validator
         * @param name
         * @return
         */
        public static Argument optional(String name) {
            return new Argument(name, false);
        }


        private final String name;
        private final boolean required;

        /**
         * Constructs a new Argument
         * @param name the name of the argument. This is used if the argument is missing as help message.
         */
        private Argument(String name, boolean required) {
            this.required = required;
            if (required) {
                this.name = "<" + name + ">";
            } else {
                this.name = "[" + name + "]";
            }
        }

        /**
         * Returns the command name with < or [ around the name
         * @return
         */
        public String getName() {
            return name;
        }

        /**
         * Returns true if the command is required, false otherwise.
         * @return
         */
        public boolean isRequired() {
            return required;
        }
    }
}
