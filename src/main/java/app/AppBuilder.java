package app;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import data_access.*;
import entity.*;
import interface_adapter.ViewManagerModel;
import interface_adapter.chat.*;
import interface_adapter.create_group.CreateGroupController;
import interface_adapter.create_group.CreateGroupPresenter;
import interface_adapter.create_group.CreateGroupViewModel;
import interface_adapter.group.*;
import interface_adapter.join_group.JoinGroupController;
import interface_adapter.join_group.JoinGroupPresenter;
import interface_adapter.join_group.JoinGroupViewModel;
import interface_adapter.login.*;
import interface_adapter.signup.*;
import interface_adapter.welcome.WelcomeController;
import interface_adapter.welcome.WelcomePresenter;
import interface_adapter.welcome.WelcomeState;
import interface_adapter.welcome.WelcomeViewModel;
import use_case.chat.*;
import use_case.create_group.CreateGroupInteractor;
import use_case.group.*;
import use_case.join_group.JoinGroupInteractor;
import use_case.leave_group.LeaveGroupInteractor;
import use_case.login.*;
import use_case.receive_message.ReceiveMessageInteractor;
import use_case.send_message.SendMessageInteractor;
import use_case.signup.*;
import use_case.change_password.*;
import use_case.vacation_bot.*;
import use_case.welcome.WelcomeInteractor;
import view.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final UserFactory userFactory = new CommonUserFactory();
    private final GroupFactory groupFactory = new CommonGroupFactory();
    private final MessageFactory messageFactory = new CommonMessageFactory();
    private final ResponseFactory responseFactory = new CommonResponseFactory();
    private final RecommendationFactory recommendationFactory = new CommonRecommendationFactory();

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final FirestoreDataAccessObject firestoreDataAccessObject = new FirestoreDataAccessObject();
    private final FirestoreGroupDataAccessObject firestoreGroupDataAccessObject = new FirestoreGroupDataAccessObject(groupFactory, responseFactory, messageFactory, recommendationFactory);
    private final FirestoreUserDataAccessObject firestoreUserDataAccessObject = new FirestoreUserDataAccessObject(userFactory, firestoreGroupDataAccessObject);


    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private ChatViewModel chatViewModel;
    private WelcomeViewModel welcomeViewModel;
    private CreateGroupViewModel createGroupViewModel;
    private JoinGroupViewModel joinGroupViewModel;

    private ChatState chatState;
    private ChatPresenter chatPresenter;
    private ChatInteractor chatInteractor;
    private VacationBotInteractor vacationBotInteractor;
    private ChatController chatController;
    private WelcomeState welcomeState;

    private SignupView signupView;
    private LoginView loginView;
    private WelcomeView welcomeView;
    private CreateGroupView createGroupView;
    private JoinGroupView joinGroupView;
    private ChatView chatView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel, cardLayout, cardPanel);
        cardPanel.add(signupView, "signup");
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel, cardLayout, cardPanel);
        cardPanel.add(loginView, "login");
        return this;
    }

    public AppBuilder addWelcomeView() {
        welcomeViewModel = new WelcomeViewModel();
        welcomeView = new WelcomeView(welcomeViewModel, cardLayout, cardPanel);
        cardPanel.add(welcomeView, "welcome");
        return this;
    }

    public AppBuilder addCreateGroupView() {
        createGroupViewModel = new CreateGroupViewModel();
        createGroupView = new CreateGroupView(createGroupViewModel, cardLayout, cardPanel);
        cardPanel.add(createGroupView, "create-group");
        return this;
    }

    public AppBuilder addJoinGroupView() {
        joinGroupViewModel = new JoinGroupViewModel();
        joinGroupView = new JoinGroupView(joinGroupViewModel, cardLayout, cardPanel);
        cardPanel.add(joinGroupView, "join-group");
        return this;
    }


    public AppBuilder addChatView() {
        chatViewModel = new ChatViewModel();
        chatView = new ChatView(chatViewModel, "Test Group", new ArrayList<>(), cardLayout, cardPanel);
        cardPanel.add(chatView, "chat");
        return this;
    }

    public AppBuilder addSignupUseCase() {
        SignupPresenter signupPresenter = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        SignupInteractor signupInteractor = new SignupInteractor(firestoreUserDataAccessObject, signupPresenter, userFactory);
        SignupController signupController = new SignupController(signupInteractor);
        signupView.setSignupController(signupController);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        LoginPresenter loginPresenter = new LoginPresenter(viewManagerModel, welcomeViewModel, loginViewModel, chatViewModel);
        LoginInteractor loginInteractor = new LoginInteractor(firestoreUserDataAccessObject, firestoreGroupDataAccessObject, loginPresenter);
        LoginController loginController = new LoginController(loginInteractor);

        loginView.setLoginController(loginController);

        // Listen for successful login to set the current user
//        loginPresenter.setOnLoginSuccessListener(username -> {
//            if (chatInteractor != null) {
//                chatInteractor.setCurrentUser(username);
//            }
//            if (groupController != null) {
//                groupController.setCurrentUser(username);
//            }
//        });

        loginPresenter.setOnLoginSuccessListener(username -> {
            if (chatInteractor != null) {
            }
        });
        return this;
    }


    public AppBuilder addChatUseCase() {
        chatState = ChatState.getInstance(); // Use the singleton instance

        chatPresenter = new ChatPresenter(viewManagerModel, chatViewModel, welcomeViewModel);
        chatInteractor = new ChatInteractor(chatPresenter, chatState);
        vacationBotInteractor = new VacationBotInteractor(firestoreUserDataAccessObject, firestoreGroupDataAccessObject, messageFactory);
        ReceiveMessageInteractor receiveMessageInteractor = new ReceiveMessageInteractor(chatPresenter, messageFactory);
        LeaveGroupInteractor leaveGroupInteractor = new LeaveGroupInteractor(firestoreGroupDataAccessObject, firestoreUserDataAccessObject, userFactory, chatPresenter);

        chatViewModel.setChatUpdatesUseCase(new RealTimeChatUpdatesUseCase(firestoreGroupDataAccessObject));
        chatViewModel.setSendMessageInteractor(new SendMessageInteractor(firestoreUserDataAccessObject ,firestoreGroupDataAccessObject, messageFactory));
        chatViewModel.setBotInteractor(vacationBotInteractor);

        chatController = new ChatController(chatInteractor, leaveGroupInteractor, vacationBotInteractor, receiveMessageInteractor);

        chatView.setChatController(chatController);
        chatViewModel.setController(chatController);
        return this;
    }

    public AppBuilder addWelcomeUseCase() {
        WelcomePresenter welcomePresenter = new WelcomePresenter(viewManagerModel, createGroupViewModel, joinGroupViewModel,welcomeViewModel);
        WelcomeInteractor welcomeInteractor = new WelcomeInteractor(welcomePresenter);

        WelcomeController welcomeController = new WelcomeController(welcomeInteractor);
        welcomeView.setWelcomeController(welcomeController);
        return this;
    }

    public AppBuilder addVacationBotUseCase() {
        vacationBotInteractor = new VacationBotInteractor(firestoreUserDataAccessObject, firestoreGroupDataAccessObject, messageFactory);
        return this;
    }

    public AppBuilder addJoinGroupUseCase() {
        JoinGroupPresenter joinGroupPresenter = new JoinGroupPresenter(viewManagerModel,joinGroupViewModel, chatViewModel, welcomeViewModel);
        JoinGroupInteractor joinGroupInteractor = new JoinGroupInteractor(userFactory, firestoreGroupDataAccessObject, firestoreUserDataAccessObject, joinGroupPresenter);
        JoinGroupController joinGroupController = new JoinGroupController(joinGroupInteractor);


        joinGroupView.setJoinGroupController(joinGroupController);
        return this;
    }

    public AppBuilder addCreateGroupUseCase() {
        CreateGroupPresenter createGroupPresenter = new CreateGroupPresenter(viewManagerModel, chatViewModel, createGroupViewModel, welcomeViewModel);
        CreateGroupInteractor createGroupInteractor = new CreateGroupInteractor(firestoreGroupDataAccessObject, firestoreUserDataAccessObject, groupFactory, userFactory, createGroupPresenter);
        CreateGroupController createGroupController = new CreateGroupController(createGroupInteractor);

        createGroupView.setCreateGroupController(createGroupController);
        return this;
    }

    public JFrame build() {

        JFrame application = new JFrame("Application");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setSize(1280, 720);
        application.setMinimumSize(new Dimension(1280, 720));
        application.setResizable(false);
        application.setLocationRelativeTo(null);
        application.add(cardPanel);

        viewManagerModel.setState(loginView.getViewName());

        return application;
    }

}