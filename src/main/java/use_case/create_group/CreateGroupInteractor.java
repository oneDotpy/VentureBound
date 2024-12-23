package use_case.create_group;

import entity.Group;
import entity.GroupFactory;
import entity.User;
import entity.UserFactory;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupInteractor implements CreateGroupInputBoundary{
    private final CreateGroupGroupDataAccessInterface groupDataAccessObject;
    private final CreateGroupUserDataAccessInterface userDataAccessObject;
    private final GroupFactory groupFactory;
    private final UserFactory userFactory;
    private final CreateGroupOutputBoundary createGroupPresenter;


    public CreateGroupInteractor(CreateGroupGroupDataAccessInterface groupDataAccessObject,
                                 CreateGroupUserDataAccessInterface userDataAccessObject, GroupFactory groupFactory,
                                 UserFactory userFactory, CreateGroupOutputBoundary createGroupPresenter) {
        this.groupDataAccessObject = groupDataAccessObject;
        this.userDataAccessObject = userDataAccessObject;
        this.groupFactory = groupFactory;
        this.userFactory = userFactory;
        this.createGroupPresenter = createGroupPresenter;
    }

    public void createGroup(CreateGroupInputData createGroupInputData) {
        if ("".equals(createGroupInputData.getGroupname())){
            createGroupPresenter.presentFailView("Empty group name");
        }

        else {
            List<String> users = new ArrayList<>();
            User user = createGroupInputData.getUser();
            users.add(user.getName());
            Group group = groupFactory.create(createGroupInputData.getGroupname(), users);
            String groupID = groupDataAccessObject.save(group);
            Group new_group = groupFactory.create(createGroupInputData.getGroupname(), users, groupID);
            userDataAccessObject.setGroupID(groupID, user.getName());

            String username = user.getName();
            String password = user.getPassword();
            String email = user.getEmail();
            User new_user = userFactory.create(username, password, email, new_group, groupID);

            CreateGroupOutputData createGroupOutputData = new CreateGroupOutputData(new_group, new_user);
            createGroupPresenter.prepareChatView(createGroupOutputData);
        }
    }

    public void switchToWelcomeView(CreateGroupInputData createGroupInputData) {
        User user = createGroupInputData.getUser();
        CreateGroupOutputData createGroupOutputData = new CreateGroupOutputData(null, user);
        createGroupPresenter.switchToWelcomeView(createGroupOutputData);
    }

}
