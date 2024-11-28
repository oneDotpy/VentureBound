package interface_adapter.join_group;

import entity.GroupFactory;
import entity.Message;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.welcome.WelcomeState;
import interface_adapter.welcome.WelcomeViewModel;
import use_case.join_group.JoinGroupOutputBoundary;
import use_case.join_group.JoinGroupOutputData;

import java.util.List;

public class JoinGroupPresenter implements JoinGroupOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final JoinGroupViewModel joinGroupViewModel;
    private final ChatViewModel chatViewModel;
    private final WelcomeViewModel welcomeViewModel;


    public JoinGroupPresenter(ViewManagerModel viewManagerModel, JoinGroupViewModel joinGroupViewModel, ChatViewModel chatViewModel, WelcomeViewModel welcomeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.joinGroupViewModel = joinGroupViewModel;
        this.chatViewModel = chatViewModel;
        this.welcomeViewModel = welcomeViewModel;
    }

    public void presentChatView(JoinGroupOutputData joinGroupOutputData) {
        // Set Members, Current Users
        ChatState chatState = chatViewModel.getState();
        chatState.setUser(joinGroupOutputData.getUser());
        chatState.setCurrentUser(joinGroupOutputData.getUser());
        chatState.setMembers(joinGroupOutputData.getGroup().getUsernames());
        List<Message> messages = joinGroupOutputData.getGroup().getMessages();
        for (int i = 0; i < messages.size() - 1; i++) {
            Message message = messages.get(i);
            System.out.println(message.getContent());
            chatState.addMessage(message.getSender(), message.getContent());
        }
        chatState.setGroupName(joinGroupOutputData.getGroup().getGroupName());

        chatViewModel.setState(chatState);

        // Fire to switch into ChatViewModel
        viewManagerModel.setState(chatViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        // Fire to notify chatViewModel to update the members
        chatViewModel.startListeningForUpdates(joinGroupOutputData.getGroup().getGroupID());
        chatViewModel.firePropertyChanged("members");
        chatViewModel.firePropertyChanged("messages");
    }

    public void presentFailView(String message) {
        JoinGroupState joinGroupState = joinGroupViewModel.getState();
        joinGroupState.setGroupError(message);

        joinGroupViewModel.setState(joinGroupState);

        joinGroupViewModel.firePropertyChanged("error");
    }

    public void switchToWelcomeView(JoinGroupOutputData joinGroupOutputData) {
        WelcomeState welcomeState = welcomeViewModel.getState();
        welcomeState.setUser(joinGroupOutputData.getUser());
        welcomeViewModel.setState(welcomeState);

        viewManagerModel.setState(welcomeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
