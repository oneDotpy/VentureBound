package interface_adapter.chat;

import interface_adapter.ViewManagerModel;
import interface_adapter.welcome.WelcomeState;
import interface_adapter.welcome.WelcomeViewModel;
import use_case.chat.ChatOutputBoundary;
import use_case.chat.ChatOutputData;
import use_case.leave_group.LeaveGroupOutputBoundary;
import use_case.leave_group.LeaveGroupOutputData;

import java.util.ArrayList;
import java.util.List;

public class ChatPresenter implements ChatOutputBoundary, LeaveGroupOutputBoundary {
    private final ChatViewModel chatViewModel;
    private final ChatState chatState = ChatState.getInstance();// Use singleton
    private final WelcomeViewModel welcomeViewModel;
    private final ViewManagerModel viewManagerModel;

    public ChatPresenter(ViewManagerModel viewManagerModel, ChatViewModel chatViewModel, WelcomeViewModel welcomeViewModel) {

        this.viewManagerModel = viewManagerModel;
        this.chatViewModel = chatViewModel;
        this.welcomeViewModel = welcomeViewModel;
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

    @Override
    public void switchToWelcomeView(LeaveGroupOutputData response) {
        ChatState chatState = chatViewModel.getState();
        chatState.setUser(null);
        chatState.setMembers(new ArrayList<String>());
        chatState.setGroupName("");
        chatState.setCurrentUser(null);
        chatState.setMessages(new ArrayList<>());

        chatViewModel.setState(chatState);
        chatViewModel.firePropertyChanged("messages");
        chatViewModel.firePropertyChanged("members");

        WelcomeState welcomeState = welcomeViewModel.getState();
        welcomeState.setUser(response.getUser());
        welcomeViewModel.setState(welcomeState);
        welcomeViewModel.firePropertyChanged("username");

        viewManagerModel.setState(welcomeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}

