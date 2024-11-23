package entity;

import com.google.cloud.Timestamp;

/**
 * Factory for creating CommonMessage objects.
 */
public class CommonMessageFactory implements MessageFactory {

    @Override
    public Message createMessage(String user, String content, Timestamp timestamp) {
        System.out.println("[MessageFactory] Creating new message : " + content + " from :" + user + " at " + timestamp);
        return new CommonMessage(user, content, timestamp);
    }
}
