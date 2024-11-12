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
        viewModel.setCreateMessage(outputData.getMessage());
    }

    @Override
    public void presentJoinGroupResult(GroupOutputData outputData) {
        viewModel.setJoinMessage(outputData.getMessage());
    }
}
