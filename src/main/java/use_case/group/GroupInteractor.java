package use_case.group;

import interface_adapter.group.GroupState;

public class GroupInteractor implements GroupInputBoundary {
    private final GroupOutputBoundary presenter;
    private final GroupState groupState;

    public GroupInteractor(GroupOutputBoundary presenter, GroupState groupState) {
        this.presenter = presenter;
        this.groupState = groupState;
    }

    @Override
    public void createGroup(GroupInputData inputData) {
        String groupName = inputData.getGroupName();
        if (groupName == null || groupName.isEmpty()) {
            presenter.presentCreateGroupResult(new GroupOutputData(false, "Group name cannot be empty"));
        } else {
            groupState.setGroupName(groupName);
            presenter.presentCreateGroupResult(new GroupOutputData(true, "Group created successfully"));
        }
    }

    @Override
    public void joinGroup(GroupInputData inputData) {
        String groupCode = inputData.getGroupName();
        if (groupCode == null || groupCode.isEmpty()) {
            presenter.presentJoinGroupResult(new GroupOutputData(false, "Group code cannot be empty"));
        } else {
            presenter.presentJoinGroupResult(new GroupOutputData(true, "Joined group successfully"));
        }
    }
}
