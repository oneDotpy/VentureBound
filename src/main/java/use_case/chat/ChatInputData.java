package use_case.chat;

import com.google.cloud.Timestamp;

public class ChatInputData {
    private final String sender;
    private final String content;
    private final String currentUser;
    private final Timestamp timestamp;

    public ChatInputData(String sender, String content, String currentUser, Timestamp timestamp) {
        this.sender = sender;
        this.content = content;
        this.currentUser = currentUser;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return sender;
    }

    public String getMessage() {
        return content;
    }

    public String currentUser() {return currentUser;}

    public Timestamp getTimestamp() {return timestamp;}
}
