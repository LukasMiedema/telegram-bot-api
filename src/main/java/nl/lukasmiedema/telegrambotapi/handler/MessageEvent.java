package nl.lukasmiedema.telegrambotapi.handler;

import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramMessage;

/**
 * Represents a TelegramMessageEvent which propagates through the message handlers either until there are no
 * more handlers or a handler cancels it.
 * @author Lukas Miedema
 */
public class MessageEvent<M extends TelegramMessage> {

    private final M message;
    private boolean isCancelled;

    /**
     * Construct a new TelegramMessageEvent.
     * @param message
     * @param isCancelled
     */
    public MessageEvent(M message, boolean isCancelled) {
        this.message = message;
        this.isCancelled = isCancelled;
    }

    /**
     * Returns the TelegramMessage
     * @return
     */
    public M getMessage() {
        return message;
    }

    /**
     * Returns true if this event is cancelled. If it's cancelled, it will not invoke any other event handlers.
     * @return
     */
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Sets the cancelled state of the event.
     * @param cancelled when set to true, any following event handlers will not be invoked by this event.
     */
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
