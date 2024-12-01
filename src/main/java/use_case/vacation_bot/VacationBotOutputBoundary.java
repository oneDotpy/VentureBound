package use_case.vacation_bot;

/**
 * Output boundary interface for the Vacation Bot use case.
 * Defines methods to present bot responses and send bot messages.
 */
public interface VacationBotOutputBoundary {
    /**
     * Presents a response from the bot to the user.
     *
     * @param response Data containing the bot's response message.
     */
    void presentBotResponse(VacationBotOutputData response);

    /**
     * Sends a bot message to a user or group.
     *
     * @param sender  The name of the bot or sender.
     * @param message The content of the message to be sent.
     */
    void sendBotMessage(String sender, String message);
}
