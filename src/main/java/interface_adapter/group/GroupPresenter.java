package interface_adapter.group;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import use_case.group.GroupOutputBoundary;
import use_case.group.GroupOutputData;

import java.util.List;

public class GroupPresenter implements GroupOutputBoundary {
    private final GroupViewModel viewModel;
    private final ChatViewModel chatViewModel;
    private final ViewManagerModel viewManagerModel;

    public GroupPresenter(GroupViewModel viewModel, ChatViewModel chatViewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.chatViewModel = chatViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentCreateGroupResult(GroupOutputData outputData) {
        System.out.println("[GroupPresenter] Create Group Result: " + outputData.getMessage());

        GroupState state = viewModel.getState();
        state.setCreateMessage(outputData.getMessage());
        state.setMembers(outputData.getMembers()); // Update members list
        chatViewModel.setState(chatViewModel.getState());
        chatViewModel.firePropertyChanged("createMessage");
        chatViewModel.firePropertyChanged("members");
    }

    @Override
    public void presentJoinGroupResult(GroupOutputData outputData) {
        System.out.println("[GroupPresenter] Join Group Result: " + outputData.getMessage());

        GroupState state = viewModel.getState();
        final ChatState chatState = chatViewModel.getState();
        state.setJoinMessage(outputData.getMessage());
        state.setMembers(outputData.getMembers()); // Update members list
        viewModel.setState(state);
        chatViewModel.firePropertyChanged("joinMessage");
        chatViewModel.firePropertyChanged("members");
    }
}
