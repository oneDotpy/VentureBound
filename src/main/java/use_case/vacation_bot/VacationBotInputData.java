package use_case.vacation_bot;

/**
 * Input data class for the Vacation Bot.
 * Contains information about the sender and their message for processing.
 */
public class VacationBotInputData {
    private final String username;
    private final String message;

    /**
     * Constructs a new instance of VacationBotInputData.
     *
     * @param username The username of the person sending the message.
     * @param message  The content of the message sent by the user.
     */
    public VacationBotInputData(String username, String message) {
        this.username = username;
        this.message = message;
    }

    /**
     * Gets the username of the message sender.
     *
     * @return The username of the sender.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the content of the message.
     *
     * @return The message content.
     */
    public String getMessage() {
        return message;
    }
}
