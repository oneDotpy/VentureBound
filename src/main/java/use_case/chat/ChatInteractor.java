package use_case.chat;

public class ChatInteractor implements ChatInputBoundary {
    private final ChatOutputBoundary presenter;

    public ChatInteractor(ChatOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void sendMessage(ChatInputData inputData) {
        // Format message and send to presenter
        String formattedMessage = inputData.getUsername() + ": " + inputData.getMessage();
        ChatOutputData outputData = new ChatOutputData(formattedMessage);
        presenter.presentMessage(outputData);
    }
}
