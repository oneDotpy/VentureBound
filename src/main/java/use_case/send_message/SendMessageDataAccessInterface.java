package use_case.send_message;

import entity.Message;

import java.util.List;

public interface SendMessageDataAccessInterface {
    public void updateMessage(String groupID, Message messages);
}
