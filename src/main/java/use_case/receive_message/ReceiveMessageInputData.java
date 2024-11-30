package use_case.receive_message;

import com.google.cloud.Timestamp;

/**
 * The input data for the Receive Message Use Case.
 */
public class ReceiveMessageInputData {
    private final String sender;
    private final String content;
    private final String currentUser;
    private final Timestamp timestamp;

    public ReceiveMessageInputData(String sender, String content, String currentUser, Timestamp timestamp) {
        this.sender = sender;
        this.content = content;
        this.currentUser = currentUser;
        this.timestamp = timestamp;
    }

    public String getSender() {return sender;}

    public String getContent() {return content;}

    public String getCurrentUser() {return currentUser;}

    public Timestamp getTimestamp() {return timestamp;}
}
