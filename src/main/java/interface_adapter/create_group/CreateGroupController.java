package interface_adapter.create_group;

import entity.User;
import use_case.create_group.CreateGroupInputData;
import use_case.create_group.CreateGroupInteractor;

public class CreateGroupController {
    private final CreateGroupInteractor createGroupInteractor;


    public CreateGroupController(CreateGroupInteractor createGroupInteractor) {
        this.createGroupInteractor = createGroupInteractor;
    }

    public void createGroup(String groupName, User user) {
        CreateGroupInputData createGroupInputData = new CreateGroupInputData(groupName, user);
        createGroupInteractor.createGroup(createGroupInputData);
    }
}
