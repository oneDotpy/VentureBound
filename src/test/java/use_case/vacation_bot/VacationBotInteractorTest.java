package use_case.vacation_bot;

import data_access.InMemoryGroupDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VacationBotInteractorTest {
    private final MessageFactory messageFactory = new CommonMessageFactory();
    private final GroupFactory groupFactory = new CommonGroupFactory();
    private final UserFactory userFactory = new CommonUserFactory();

    @Test
    public void testHandleMessage(){
        VacationBotUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
        VacationBotGroupDataAccessInterface groupRepository = new InMemoryGroupDataAccessObject();

        List<String> users = new ArrayList<>();
        users.add("VacationBot");
        Group group = groupFactory.create("VacationTesting", users, "VacationTestingID");
        User user = userFactory.create("VacationBot", "1234", "@gmail.com", group, "VacationTestingID");

        ((InMemoryUserDataAccessObject) userRepository).save(user);
        ((InMemoryGroupDataAccessObject) groupRepository).save(group);

        VacationBotInputBoundary interactor = new VacationBotInteractor(userRepository, groupRepository, messageFactory);
        interactor.startBot("VacationTestingID", 1);
        assertTrue(interactor.isBotActive());

        interactor.handleMessage("VacationTesting", "Indonesia", 1, "VacationTestingID");
        assertTrue(((InMemoryGroupDataAccessObject) groupRepository).get("VacationTestingID").getMessages().size() > 2);

        interactor.handleMessage("VacationTesting", "Hiking", 1, "VacationTestingID");
        assertTrue(((InMemoryGroupDataAccessObject)groupRepository).get("VacationTestingID").getMessages().size() > 5);

        interactor.handleMessage("VacationTesting", "StreetFood", 1, "VacationTestingID");
        interactor.handleMessage("VacationTesting", "Urban", 1, "VacationTestingID");

        assertTrue(((InMemoryGroupDataAccessObject)groupRepository).get("VacationTestingID").getMessages().size() > 10);

        assertTrue(!interactor.isBotActive());
    }
}
