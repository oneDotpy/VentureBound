package use_case.send_message;

import entity.Message;

/**
 * DAO for the Send Message Use Case.
 */
public interface SendMessageDataAccessInterface {

    /**
     * Returns the user with the given username.
     * @param groupID the group ID of the group the message is sent to
     * @param message the message sent
     */
    void updateMessage(String groupID, Message message);
}
