package interface_adapter.chat;

import interface_adapter.ViewManagerModel;
import use_case.chat.ChatOutputBoundary;
import use_case.chat.ChatOutputData;

import java.util.List;

public class ChatPresenter implements ChatOutputBoundary {
    private final ChatViewModel chatViewModel;
    private final ChatState chatState = ChatState.getInstance(); // Use singleton
    private final ViewManagerModel viewManagerModel;

    public ChatPresenter(ViewManagerModel viewManagerModel, ChatViewModel chatViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.chatViewModel = chatViewModel;
    }

    @Override
    public void presentMessage(ChatOutputData response) {
        System.out.println("[ChatPresenter] Presenting message from " + response.getSender() + ": " + response.getMessage());
        chatState.addMessage(response.getSender(), response.getMessage());

        // Notify the view model
        chatViewModel.setState(chatState);
        chatViewModel.firePropertyChanged("messages");
        chatViewModel.firePropertyChanged("members");
    }

    public void updateMembers(List<String> members) {
        System.out.println("[ChatPresenter] Updating members list: " + members);
        chatState.setMembers(members);

        // Notify the view model about the updated members
        chatViewModel.setState(chatState);
        chatViewModel.firePropertyChanged("members");
    }

}

