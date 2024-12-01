package use_case.chat;

/**
 * Output data class for the Chat use case.
 * Contains information about a chat message to be displayed.
 */
public class ChatOutputData {
    private final String sender;
    private final String message;

    /**
     * Constructs a new instance of ChatOutputData.
     *
     * @param sender  The username of the message sender.
     * @param message The content of the chat message.
     */
    public ChatOutputData(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    /**
     * Gets the username of the message sender.
     *
     * @return The sender's username.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets the content of the chat message.
     *
     * @return The message content.
     */
    public String getMessage() {
        return message;
    }
}
