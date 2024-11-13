package interface_adapter.group;

import use_case.group.GroupInputBoundary;
import use_case.group.GroupInputData;

public class GroupController {
    private final GroupInputBoundary interactor;

    public GroupController(GroupInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void createGroup(String groupName) {
        System.out.println("[GroupController] Creating group with name: " + groupName);
        interactor.createGroup(new GroupInputData(groupName));
    }

    public void joinGroup(String groupCode) {
        System.out.println("[GroupController] Joining group with code: " + groupCode);
        interactor.joinGroup(new GroupInputData(groupCode));
    }
}
