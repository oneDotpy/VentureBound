package use_case.send_message;

import use_case.create_group.CreateGroupOutputData;

public interface SendMessageGroupOutputBoundary {
    public void prepareChatView(SendMessageOutputData sendMessageOutputData);

    public void prepareFailView(String message);
}
