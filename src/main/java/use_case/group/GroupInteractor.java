package use_case.group;

import interface_adapter.group.GroupState;
import java.util.ArrayList;
import java.util.List;

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
        String username = inputData.getUsername();

        // Validate group name
        if (groupName == null || groupName.isEmpty()) {
            presenter.presentCreateGroupResult(new GroupOutputData(false, "Group name cannot be empty", new ArrayList<>()));
            return;
        }

        // Create a new group with the current user as the only member
        List<String> members = new ArrayList<>();
        members.add(username);

        // Update the group state
        groupState.setGroupName(groupName);
        groupState.setMembers(members);

        // Pass the result to the presenter
        presenter.presentCreateGroupResult(new GroupOutputData(true, "Group created successfully", members));
        System.out.println("[GroupInteractor] Group created with members: " + members);
    }

    @Override
    public void joinGroup(GroupInputData inputData) {
        String groupCode = inputData.getGroupName();
        String username = inputData.getUsername();

        // Validate group code
        if (groupCode == null || groupCode.isEmpty()) {
            presenter.presentJoinGroupResult(new GroupOutputData(false, "Group code cannot be empty", new ArrayList<>()));
            return;
        }

        // Simulate fetching existing members (replace with database logic later)
        List<String> members = groupState.getMembers();
        if (!members.contains(username)) {
            members.add(username);
        }

        // Update the group state with the new members list
        groupState.setMembers(members);

        // Pass the result to the presenter
        presenter.presentJoinGroupResult(new GroupOutputData(true, "Joined group successfully", members));
        System.out.println("[GroupInteractor] User " + username + " joined group with members: " + members);
    }
}
