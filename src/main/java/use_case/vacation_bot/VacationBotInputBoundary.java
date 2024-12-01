package use_case.vacation_bot;

import java.util.List;

/**
 * Input boundary interface for the Vacation Bot use case.
 * Defines the methods that an interactor must implement to manage the Vacation Bot.
 */
public interface VacationBotInputBoundary {
    /**
     * Starts the bot with a specified group ID and response threshold.
     *
     * @param groupID   The unique identifier of the group interacting with the bot.
     * @param threshold The number of responses required to proceed with processing.
     */
    void startBot(String groupID, int threshold);

    /**
     * Stops the bot and resets its state.
     */
    void stopBot();

    /**
     * Checks whether the bot is currently active.
     *
     * @return True if the bot is active, false otherwise.
     */
    boolean isBotActive();

    /**
     * Handles an incoming message from a user.
     *
     * @param username  The name of the user sending the message.
     * @param message   The content of the message sent by the user.
     * @param groupSize The size of the group interacting with the bot.
     * @param groupID   The unique identifier of the group interacting with the bot.
     */
    void handleMessage(String username, String message, int groupSize, String groupID);

    /**
     * Sets the response threshold required for processing responses.
     *
     * @param threshold The new threshold value.
     */
    void setThreshold(int threshold);

    /**
     * Removes responses from group members who are no longer part of the group.
     *
     * @param members A list of current group members.
     */
    void removeResponse(List<String> members);
}
