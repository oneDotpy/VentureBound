package entity;

import com.google.cloud.Timestamp;

/**
 * The representation of a message in our program.
 */
public interface Message {
    /**
     * Returns the User of the sender message.
     * @return the Sender.
     */
    String getSender();

    /**
     * Returns the content of the message.
     * @return the content of the message
     */
    String getContent();

    /**
     * Returns the timestamp of the message.
     * @return the timestamp of the message.
     */
    Timestamp getTimestamp();
}
