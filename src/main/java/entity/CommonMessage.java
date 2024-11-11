package entity;
import com.google.cloud.Timestamp;

public class CommonMessage implements Message{
    private final User user;
    private final String content;
    private final Timestamp timestamp;

    public CommonMessage(User user, String content, Timestamp timestamp) {
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }


    @Override
    public User getSender() {
        return user;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Timestamp getTimestamp() {
        return timestamp;
    }
}
