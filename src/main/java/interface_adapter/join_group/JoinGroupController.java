package interface_adapter.join_group;

import entity.User;
import use_case.join_group.JoinGroupInputBoundary;
import use_case.join_group.JoinGroupInputData;
import use_case.join_group.JoinGroupInteractor;

public class JoinGroupController implements JoinGroupInputBoundary {
    private final JoinGroupInteractor joinGroupInteractor;

    public JoinGroupController(JoinGroupInteractor joinGroupInteractor) {
        this.joinGroupInteractor = joinGroupInteractor;
    }

    @Override
    public void joinGroup(String groupID, User user) {
        JoinGroupInputData joinGroupInputData = new JoinGroupInputData(user, groupID);
        joinGroupInteractor.joinGroup(joinGroupInputData);
    }
}
