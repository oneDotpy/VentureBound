package entity;

import com.google.cloud.Timestamp;

/**
 * Factory for creating message.
 */
public interface MessageFactory {
    /**
     * Creates a group from database.
     * @param user the sender of the message
     * @param content the content of the message
     * @param timestamp the timestamp of the message
     * @return the message
     */
    public Message createMessage(User user, String content, Timestamp timestamp);
}
