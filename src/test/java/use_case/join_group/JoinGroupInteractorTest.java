package use_case.join_group;

import data_access.InMemoryGroupDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JoinGroupInteractorTest {
    GroupFactory groupFactory = new CommonGroupFactory();
    UserFactory userFactory = new CommonUserFactory();

    @Test
    public void successTest() {
        User user = new CommonUser("JoinGroupTesting", "1234", "Create@gmail.com", null);
        Group group = groupFactory.create("JoinGroupGroup", new ArrayList<>(), "JoinGroupID");

        JoinGroupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
        JoinGroupGroupDataAccessInterface groupRepository = new InMemoryGroupDataAccessObject();
        ((InMemoryGroupDataAccessObject)groupRepository).save(group);
        ((InMemoryUserDataAccessObject)userRepository).save(user);

        JoinGroupInputData inputData = new JoinGroupInputData(user, "JoinGroupID");
        JoinGroupOutputBoundary presenter = new JoinGroupOutputBoundary() {
            @Override
            public void presentChatView(JoinGroupOutputData joinGroupOutputData) {
                User user = joinGroupOutputData.getUser();
                Group group = joinGroupOutputData.getGroup();

                assertEquals(user.getGroupID(), "JoinGroupID");
                assertTrue(group.getUsernames().contains("JoinGroupTesting"));
                assertTrue(groupRepository.get("JoinGroupID").getUsernames().contains("JoinGroupTesting"));
                assertEquals(((InMemoryUserDataAccessObject)userRepository).get("JoinGroupTesting").getGroupID(), "JoinGroupID");
            }

            @Override
            public void presentFailView(String message) {
                fail("Unexpected failure Join Group use Case");
            }

            @Override
            public void switchToWelcomeView(JoinGroupOutputData joinGroupOutputData) {
                fail("Unexpected failure Join Group use Case");
            }
        };
        JoinGroupInputBoundary joinGroupInteractor = new JoinGroupInteractor(userFactory, groupRepository, userRepository, presenter);
        joinGroupInteractor.joinGroup(inputData);
    }

    @Test
    public void emptyGroupID() {
        User user = new CommonUser("JoinGroupTesting", "1234", "Create@gmail.com", null);

        JoinGroupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
        JoinGroupGroupDataAccessInterface groupRepository = new InMemoryGroupDataAccessObject();
        ((InMemoryUserDataAccessObject)userRepository).save(user);

        JoinGroupInputData inputData = new JoinGroupInputData(user, "");
        JoinGroupOutputBoundary presenter = new JoinGroupOutputBoundary() {
            @Override
            public void presentChatView(JoinGroupOutputData joinGroupOutputData) {
                fail("Unexpected failure Join Group use Case");
            }

            @Override
            public void presentFailView(String message) {
                assertEquals("Empty Group ID", message);
            }

            @Override
            public void switchToWelcomeView(JoinGroupOutputData joinGroupOutputData) {
                fail("Unexpected failure Join Group use Case");
            }
        };

        JoinGroupInputBoundary joinGroupInteractor = new JoinGroupInteractor(userFactory, groupRepository, userRepository, presenter);
        joinGroupInteractor.joinGroup(inputData);
    }

    @Test
    public void notValidID() {
        User user = new CommonUser("JoinGroupTesting", "1234", "Create@gmail.com", null);

        JoinGroupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
        JoinGroupGroupDataAccessInterface groupRepository = new InMemoryGroupDataAccessObject();
        ((InMemoryUserDataAccessObject)userRepository).save(user);

        JoinGroupInputData inputData = new JoinGroupInputData(user, "INVALID");
        JoinGroupOutputBoundary presenter = new JoinGroupOutputBoundary() {
            @Override
            public void presentChatView(JoinGroupOutputData joinGroupOutputData) {
                fail("Unexpected failure Join Group use Case");
            }

            @Override
            public void presentFailView(String message) {
                assertEquals("Invalid GroupID", message);
            }

            @Override
            public void switchToWelcomeView(JoinGroupOutputData joinGroupOutputData) {
                fail("Unexpected failure Join Group use Case");
            }
        };

        JoinGroupInputBoundary joinGroupInteractor = new JoinGroupInteractor(userFactory, groupRepository, userRepository, presenter);
        joinGroupInteractor.joinGroup(inputData);
    }
}
