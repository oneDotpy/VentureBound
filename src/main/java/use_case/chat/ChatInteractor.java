package use_case.chat;

public class ChatInteractor implements ChatInputBoundary {
    private final ChatOutputBoundary chatPresenter;

    public ChatInteractor(ChatOutputBoundary chatPresenter) {
        this.chatPresenter = chatPresenter;
    }

    @Override
    public void sendMessage(ChatInputData inputData) {
        String message = inputData.getMessage();
        String sender = inputData.getUsername();

        System.out.println("[ChatInteractor] Processing message from " + sender + ": " + message);
        ChatOutputData outputData = new ChatOutputData(sender, message);
        chatPresenter.presentMessage(outputData);
    }
}

