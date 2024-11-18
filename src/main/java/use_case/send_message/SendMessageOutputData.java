package use_case.send_message;

import entity.Message;

public class SendMessageOutputData {
    private final Message message;

    public SendMessageOutputData(Message message) {
        this.message = message;
    }

    public Message getMessage() { return message; }
}
