package entity;
import com.google.cloud.Timestamp;

public class CommonMessage implements Message{
    private final String user;
    private final String content;
    private final Timestamp timestamp;

    public CommonMessage(String user, String content, Timestamp timestamp) {
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }


    @Override
    public String getSender() {
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
