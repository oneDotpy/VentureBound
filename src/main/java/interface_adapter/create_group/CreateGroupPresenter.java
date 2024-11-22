package interface_adapter.create_group;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.welcome.WelcomeState;
import interface_adapter.welcome.WelcomeViewModel;
import use_case.create_group.CreateGroupOutputBoundary;
import use_case.create_group.CreateGroupOutputData;

/**
 * Presenter that prepare data from interactor into chatView
 */
public class CreateGroupPresenter implements CreateGroupOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final ChatViewModel chatViewModel;
    private final CreateGroupViewModel createGroupViewModel;
    private final WelcomeViewModel welcomeViewModel;

    public CreateGroupPresenter(ViewManagerModel viewManagerModel, ChatViewModel chatViewModel, CreateGroupViewModel createGroupViewModel, WelcomeViewModel welcomeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.chatViewModel = chatViewModel;
        this.createGroupViewModel = createGroupViewModel;
        this.welcomeViewModel = welcomeViewModel;
    }

    @Override
    public void prepareChatView(CreateGroupOutputData createGroupOutputData) {
        ChatState chatState = chatViewModel.getState();
        chatState.setUser(createGroupOutputData.getUser());
        chatState.setCurrentUser(createGroupOutputData.getUser().getName());
        chatState.setMembers(createGroupOutputData.getGroup().getUsernames());
        chatState.setGroupName(createGroupOutputData.getGroup().getGroupName());
        chatViewModel.setState(chatState);

        viewManagerModel.setState(chatViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        chatViewModel.firePropertyChanged("members");
    }

    public void presentFailView(String message) {
        CreateGroupState createGroupState = createGroupViewModel.getState();
        createGroupState.setGroupError(message);

        createGroupViewModel.setState(createGroupState);
        createGroupViewModel.firePropertyChanged();
    }

    public void switchToWelcomeView(CreateGroupOutputData createGroupOutputData) {
        WelcomeState welcomeState = welcomeViewModel.getState();
        welcomeState.setUser(createGroupOutputData.getUser());
        welcomeViewModel.setState(welcomeState);

        viewManagerModel.setState(welcomeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
