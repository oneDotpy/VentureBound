package use_case.send_message;

public interface SendMessageOutputBoundary {
    public void prepareChatView(SendMessageOutputData sendMessageOutputData);

    public void prepareFailView(String message);
}
