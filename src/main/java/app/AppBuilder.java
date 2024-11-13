package app;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.*;
import interface_adapter.chat.*;
import interface_adapter.group.*;
import interface_adapter.login.*;
import interface_adapter.logout.*;
import interface_adapter.signup.*;
import interface_adapter.welcome.WelcomeViewModel;
import use_case.chat.*;
import use_case.group.*;
import use_case.login.*;
import use_case.logout.*;
import use_case.signup.*;
import use_case.change_password.*;
import use_case.vacation_bot.*;
import view.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();

    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private ChatViewModel chatViewModel;
    private GroupViewModel groupViewModel;
    private WelcomeViewModel welcomeViewModel;

    private ChatState chatState;
    private ChatPresenter chatPresenter;
    private ChatInteractor chatInteractor;
    private VacationBotPresenter vacationBotPresenter;
    private VacationBotInteractor vacationBotInteractor;
    private VacationBotController vacationBotController;
    private ChatController chatController;
    private GroupController groupController;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    // Step 1: Add Views
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        SignupView signupView = new SignupView(signupViewModel, cardLayout, cardPanel);
        cardPanel.add(signupView, "signup");
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        LoginView loginView = new LoginView(loginViewModel, cardLayout, cardPanel);
        cardPanel.add(loginView, "login");
        return this;
    }

    public AppBuilder addWelcomeView() {
        welcomeViewModel = new WelcomeViewModel();
        WelcomeView welcomeView = new WelcomeView(welcomeViewModel, cardLayout, cardPanel);
        cardPanel.add(welcomeView, "welcome");
        return this;
    }

    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        LoggedInView loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addGroupViews() {
        groupViewModel = new GroupViewModel();
        if (groupController == null) {
            addGroupUseCase();
        }
        CreateGroupView createGroupView = new CreateGroupView(groupViewModel, groupController, cardLayout, cardPanel);
        JoinGroupView joinGroupView = new JoinGroupView(groupViewModel, groupController, cardLayout, cardPanel);

        cardPanel.add(createGroupView, "create_group");
        cardPanel.add(joinGroupView, "join_group");
        return this;
    }

    public AppBuilder addChatView() {
        chatViewModel = new ChatViewModel();

        if (chatController == null) {
            addChatUseCase();
        }

        ChatView chatView = new ChatView(chatViewModel, chatController, "Test Group", new ArrayList<>(), cardLayout, cardPanel);
        cardPanel.add(chatView, "chat");
        return this;
    }

    public AppBuilder addSignupUseCase() {
        SignupPresenter signupPresenter = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        SignupInteractor signupInteractor = new SignupInteractor(userDataAccessObject, signupPresenter, userFactory);
        SignupController signupController = new SignupController(signupInteractor);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        LoginPresenter loginPresenter = new LoginPresenter(viewManagerModel, loggedInViewModel, loginViewModel);
        LoginInteractor loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter);
        LoginController loginController = new LoginController(loginInteractor);

        // Set the login controller to the LoginView
        LoginView loginView = (LoginView) cardPanel.getComponent(0); // Assuming login is the second component added
        loginView.setLoginController(loginController);

        // Listen for successful login to set the current user
        loginPresenter.setOnLoginSuccessListener(username -> {
            if (chatInteractor != null) {
                chatInteractor.setCurrentUser(username);
            }
            if (groupController != null) {
                groupController.setCurrentUser(username);
            }
        });
        return this;
    }


    public AppBuilder addChatUseCase() {
        chatState = ChatState.getInstance(); // Use the singleton instance
        chatPresenter = new ChatPresenter(chatViewModel);
        chatInteractor = new ChatInteractor(chatPresenter, chatState);

        vacationBotPresenter = new VacationBotPresenter(chatViewModel);
        vacationBotInteractor = new VacationBotInteractor(vacationBotPresenter, chatInteractor);
        vacationBotController = new VacationBotController(vacationBotInteractor);

        chatController = new ChatController(chatInteractor, vacationBotInteractor);
        return this;
    }


    public AppBuilder addVacationBotUseCase() {
        vacationBotPresenter = new VacationBotPresenter(chatViewModel);
        vacationBotInteractor = new VacationBotInteractor(vacationBotPresenter, chatInteractor);
        vacationBotController = new VacationBotController(vacationBotInteractor);
        return this;
    }

    public AppBuilder addGroupUseCase() {
        GroupPresenter groupPresenter = new GroupPresenter(groupViewModel, chatViewModel);
        GroupInteractor groupInteractor = new GroupInteractor(groupPresenter, new GroupState());
        groupController = new GroupController(groupInteractor);

        // Set current user if already logged in
        if (chatInteractor != null && chatInteractor.getCurrentUser() != null) {
            groupController.setCurrentUser(chatInteractor.getCurrentUser());
        }
        return this;
    }


    public JFrame build() {
        // Pre-populate test data before adding views
        prePopulateTestData();

        JFrame application = new JFrame("Application");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setSize(1280, 720);
        application.setMinimumSize(new Dimension(900, 600));
        application.setResizable(false);
        application.setLocationRelativeTo(null);
        application.add(cardPanel);
        application.setVisible(true);

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

        // Pre-populate chat members
        ArrayList<String> testMembers = new ArrayList<>();
        testMembers.add("Alice");
        testMembers.add("Charlie");

        if (chatInteractor != null) {
            chatInteractor.setMembers(testMembers);
            System.out.println("[Debug] Members pre-populated: " + testMembers);
        }

        // Pre-populate a group for testing
        if (groupController != null) {
            groupController.createGroup("Test Group");
            System.out.println("[Debug] Test Group created with members: " + testMembers);
        }
    }
}
