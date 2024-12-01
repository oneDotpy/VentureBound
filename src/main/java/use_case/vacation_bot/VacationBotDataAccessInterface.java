package use_case.vacation_bot;

import entity.Message;

public interface VacationBotDataAccessInterface {
    /**
     * Returns the user with the given username.
     * @param groupID the group ID of the group the message is sent to
     * @param message the message sent
     */
    void updateMessage(String groupID, Message message);
}
