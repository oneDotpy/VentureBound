package use_case.leave_group;

import entity.User;
import entity.UserFactory;

public class LeaveGroupInteractor implements LeaveGroupInputBoundary {
    private final LeaveGroupGroupDataAccessInterface groupDataAccessObject;
    private final LeaveGroupUserDataAccessInterface userDataAccessObject;
    private final UserFactory userFactory;
    private final LeaveGroupOutputBoundary chatPresenter;


    public LeaveGroupInteractor(LeaveGroupGroupDataAccessInterface groupDataAccessObject,
                                LeaveGroupUserDataAccessInterface userDataAccessObject, UserFactory userFactory,
                                LeaveGroupOutputBoundary chatPresenter) {
        this.groupDataAccessObject = groupDataAccessObject;
        this.userDataAccessObject = userDataAccessObject;
        this.userFactory = userFactory;
        this.chatPresenter = chatPresenter;
    }

    @Override
    public void leaveGroup(LeaveGroupInputData leaveGroupInputData) {
        User user = leaveGroupInputData.getUser();
        String groupID = user.getGroupID();
        groupDataAccessObject.removeMember(groupID, user.getName());
        userDataAccessObject.setGroupID("", user.getName());

        groupDataAccessObject.detachListener();

        User new_user = userFactory.create(user.getName(), user.getPassword(), user.getEmail());

        LeaveGroupOutputData response = new LeaveGroupOutputData(new_user);
        chatPresenter.switchToWelcomeView(response);
    }
}
