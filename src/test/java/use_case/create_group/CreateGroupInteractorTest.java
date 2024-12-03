package use_case.create_group;

import data_access.InMemoryGroupDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.*;
import interface_adapter.create_group.CreateGroupPresenter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateGroupInteractorTest {
    GroupFactory groupFactory = new CommonGroupFactory();
    UserFactory userFactory = new CommonUserFactory();


    @Test
    public void successTest() {
        User user = new CommonUser("CreateGroupTesting", "1234", "Create@gmail.com", null);
        CreateGroupInputData inputData = new CreateGroupInputData("CreateGroupTesting", user);

        CreateGroupGroupDataAccessInterface groupRepository = new InMemoryGroupDataAccessObject();
        CreateGroupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
        ((InMemoryUserDataAccessObject) userRepository).save(user);

        CreateGroupOutputBoundary presenter = new CreateGroupOutputBoundary() {
            @Override
            public void prepareChatView(CreateGroupOutputData createGroupOutputData) {
                User user = createGroupOutputData.getUser();
                Group group = createGroupOutputData.getGroup();

                assertEquals("CreateGroupID", user.getGroupID());
                assertEquals("CreateGroupID", group.getGroupID());
                assertEquals("CreateGroupTesting", group.getGroupName());
                assertEquals("CreateGroupID", ((InMemoryUserDataAccessObject) userRepository).get("CreateGroupTesting").getGroupID());
                assertTrue(((InMemoryGroupDataAccessObject) groupRepository).existByID("CreateGroupID"));
            }

            @Override
            public void presentFailView(String message) {
                fail("CreateGroup use case unexpected failure");
            }

            @Override
            public void switchToWelcomeView(CreateGroupOutputData createGroupOutputData) {
                fail("CreateGroup use case unexpected failure");
            }
        };

        CreateGroupInputBoundary createGroupInteractor = new CreateGroupInteractor(groupRepository, userRepository, groupFactory, userFactory, presenter);
        createGroupInteractor.createGroup(inputData);

    }


    @Test
    public void emptyGroupNameTest() {
        User user = new CommonUser("CreateGroupBot", "1234", "Create@gmail.com", null);
        CreateGroupInputData inputData = new CreateGroupInputData("", user);

        CreateGroupGroupDataAccessInterface groupRepository = new InMemoryGroupDataAccessObject();
        CreateGroupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        CreateGroupOutputBoundary presenter = new CreateGroupOutputBoundary() {
            @Override
            public void prepareChatView(CreateGroupOutputData createGroupOutputData) {
                fail("CreateGroup use case empty group name unexpected failure");
            }

            @Override
            public void presentFailView(String message) {
                assertEquals("Empty group name", message);
            }

            @Override
            public void switchToWelcomeView(CreateGroupOutputData createGroupOutputData) {
                fail("CreateGroup use case empty group name unexpected failure");
            }
        };

        CreateGroupInputBoundary createGroupInteractor = new CreateGroupInteractor(groupRepository, userRepository, groupFactory, userFactory, presenter);
        createGroupInteractor.createGroup(inputData);

    }

    @Test
    public void switchToWelcomeViewTest() {
        User user = new CommonUser("CreateGroupBot", "1234", "Create@gmail.com", null);
        CreateGroupInputData inputData = new CreateGroupInputData("CreateGroupTesting", user);

        CreateGroupGroupDataAccessInterface groupRepository = new InMemoryGroupDataAccessObject();
        CreateGroupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        CreateGroupOutputBoundary presenter = new CreateGroupOutputBoundary() {
            @Override
            public void prepareChatView(CreateGroupOutputData createGroupOutputData) {
                fail("CreateGroup use case switchWelcome name unexpected failure");
            }

            @Override
            public void presentFailView(String message) {
                fail("CreateGroup use case switchWelcome name unexpected failure");
            }

            @Override
            public void switchToWelcomeView(CreateGroupOutputData createGroupOutputData) {
                assertEquals(createGroupOutputData.getUser().getGroupID(), "");
                assertNull(createGroupOutputData.getGroup());
            }
        };
        
        CreateGroupInputBoundary interactor = new CreateGroupInteractor(groupRepository, userRepository, groupFactory, userFactory, presenter);
        interactor.switchToWelcomeView(inputData);
    }

}
