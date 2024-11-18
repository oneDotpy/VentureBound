package use_case.send_message;

import entity.Group;
import entity.Message;

public class SendMessageInputData {
    private final Group group;
    private final Message message;

    public SendMessageInputData(Group group, Message message) {
        this.group = group;
        this.message = message;
    }

    public String getGroupID() { return group.getGroupID(); }

    public Message getMessage() { return message; }

}
