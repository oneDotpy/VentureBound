package interface_adapter.join_group;

import entity.GroupFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.welcome.WelcomeState;
import interface_adapter.welcome.WelcomeViewModel;
import use_case.join_group.JoinGroupOutputData;
import view.ChatView;

public class JoinGroupPresenter {
    private final GroupFactory groupFactory;
    private final UserFactory userFactory;
    private final ViewManagerModel viewManagerModel;
    private final JoinGroupViewModel joinGroupViewModel;
    private final ChatViewModel chatViewModel;
    private final WelcomeViewModel welcomeViewModel;


    public JoinGroupPresenter(GroupFactory groupFactory, UserFactory userFactory, ViewManagerModel viewManagerModel, JoinGroupViewModel joinGroupViewModel, ChatViewModel chatViewModel, WelcomeViewModel welcomeViewModel) {
        this.groupFactory = groupFactory;
        this.userFactory = userFactory;
        this.viewManagerModel = viewManagerModel;
        this.joinGroupViewModel = joinGroupViewModel;
        this.chatViewModel = chatViewModel;
        this.welcomeViewModel = welcomeViewModel;
    }

    public void presentChatView(JoinGroupOutputData joinGroupOutputData) {
        ChatState chatState = chatViewModel.getState();
        chatState.setUser(joinGroupOutputData.getUser());
        chatState.setCurrentUser(joinGroupOutputData.getUser().getName());

        viewManagerModel.setState(chatViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void presentFailView(String message) {
        JoinGroupState joinGroupState = joinGroupViewModel.getState();
        joinGroupState.setGroupError(message);

        joinGroupViewModel.setState(joinGroupState);
        joinGroupViewModel.firePropertyChanged();
    }

    public void switchToWelcomeView(JoinGroupOutputData joinGroupOutputData) {
        WelcomeState welcomeState = welcomeViewModel.getState();
        welcomeState.setUser(joinGroupOutputData.getUser());
        welcomeViewModel.setState(welcomeState);

        viewManagerModel.setState(welcomeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
