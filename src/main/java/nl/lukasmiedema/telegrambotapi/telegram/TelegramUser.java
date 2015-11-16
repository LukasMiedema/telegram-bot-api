package nl.lukasmiedema.telegrambotapi.telegram;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.lukasmiedema.telegrambotapi.telegram.response.TelegramResponse;

import javax.ws.rs.core.GenericType;

/**
 * Represents a Telegram User.
 * @author Lukas Miedema
 */
public class TelegramUser {

    /**
     * Generic type for deserialization with Jackson
     */
    public final static GenericType<TelegramResponse<TelegramUser>> GENERIC_TYPE = new GenericType<TelegramResponse<TelegramUser>>(){};

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String username;

    /**
     * Constructs a new TelegramUser.
     * @param id the telegram user id
     * @param firstName the first name or null if the user hasn't set a first name.
     * @param lastName the last name or null if the user hasn't set a last name.
     * @param username the user name (@...), or null if the user hasn't set a username.
     */
    @JsonCreator
    public TelegramUser(@JsonProperty("id") int id, @JsonProperty("first_name") String firstName,
                        @JsonProperty("last_name") String lastName, @JsonProperty("username") String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    /**
     * Returns the Telegram User Id.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the users first name or null if the user hasn't set a first name.
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the users last name or null if the user hasn't set a last name.
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the users username (@...), or null if the user hasn't set a username.
     * @return
     */
    public String getUsername() {
        return username;
    }
}
