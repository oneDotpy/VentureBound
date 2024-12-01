package use_case.chat;

import java.util.List;

/**
 * Output boundary interface for the Chat use case.
 * Defines methods for presenting chat messages and updating members.
 */
public interface ChatOutputBoundary {
    /**
     * Presents a chat message to the user interface.
     *
     * @param outputData Data containing the sender and message details.
     */
    void presentMessage(ChatOutputData outputData);

    /**
     * Updates the list of members in the chat.
     *
     * @param members A list of updated member usernames.
     */
    void updateMembers(List<String> members);
}
