package interface_adapter.join_group;

import entity.User;
import use_case.join_group.JoinGroupInputBoundary;
import use_case.join_group.JoinGroupInputData;
import use_case.join_group.JoinGroupInteractor;

public class JoinGroupController{
    private final JoinGroupInputBoundary joinGroupInteractor;

    public JoinGroupController(JoinGroupInputBoundary joinGroupInteractor) {
        this.joinGroupInteractor = joinGroupInteractor;
    }

    public void joinGroup(String groupID, User user) {
        JoinGroupInputData joinGroupInputData = new JoinGroupInputData(user, groupID);
        joinGroupInteractor.joinGroup(joinGroupInputData);
    }

    public void switchToWelcomeView(User user) {
        JoinGroupInputData joinGroupInputData = new JoinGroupInputData(user, "");
        joinGroupInteractor.switchToWelcomeView(joinGroupInputData);
    }
}
