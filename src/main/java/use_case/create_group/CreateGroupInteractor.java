package use_case.create_group;

import data_access.FirestoreGroupDataAccessObject;
import entity.Group;
import entity.GroupFactory;
import entity.User;
import interface_adapter.create_group.CreateGroupPresenter;
import interface_adapter.welcome.WelcomeState;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupInteractor {
    private final FirestoreGroupDataAccessObject groupDataAccessObject;
    private final GroupFactory groupFactory;
    private final CreateGroupPresenter presenter;


    public CreateGroupInteractor(FirestoreGroupDataAccessObject groupDataAccessObject, GroupFactory groupFactory, CreateGroupPresenter presenter) {
        this.groupDataAccessObject = groupDataAccessObject;
        this.groupFactory = groupFactory;
        this.presenter = presenter;
    }

    public void createGroup(CreateGroupInputData createGroupInputData) {
        if (groupDataAccessObject.existByID(createGroupInputData.getGroupname())){
            presenter.prepareFailView("this name is already exist");
        }
        else {
            List<String> users = new ArrayList<>();
            User user = createGroupInputData.getUser();
            users.add(user.getName());
            Group group = groupFactory.create(createGroupInputData.getGroupname(), users);

            String groupID = groupDataAccessObject.save(group);
            group.setGroupID(groupID);

            user.setGroupID(group.getGroupID());
            CreateGroupOutputData createGroupOutputData = new CreateGroupOutputData(group, user);
            presenter.prepareChatView(createGroupOutputData);
        }
    }

    public void switchToWelcomeView(CreateGroupInputData createGroupInputData) {
        User user = createGroupInputData.getUser();
        CreateGroupOutputData createGroupOutputData = new CreateGroupOutputData(null, user);
        presenter.switchToWelcomeView(createGroupOutputData);
    }

}
