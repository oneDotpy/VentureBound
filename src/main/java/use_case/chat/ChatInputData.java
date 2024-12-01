package use_case.chat;

import com.google.cloud.Timestamp;

/**
 * Input data class for the Chat use case.
 * Contains information about a chat message and its sender.
 */
public class ChatInputData {
    private final String sender;
    private final String content;
    private final String currentUser;
    private final Timestamp timestamp;

    /**
     * Constructs a new instance of ChatInputData.
     *
     * @param sender      The username of the message sender.
     * @param content     The content of the chat message.
     * @param currentUser The username of the current user viewing the message.
     * @param timestamp   The timestamp of the message.
     */
    public ChatInputData(String sender, String content, String currentUser, Timestamp timestamp) {
        this.sender = sender;
        this.content = content;
        this.currentUser = currentUser;
        this.timestamp = timestamp;
    }

    /**
     * Gets the username of the message sender.
     *
     * @return The sender's username.
     */
    public String getUsername() {
        return sender;
    }

    /**
     * Gets the content of the message.
     *
     * @return The message content.
     */
    public String getMessage() {
        return content;
    }

    /**
     * Gets the username of the current user.
     *
     * @return The current user's username.
     */
    public String currentUser() {
        return currentUser;
    }

    /**
     * Gets the timestamp of the message.
     *
     * @return The message timestamp.
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }
}
