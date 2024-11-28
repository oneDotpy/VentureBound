package interface_adapter.chat;

import com.google.cloud.Timestamp;
import entity.Group;
import entity.Message;
import interface_adapter.ViewManagerModel;
import interface_adapter.welcome.WelcomeState;
import interface_adapter.welcome.WelcomeViewModel;
import use_case.chat.ChatOutputBoundary;
import use_case.chat.ChatOutputData;
import use_case.leave_group.LeaveGroupOutputBoundary;
import use_case.leave_group.LeaveGroupOutputData;
import use_case.receive_message.ReceiveMessageOutputBoundary;
import use_case.receive_message.ReceiveMessageOutputData;

import java.util.ArrayList;
import java.util.List;

public class ChatPresenter implements ChatOutputBoundary, LeaveGroupOutputBoundary, ReceiveMessageOutputBoundary {
    private final ChatViewModel chatViewModel;// Use singleton
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
        ChatState chatState = chatViewModel.getState();
        chatState.addMessage(response.getSender(), response.getMessage());

        // Notify the view model
        chatViewModel.setState(chatState);
        chatViewModel.firePropertyChanged("messages");
        chatViewModel.firePropertyChanged("members");
    }

    public void updateMembers(List<String> members) {
        System.out.println("[ChatPresenter] Updating members list: " + members);

        ChatState chatState = chatViewModel.getState();
        chatState.setMembers(members);

        chatState.getCurrentUser().getGroup().setMembers(members);

        System.out.println("ChatPresenter : Presenting new State");
        // Notify the view model about the updated members
        chatViewModel.setState(chatState);
        chatViewModel.firePropertyChanged("members");
    }

    @Override
    public void switchToWelcomeView(LeaveGroupOutputData response) {
        chatViewModel.stopBot();
        ChatState chatState = chatViewModel.getState();
        chatState.setUser(null);
        chatState.setMembers(new ArrayList<String>());
        chatState.setGroupName("");
        chatState.setCurrentUser(null);
        chatState.setMessages(new ArrayList<>());
        chatViewModel.setState(chatState);
        chatViewModel.stopListenMember();
        chatViewModel.stopListenMessage();
        chatViewModel.firePropertyChanged("messages");
        chatViewModel.firePropertyChanged("members");

        WelcomeState welcomeState = welcomeViewModel.getState();
        welcomeState.setUser(response.getUser());
        welcomeViewModel.setState(welcomeState);
        welcomeViewModel.firePropertyChanged("username");

        viewManagerModel.setState(welcomeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void showMessage(ReceiveMessageOutputData response) {
        Message message = response.getMessage();
        String formattedMessage = response.getFormattedMessage();

        ChatState chatState = chatViewModel.getState();
        chatState.getCurrentUser().getGroup().addMessage(message);

        chatState.addMessages(formattedMessage);
        chatViewModel.setState(chatState);
        chatViewModel.firePropertyChanged("messages");
    }
}

