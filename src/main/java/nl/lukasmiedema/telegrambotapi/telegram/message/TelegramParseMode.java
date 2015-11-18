package nl.lukasmiedema.telegrambotapi.telegram.message;

/**
 * @author Lukas Miedema
 */
public enum TelegramParseMode {
    MARKDOWN("Markdown");

    private final String name;

    TelegramParseMode(String nm) {
        this.name = nm;
    }

    @Override
    public String toString() {
        return name;
    }
}
