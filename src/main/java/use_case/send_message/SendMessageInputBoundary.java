package use_case.send_message;

import entity.Message;

public interface SendMessageInputBoundary {
    public void sendMessage(String groupID, Message message);
}
