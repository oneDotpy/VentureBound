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
import interface_adapter.create_group.CreateGroupState;
import interface_adapter.create_group.CreateGroupViewModel;
import interface_adapter.group.*;
import interface_adapter.join_group.JoinGroupController;
import interface_adapter.join_group.JoinGroupPresenter;
import interface_adapter.join_group.JoinGroupState;
import interface_adapter.join_group.JoinGroupViewModel;
import interface_adapter.login.*;
import interface_adapter.logout.*;
import interface_adapter.signup.*;
import interface_adapter.welcome.WelcomeController;
import interface_adapter.welcome.WelcomeViewModel;
import use_case.chat.*;
import use_case.create_group.CreateGroupInteractor;
import use_case.group.*;
import use_case.join_group.JoinGroupInteractor;
import use_case.login.*;
import use_case.logout.*;
import use_case.signup.*;
import use_case.vacation_bot.*;
import use_case.welcome.WelcomeInteractor;
import view.*;
import interface_adapter.welcome.*;

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
    private GroupViewModel groupViewModel;
    private WelcomeViewModel welcomeViewModel;
    private CreateGroupViewModel createGroupViewModel;
    private JoinGroupViewModel joinGroupViewModel;

    private ChatState chatState;
    private ChatPresenter chatPresenter;
    private ChatInteractor chatInteractor;
    private VacationBotPresenter vacationBotPresenter;
    private VacationBotInteractor vacationBotInteractor;
    private VacationBotController vacationBotController;
    private ChatController chatController;
    private GroupController groupController;
    private WelcomeController welcomeController;
    private WelcomeState welcomeState;
    private JoinGroupController joinGroupController;
    private CreateGroupController createGroupController;
    private JoinGroupState joinGroupState;
    private CreateGroupState createGroupState;

    private SignupView signupView;
    private LoginView loginView;
    private WelcomeView welcomeView;
    private CreateGroupView createGroupView;
    private JoinGroupView joinGroupView;
    private ChatView chatView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    // Step 1: Add Views
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

    public AppBuilder addGroupViews() {
        createGroupViewModel = new CreateGroupViewModel();
        joinGroupViewModel = new JoinGroupViewModel();
        groupViewModel = new GroupViewModel();
        createGroupView = new CreateGroupView(createGroupViewModel, cardLayout, cardPanel);
        joinGroupView = new JoinGroupView(joinGroupViewModel, cardLayout, cardPanel);

        cardPanel.add(createGroupView, "create-group");
        cardPanel.add(joinGroupView, "join-group");
        return this;
    }

    public AppBuilder addChatView() {
        chatViewModel = new ChatViewModel();
        chatView = new ChatView(chatViewModel, "", new ArrayList<>(), cardLayout, cardPanel);
        cardPanel.add(chatView, "chat");
        return this;
    }
//
//    public AppBuilder addSignupUseCase() {
//        SignupPresenter signupPresenter = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
//        SignupInteractor signupInteractor = new SignupInteractor(firestoreUserDataAccessObject, signupPresenter, userFactory);
//        SignupController signupController = new SignupController(signupInteractor);
//        signupView.setSignupController(signupController);
//        return this;
//    }

