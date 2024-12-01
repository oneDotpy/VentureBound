package use_case.vacation_bot;

/**
 * Output data class for the Vacation Bot.
 * Contains the message to be presented to the user.
 */
public class VacationBotOutputData {
    private final String message;

    /**
     * Constructs a new instance of VacationBotOutputData.
     *
     * @param message The message to be presented.
     */
    public VacationBotOutputData(String message) {
        this.message = message;
    }

    /**
     * Gets the bot's response message.
     *
     * @return The response message.
     */
    public String getMessage() {
        return message;
    }
}
