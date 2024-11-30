package use_case.receive_message;

import com.google.cloud.Timestamp;
import entity.Message;

public class ReceiveMessageOutputData {
    private final String formatted_message;
    private final Message message;


    public ReceiveMessageOutputData(String formattedMessage, Message message) {
        formatted_message = formattedMessage;
        this.message = message;
    }

    public Message getMessage() {return message;}

    public String getFormattedMessage() {return formatted_message;}
}
