package nl.lukasmiedema.telegrambotapi.telegram.message;

/**
 * @author Lukas Miedema
 */
public enum TelegramParseMode {

    /**
     * Use markdown to parse the message
     */
    MARKDOWN("Markdown"),

    /**
     * Don't do any message parsing
     */
    NORMAL(null);

    private final String name;

    TelegramParseMode(String nm) {
        this.name = nm;
    }

    @Override
    public String toString() {
        return name;
    }
}
