package interface_adapter.create_group;

import entity.User;
import use_case.create_group.CreateGroupInputBoundary;
import use_case.create_group.CreateGroupInputData;

/**
 * Controller that control interaction between view and interactor
 */
public class CreateGroupController {
    private final CreateGroupInputBoundary createGroupInteractor;


    public CreateGroupController(CreateGroupInputBoundary createGroupInteractor) {
        this.createGroupInteractor = createGroupInteractor;
    }

    public void createGroup(String groupName, User user) {
        CreateGroupInputData createGroupInputData = new CreateGroupInputData(groupName, user);
        createGroupInteractor.createGroup(createGroupInputData);
    }

    public void switchToLoginWelcomeView(User user) {
        CreateGroupInputData createGroupInputData = new CreateGroupInputData("", user);
        createGroupInteractor.switchToWelcomeView(createGroupInputData);
    }
}
