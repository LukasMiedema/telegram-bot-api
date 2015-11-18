package nl.lukasmiedema.telegrambotapi.handler;

import nl.lukasmiedema.telegrambotapi.TelegramApi;
import nl.lukasmiedema.telegrambotapi.telegram.message.TelegramMessage;

/**
 * Functional interface for TelegramMessageHandler. TelegramMessageHandlers can be registered with
 * {@link nl.lukasmiedema.telegrambotapi.TelegramBot} and receive an instance of {@link TelegramMessage}
 * @author Lukas Miedema
 */
@FunctionalInterface
public interface MessageHandler<M extends TelegramMessage> {

    /**
     * Handle the message.
     * @param event     the message event
     * @param api       the TelegramApi which can be used to send a response
     */
    void handle(MessageEvent<M> event, TelegramApi api);

}
