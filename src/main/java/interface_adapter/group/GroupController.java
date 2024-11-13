package interface_adapter.group;

import use_case.group.GroupInputBoundary;
import use_case.group.GroupInputData;
import java.util.List;

public class GroupController {
    private final GroupInputBoundary interactor;
    private String currentUser;

    public GroupController(GroupInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void createGroup(String groupName) {
        if (currentUser != null) {
            interactor.createGroup(new GroupInputData(groupName, currentUser));
            System.out.println("[GroupController] Group created by: " + currentUser);
        }
    }

    public void joinGroup(String groupCode) {
        if (currentUser != null) {
            interactor.joinGroup(new GroupInputData(groupCode, currentUser));
        }
    }

    public void updateGroupMembers(List<String> members) {
        System.out.println("[GroupController] Updating group members: " + members);
    }
}
