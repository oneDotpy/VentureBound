package use_case.receive_message;

import com.google.cloud.Timestamp;
import entity.Message;

/**
 * The input data for the Receive Message Use Case.
 */
public class ReceiveMessageInputData {
    private final String currentUser;
    private final Message message;

    public ReceiveMessageInputData(String currentUser, Message message) {
        this.currentUser = currentUser;
        this.message = message;
    }

    public String getCurrentUser() {return currentUser;}

    public Message getMessage() {return message;}
}
