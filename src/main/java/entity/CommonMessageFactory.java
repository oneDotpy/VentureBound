package entity;

import com.google.cloud.Timestamp;

/**
 * Factory for creating CommonMessage objects.
 */
public class CommonMessageFactory implements MessageFactory {

    @Override
    public Message createMessage(User user, String content, Timestamp timestamp) {
        return new CommonMessage(user, content, timestamp);
    }
}
