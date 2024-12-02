package use_case.send_message;

import data_access.InMemoryGroupDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonMessageFactory;
import entity.MessageFactory;
import org.junit.Test;

public class SendMessageInteractorTest {

    @Test
    public void testSendMessage() {
        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject();
        InMemoryGroupDataAccessObject inMemoryGroupDataAccessObject = new InMemoryGroupDataAccessObject();
        SendMessageInputData inputData = new SendMessageInputData(inMemoryUserDataAccessObject.get("user"), "content");
        MessageFactory messageFactory = new CommonMessageFactory();

        SendMessageInteractor interactor = new SendMessageInteractor(inMemoryUserDataAccessObject,
                inMemoryGroupDataAccessObject, messageFactory);
        interactor.sendMessage(inputData);
    }
}
