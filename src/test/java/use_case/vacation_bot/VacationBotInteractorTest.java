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
        users.add("VacationBotTemp1");
        Group group = groupFactory.create("VacationTesting", users, "VacationTestingID");
        User user = userFactory.create("VacationBot", "1234", "@gmail.com", group, "VacationTestingID");

        ((InMemoryUserDataAccessObject) userRepository).save(user);
        ((InMemoryGroupDataAccessObject) groupRepository).save(group);


        VacationBotInputBoundary interactor = new VacationBotInteractor(userRepository, groupRepository, messageFactory);
        VacationBotInputData vacationBotInputData = new VacationBotInputData("VacationTestingID", 1);
        interactor.startBot(vacationBotInputData);
        assertTrue(interactor.isBotActive());

        interactor.setThreshold(3);
        interactor.handleMessage("VacationBotTemp2", "Bolivia", 3, "VacationTestingID");
        interactor.handleMessage("VacationBot", "Indonesia", 3, "VacationTestingID");
        interactor.removeResponse(users);
        interactor.setThreshold(2);
        interactor.handleMessage("VacationBotTemp1", "Bolivia", 2, "VacationTestingID");
        assertTrue(((InMemoryGroupDataAccessObject) groupRepository).get("VacationTestingID").getMessages().size() > 2);


        users.remove("VacationBotTemp1");
        interactor.handleMessage("VacationBot", "Hiking", 2, "VacationTestingID");
        assertTrue(((InMemoryGroupDataAccessObject)groupRepository).get("VacationTestingID").getMessages().size() > 5);
        interactor.removeResponse(users);
        interactor.setThreshold(1);

        interactor.handleMessage("VacationBot", "StreetFood", 1, "VacationTestingID");
        interactor.handleMessage("VacationBot", "Urban", 1, "VacationTestingID");

        assertTrue(((InMemoryGroupDataAccessObject)groupRepository).get("VacationTestingID").getMessages().size() > 10);
        assertTrue(!interactor.isBotActive());
    }

    @Test
    public void testStopBot(){
        VacationBotUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
        VacationBotGroupDataAccessInterface groupRepository = new InMemoryGroupDataAccessObject();

        List<String> users = new ArrayList<>();
        users.add("VacationBot");
        Group group = groupFactory.create("VacationTesting", users, "VacationTestingID");
        User user = userFactory.create("VacationBot", "1234", "@gmail.com", group, "VacationTestingID");

        ((InMemoryUserDataAccessObject) userRepository).save(user);
        ((InMemoryGroupDataAccessObject) groupRepository).save(group);

        VacationBotInputBoundary interactor = new VacationBotInteractor(userRepository, groupRepository, messageFactory);
        VacationBotInputData vacationBotInputData = new VacationBotInputData("VacationTestingID", 1);
        interactor.startBot(vacationBotInputData);
        assertTrue(interactor.isBotActive());
        interactor.stopBot();
        assertTrue(!interactor.isBotActive());
    }

    @Test
    public void testStartBotTwice(){
        VacationBotUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
        VacationBotGroupDataAccessInterface groupRepository = new InMemoryGroupDataAccessObject();

        List<String> users = new ArrayList<>();
        users.add("VacationBot");
        Group group = groupFactory.create("VacationTesting", users, "VacationTestingID");
        User user = userFactory.create("VacationBot", "1234", "@gmail.com", group, "VacationTestingID");

        ((InMemoryUserDataAccessObject) userRepository).save(user);
        ((InMemoryGroupDataAccessObject) groupRepository).save(group);

        VacationBotInputBoundary interactor = new VacationBotInteractor(userRepository, groupRepository, messageFactory);
        VacationBotInputData vacationBotInputData = new VacationBotInputData("VacationTestingID", 1);
        interactor.startBot(vacationBotInputData);
        assertTrue(interactor.isBotActive());
        interactor.startBot(vacationBotInputData);
        assertTrue(((InMemoryGroupDataAccessObject) groupRepository).get("VacationTestingID").getMessages().size() > 1);
    }

    @Test
    public void testSendGroupID(){
        VacationBotUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
        VacationBotGroupDataAccessInterface groupRepository = new InMemoryGroupDataAccessObject();

        List<String> users = new ArrayList<>();
        users.add("VacationBot");
        Group group = groupFactory.create("VacationTesting", users, "VacationTestingID");
        User user = userFactory.create("VacationBot", "1234", "@gmail.com", group, "VacationTestingID");

        ((InMemoryUserDataAccessObject) userRepository).save(user);
        ((InMemoryGroupDataAccessObject) groupRepository).save(group);

        VacationBotInputBoundary interactor = new VacationBotInteractor(userRepository, groupRepository, messageFactory);
        VacationBotInputData vacationBotInputData = new VacationBotInputData("VacationTestingID", 1);
        interactor.startBot(vacationBotInputData);

        interactor.sendGroupID("VacationTestingID");
        assertTrue(((InMemoryGroupDataAccessObject) groupRepository).get("VacationTestingID").getMessages().size() > 1);
    }

}
