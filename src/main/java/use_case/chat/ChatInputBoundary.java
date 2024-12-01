package use_case.chat;

import java.util.List;

/**
 * Input boundary interface for the Chat use case.
 * Defines the methods for handling chat-related operations.
 */
public interface ChatInputBoundary {
    /**
     * Sends a chat message.
     *
     * @param inputData Data containing the sender, message, and other details.
     */
    void sendMessage(ChatInputData inputData);

    /**
     * Retrieves the list of members in the chat group.
     *
     * @return A list of group member usernames.
     */
    List<String> getMembers();

    /**
     * Sets the list of members in the chat group.
     *
     * @param members A list of new group member usernames.
     */
    void setMembers(List<String> members);
}
