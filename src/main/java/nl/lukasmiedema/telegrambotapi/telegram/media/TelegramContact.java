package nl.lukasmiedema.telegrambotapi.telegram.media;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TelegramContact
 * @author Lukas Miedema
 */
public class TelegramContact implements TelegramMedia {

    private final String phoneNumber;
    private final String firstName;
    private final String lastName;
    private final int userId;

    /**
     * Constructs a new telegram contact
     * @param userId the telegram user id of this contact
     * @param phoneNumber
     * @param firstName
     * @param lastName
     */
    @JsonCreator
    public TelegramContact(@JsonProperty("user_id") int userId, @JsonProperty("phone_number") String phoneNumber,
                           @JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getUserId() {
        return userId;
    }
}
