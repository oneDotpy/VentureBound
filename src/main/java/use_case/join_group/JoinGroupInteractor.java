package use_case.join_group;

import data_access.FirestoreGroupDataAccessObject;
import entity.Group;
import entity.User;
import entity.UserFactory;
import interface_adapter.join_group.JoinGroupPresenter;

public class JoinGroupInteractor {
    private final UserFactory userFactory;
    private final FirestoreGroupDataAccessObject firestoreGroupDataAccessObject;
    private final JoinGroupPresenter joinGroupPresenter;


    public JoinGroupInteractor(UserFactory userFactory, FirestoreGroupDataAccessObject firestoreGroupDataAccessObject, JoinGroupPresenter joinGroupPresenter) {
        this.userFactory = userFactory;
        this.firestoreGroupDataAccessObject = firestoreGroupDataAccessObject;
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