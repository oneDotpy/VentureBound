package use_case.join_group;

import entity.Group;
import entity.User;
import entity.UserFactory;

public class JoinGroupInteractor implements JoinGroupInputBoundary {
    private final UserFactory userFactory;
    private final JoinGroupGroupDataAccessInterface firestoreGroupDataAccessObject;
    private final JoinGroupUserDataAccessInterface firestoreUserDataAccessObject;
    private final JoinGroupOutputBoundary joinGroupPresenter;


    public JoinGroupInteractor(UserFactory userFactory,
                               JoinGroupGroupDataAccessInterface firestoreGroupDataAccessObject,
                               JoinGroupUserDataAccessInterface firestoreUserDataAccessObject,
                               JoinGroupOutputBoundary joinGroupPresenter) {
        this.userFactory = userFactory;
        this.firestoreGroupDataAccessObject = firestoreGroupDataAccessObject;
        this.firestoreUserDataAccessObject = firestoreUserDataAccessObject;
        this.joinGroupPresenter = joinGroupPresenter;
    }

    public void joinGroup(JoinGroupInputData joinGroupInputData) {
        String groupID = joinGroupInputData.getGroupID();

        if ("".equals(groupID)) {
            joinGroupPresenter.presentFailView("Empty Group ID");
        }

        else if (firestoreGroupDataAccessObject.existByID(groupID)) {
            firestoreGroupDataAccessObject.join(groupID, joinGroupInputData.getUser().getName());

            String username = joinGroupInputData.getUser().getName();
            String password =  joinGroupInputData.getUser().getPassword();
            String email = joinGroupInputData.getUser().getEmail();
            firestoreGroupDataAccessObject.join(username, groupID);
            Group group = firestoreGroupDataAccessObject.get(groupID);
            firestoreUserDataAccessObject.setGroupID(groupID, username);

            User new_user = userFactory.create(username, password, email, group, groupID);
            JoinGroupOutputData joinGroupOutputData = new JoinGroupOutputData(group, new_user);
            joinGroupPresenter.presentChatView(joinGroupOutputData);
        }
        else {
            joinGroupPresenter.presentFailView("Invalid GroupID");
        }
    }

    public void switchToWelcomeView(JoinGroupInputData joinGroupInputData) {
        JoinGroupOutputData joinGroupOutputData = new JoinGroupOutputData(null, joinGroupInputData.getUser());
        joinGroupPresenter.switchToWelcomeView(joinGroupOutputData);
    }
}
