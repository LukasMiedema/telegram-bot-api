package nl.lukasmiedema.telegrambotapi.handler;

import nl.lukasmiedema.telegrambotapi.TelegramApi;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramCommandMessage;

/**
 * Filter for {@link MessageHandler} with type
 * {@link nl.lukasmiedema.telegrambotapi.telegram.message.TelegramCommandMessage}.
 * A handler registered through this filter will only receive commands with the provided command name.
 * Additionally, this handler will cancel any commands matching its command name.
 * @author Lukas Miedema
 */
public class CommandFilter implements MessageHandler<TelegramCommandMessage> {

    private final String command;
    private final MessageHandler<TelegramCommandMessage> delegate;

    /**
     * Constructs a new CommandFilter
     * @param command the command to watch for (case insensitive)
     * @param delegate the handler to invoke when the command comes across
     */
    public CommandFilter(String command, MessageHandler<TelegramCommandMessage> delegate) {
        this.command = command;
        this.delegate = delegate;
    }


    /**
     * Handle the message.
     * @param event   the message event
     * @param api     the TelegramApi which can be used to send a response
     */
    @Override
    public void handle(MessageEvent<TelegramCommandMessage> event, TelegramApi api) {
        String cmd = event.getMessage().getCommand();
        if (cmd.equalsIgnoreCase(command)) {

            // Cancel the event so it doesn't invoke other command handlers
            // Do this before the delegation so the delegate could override it
            event.setCancelled(true);

            // And delegate
            delegate.handle(event, api);
        }
    }
}
