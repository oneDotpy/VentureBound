package interface_adapter.group;

import use_case.group.GroupOutputBoundary;
import use_case.group.GroupOutputData;

public class GroupPresenter implements GroupOutputBoundary {
    private final GroupViewModel viewModel;

    public GroupPresenter(GroupViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentCreateGroupResult(GroupOutputData outputData) {
        System.out.println("[GroupPresenter] Create Group Result: " + outputData.getMessage());

        GroupState state = viewModel.getState();
        state.setCreateMessage(outputData.getMessage());
        viewModel.setState(state);
        viewModel.firePropertyChanged("createMessage");
    }

    @Override
    public void presentJoinGroupResult(GroupOutputData outputData) {
        System.out.println("[GroupPresenter] Join Group Result: " + outputData.getMessage());

        GroupState state = viewModel.getState();
        state.setJoinMessage(outputData.getMessage());
        viewModel.setState(state);
        viewModel.firePropertyChanged("joinMessage");
    }
}