//    public AppBuilder addLoginUseCase() {
//        LoginPresenter loginPresenter = new LoginPresenter(viewManagerModel, loggedInViewModel, loginViewModel);
//        LoginInteractor loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter);
//        LoginController loginController = new LoginController(loginInteractor);
//
//        loginView.setLoginController(loginController);
//
//        // Listen for successful login to set the current user
//        loginPresenter.setOnLoginSuccessListener(username -> {
//            if (chatInteractor != null) {
//                chatInteractor.setCurrentUser(username);
//            }
//            if (groupController != null) {
//                groupController.setCurrentUser(username);
//            }
//        });
//        return this;
//    }

    public AppBuilder addWelcomeUseCase() {
        WelcomePresenter welcomePresenter = new WelcomePresenter(viewManagerModel, createGroupViewModel, joinGroupViewModel, welcomeViewModel);
        WelcomeInteractor welcomeInteractor = new WelcomeInteractor(welcomePresenter);
        welcomeController = new WelcomeController(welcomeInteractor);

        welcomeView.setWelcomeController(welcomeController);
        return this;
    }

    public AppBuilder addChatUseCase() {
        chatState = ChatState.getInstance(); // Use the singleton instance
        chatPresenter = new ChatPresenter(viewManagerModel, chatViewModel);
        chatInteractor = new ChatInteractor(chatPresenter, chatState);
        vacationBotPresenter = new VacationBotPresenter(chatViewModel, viewManagerModel);
        vacationBotInteractor = new VacationBotInteractor(vacationBotPresenter, chatInteractor);
        vacationBotController = new VacationBotController(vacationBotInteractor);

        chatController = new ChatController(chatInteractor, vacationBotInteractor);
        chatView.setChatController(chatController);
        return this;
    }


    public AppBuilder addVacationBotUseCase() {
        vacationBotPresenter = new VacationBotPresenter(chatViewModel, viewManagerModel);
        vacationBotInteractor = new VacationBotInteractor(vacationBotPresenter, chatInteractor);
        vacationBotController = new VacationBotController(vacationBotInteractor);
        return this;
    }

    public AppBuilder addGroupUseCase() {
        JoinGroupPresenter joinGroupPresenter = new JoinGroupPresenter(groupFactory, userFactory, viewManagerModel, joinGroupViewModel, chatViewModel);
        CreateGroupPresenter createGroupPresenter = new CreateGroupPresenter(viewManagerModel, chatViewModel, createGroupViewModel);
        GroupPresenter groupPresenter = new GroupPresenter(groupViewModel, chatViewModel, viewManagerModel);
        GroupInteractor groupInteractor = new GroupInteractor(groupPresenter, new GroupState());
        CreateGroupInteractor createGroupInteractor = new CreateGroupInteractor(firestoreGroupDataAccessObject, groupFactory, createGroupPresenter);
        JoinGroupInteractor joinGroupInteractor = new JoinGroupInteractor(groupFactory, userFactory, firestoreGroupDataAccessObject, joinGroupPresenter);
        groupController = new GroupController(groupInteractor);
        joinGroupController = new JoinGroupController(joinGroupInteractor);
        createGroupController = new CreateGroupController(createGroupInteractor);
        joinGroupView.setJoinGroupController(joinGroupController);
        createGroupView.setCreateGroupController(createGroupController);

        // Set current user if already logged in
        groupController.setCurrentUser(chatInteractor.getCurrentUser());

        return this;
    }


    public JFrame build() {
        // Pre-populate test data before adding views
        prePopulateTestData();

        JFrame application = new JFrame("Application");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setSize(1280, 720);
        application.setMinimumSize(new Dimension(1280, 720));
        application.setResizable(false);
        application.setLocationRelativeTo(null);
        application.add(cardPanel);

        viewManagerModel.setState(welcomeView.getViewName());
        viewManagerModel.firePropertyChanged();


        return application;
    }

    // Method to pre-populate data for testing
    private void prePopulateTestData() {
        // Set the current user for testing
        if (chatInteractor != null) {
            chatInteractor.setCurrentUser("Charlie");
            System.out.println("[Debug] Current User set to: Charlie");
        }
        if (groupController != null) {
            groupController.setCurrentUser("Charlie");
        }

        User user = new CommonUser("Patuan", "fd", "Dfadaf", null);
        welcomeState = welcomeViewModel.getState();
        welcomeState.setUser(user);
        // Pre-populate chat members
        ArrayList<String> testMembers = new ArrayList<>();
        testMembers.add("Charlie");


        // Pre-populate a group for testing
        if (groupController != null) {
            groupController.createGroup("Test Group");
            System.out.println("[Debug] Test Group created with members: " + testMembers);
        }
    }
}
