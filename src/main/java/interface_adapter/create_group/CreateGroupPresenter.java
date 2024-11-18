package interface_adapter.create_group;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import use_case.create_group.CreateGroupOutputBoundary;
import use_case.create_group.CreateGroupOutputData;
import view.CreateGroupView;

public class CreateGroupPresenter implements CreateGroupOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final ChatViewModel chatViewModel;
    private final CreateGroupViewModel createGroupViewModel;

    public CreateGroupPresenter(ViewManagerModel viewManagerModel, ChatViewModel chatViewModel, CreateGroupViewModel createGroupViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.chatViewModel = chatViewModel;
        this.createGroupViewModel = createGroupViewModel;
    }

    @Override
    public void prepareChatView(CreateGroupOutputData createGroupOutputData) {
        ChatState chatState = chatViewModel.getState();
        // TODO: Make a update implementation of chatState;
        chatState.setCurrentUser(createGroupOutputData.getUser().getName());
        chatViewModel.setState(chatState);

        viewManagerModel.setState(chatViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void prepareFailView(String message) {
        CreateGroupState createGroupState = createGroupViewModel.getState();
        createGroupState.setGroupError(message);

        createGroupViewModel.setState(createGroupState);
        createGroupViewModel.firePropertyChanged();
    }
}
