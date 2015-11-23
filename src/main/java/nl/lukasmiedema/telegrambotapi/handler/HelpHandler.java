package nl.lukasmiedema.telegrambotapi.handler;

import nl.lukasmiedema.telegrambotapi.TelegramApi;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramCommandMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class to handle a /help command. Its constructed with an array of {@link ValidatingCommandFilter}
 * and uses those to display help. Note that a {@link HelpHandler} constructs its own {@link ValidatingCommandFilter}
 * and that it will add that to the list.
 * @author Lukas Miedema
 */
public class HelpHandler implements MessageHandler<TelegramCommandMessage> {

    private final ValidatingCommandFilter helpFilter;
    private final String help;

    /**
     * Construct a new HelpHandler
     * @param helpDesc the description of the /help command in the list
     * @param prefix a message to display before the list of commands
     * @param commands the commands
     */
    public HelpHandler(String helpDesc, String prefix, ValidatingCommandFilter... commands) {

        // Create our own validating command filter
        this.helpFilter = new ValidatingCommandFilter("help", helpDesc, this::handleHelp);

        // Create a list of command filters to iterate through
        List<ValidatingCommandFilter> filters = new ArrayList<>(commands.length + 1);
        filters.addAll(Arrays.asList(commands));
        filters.add(helpFilter);

        // Start with the prefix
        StringBuilder msg = new StringBuilder(prefix).append('\n');

        // Now all the commands
        for (ValidatingCommandFilter cmd: commands) {
            msg.append(" - /").append(cmd.getCommand());

            // Now for the arguments
            if (cmd.getRequired().length > 0) {
                msg.append(' ');
                msg.append(Arrays.stream(cmd.getRequired()).map(ValidatingCommandFilter.Argument::getName).
                        collect(Collectors.joining(" ")));
            }

            // The optional args
            if (cmd.getOptional().length > 0) {
                msg.append(' ');
                msg.append(Arrays.stream(cmd.getOptional()).map(ValidatingCommandFilter.Argument::getName).
                        collect(Collectors.joining(" ")));
            }

            // Next up is the description and a newline char
            msg.append(" - ").append(cmd.getDescription()).append("\n");
        }

        this.help = msg.toString();
    }

    @Override
    public void handle(MessageEvent<TelegramCommandMessage> event, TelegramApi api) {
        // Delegate to the helpFilter
        helpFilter.handle(event, api);
    }

    /**
     * Displays the help
     * @param event
     * @param api
     */
    private void handleHelp(MessageEvent<TelegramCommandMessage> event, TelegramApi api) {

        // Send the message
        long msgId = event.getMessage().getMessageId();
        long chatId = event.getMessage().getChat().getId();
        api.sendText(chatId, this.help, msgId);
    }
}
