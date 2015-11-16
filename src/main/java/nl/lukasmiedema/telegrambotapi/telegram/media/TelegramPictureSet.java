package nl.lukasmiedema.telegrambotapi.telegram.media;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The Telegram API often sends not one picture, but a set of them.
 * This media type represents that in a type-safe way.
 * @author Lukas Miedema
 */
public class TelegramPictureSet implements TelegramMedia {

    private final List<TelegramPicture> pictures;

    /**
     * Constructs a new TelegramPictureSet. A copy will be made of the
     * provided array.
     * @param pictures
     */
    @JsonCreator
    public TelegramPictureSet(@JsonProperty TelegramPicture... pictures) {
        this.pictures = Collections.unmodifiableList(new ArrayList<>(Arrays.asList(pictures)));
    }

    /**
     * Returns an unmodifiable list of pictures.
     * @return
     */
    public List<TelegramPicture> getPictures() {
        return pictures;
    }
}
