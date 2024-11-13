package interface_adapter.chat;

import use_case.chat.ChatOutputBoundary;
import use_case.chat.ChatOutputData;

public class ChatPresenter implements ChatOutputBoundary {
    private final ChatViewModel chatViewModel;
    private final ChatState chatState;

    public ChatPresenter(ChatViewModel chatViewModel, ChatState chatState) {
        this.chatViewModel = chatViewModel;
        this.chatState = chatState;
    }

    @Override
    public void presentMessage(ChatOutputData response) {
        System.out.println("[ChatPresenter] Presenting message from " + response.getSender() + ": " + response.getMessage());
        chatState.addMessage(response.getSender(), response.getMessage());

        // Notify the view model
        chatViewModel.setState(chatState);
        chatViewModel.firePropertyChanged("messages");
    }

}
