package use_case.vacation_bot;

/**
 * Input data class for the Vacation Bot.
 * Contains information about the sender and their message for processing.
 */
public class VacationBotInputData {
    private final String groupID;
    private final int groupSize;

    /**
     * Constructs a new instance of VacationBotInputData.
     *
     * @param groupID The groupID of the person sending the message.
     * @param groupSize  The content of the message sent by the user.
     */
    public VacationBotInputData(String groupID, int groupSize) {
        this.groupID = groupID;
        this.groupSize = groupSize;
    }

    /**
     * Gets the username of the message sender.
     *
     * @return The username of the sender.
     */
    public String getGroupID() {
        return groupID;
    }

    /**
     * Gets the content of the message.
     *
     * @return The message content.
     */
    public int getGroupSize() {
        return groupSize;
    }
}
