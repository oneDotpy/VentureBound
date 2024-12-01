package use_case.receive_message;

import com.google.cloud.Timestamp;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonMessageFactory;
import entity.MessageFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatPresenter;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.welcome.WelcomeViewModel;
import org.junit.Test;

public class ReceiveMessageInteractorTest {

    @Test
    public void testReceiveMessageSelf() {
        // create inputData with sender name "user" and current user name "user"
        ReceiveMessageInputData inputData = new ReceiveMessageInputData("user",
                "content", "user", Timestamp.now());

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ChatViewModel chatViewModel = new ChatViewModel();

        // create a user with name "user"
        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject();
        ChatState chatState = ChatState.getInstance();
        chatState.setCurrentUser(inMemoryUserDataAccessObject.get("user"));

        WelcomeViewModel welcomeViewModel = new WelcomeViewModel();

        ReceiveMessageOutputBoundary presenter = new ChatPresenter(viewManagerModel, chatViewModel, welcomeViewModel);

        MessageFactory messageFactory = new CommonMessageFactory();

        ReceiveMessageInteractor interactor = new ReceiveMessageInteractor(presenter, messageFactory);
        interactor.receiveMessage(inputData);
    }

    @Test
    public void testReceiveMessageOther() {
        // create inputData with sender name "sender" and current user name "user"
        ReceiveMessageInputData inputData = new ReceiveMessageInputData("sender",
                "content", "user", Timestamp.now());

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ChatViewModel chatViewModel = new ChatViewModel();

        InMemoryUserDataAccessObject inMemoryUserDataAccessObject = new InMemoryUserDataAccessObject();
        ChatState chatState = ChatState.getInstance();
        chatState.setCurrentUser(inMemoryUserDataAccessObject.get("user"));

        WelcomeViewModel welcomeViewModel = new WelcomeViewModel();

        ReceiveMessageOutputBoundary presenter = new ChatPresenter(viewManagerModel, chatViewModel, welcomeViewModel);

        MessageFactory messageFactory = new CommonMessageFactory();

        ReceiveMessageInteractor interactor = new ReceiveMessageInteractor(presenter, messageFactory);
        interactor.receiveMessage(inputData);
    }
}
