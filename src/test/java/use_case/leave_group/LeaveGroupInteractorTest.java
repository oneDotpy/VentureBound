package use_case.leave_group;

import data_access.InMemoryGroupDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeaveGroupInteractorTest {
    GroupFactory groupFactory = new CommonGroupFactory();
    UserFactory userFactory = new CommonUserFactory();

    @Test
    public void successTest() {
        List<String> users = new ArrayList<>();
        users.add("LeaveGroupTesting");
        Group group = groupFactory.create("LeaveGroupGroup", users, "LeaveGroupID");
        User user = new CommonUser("LeaveGroupTesting", "1234", "Leave@gmail.com", group, group.getGroupID());

        LeaveGroupGroupDataAccessInterface groupRepository = new InMemoryGroupDataAccessObject();
        LeaveGroupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
        ((InMemoryGroupDataAccessObject)groupRepository).save(group);
        ((InMemoryUserDataAccessObject)userRepository).save(user);

        LeaveGroupInputData inputData = new LeaveGroupInputData(user);
        LeaveGroupOutputBoundary presenter = new LeaveGroupOutputBoundary() {
            @Override
            public void switchToWelcomeView(LeaveGroupOutputData response) {
                assertTrue(!((InMemoryGroupDataAccessObject)groupRepository).get("LeaveGroupID").getUsernames().contains("LeaveGroupTesting"));
                assertEquals(response.getUser().getName(), "LeaveGroupTesting");
            }
        };

        LeaveGroupInputBoundary interactor = new LeaveGroupInteractor(groupRepository, userRepository, userFactory, presenter);
        interactor.leaveGroup(inputData);
    }
}
