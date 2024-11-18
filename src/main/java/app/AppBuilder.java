package app;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.text.View;

import data_access.FirestoreGroupDataAccessObject;
import data_access.FirestoreUserDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonGroupFactory;
import entity.CommonUserFactory;
import entity.GroupFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.welcome.WelcomeViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.*;

import interface_adapter.chat.ChatController;
import interface_adapter.chat.ChatPresenter;
import interface_adapter.chat.ChatViewModel;
import use_case.chat.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private final GroupFactory groupFactory = new CommonGroupFactory();
    private final UserFactory userFactory = new CommonUserFactory();

    private final FirestoreGroupDataAccessObject groupDataAccessObject = new FirestoreGroupDataAccessObject(groupFactory);
    private final FirestoreUserDataAccessObject userDataAccessObject = new FirestoreUserDataAccessObject(
            userFactory, groupDataAccessObject
    );

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private ChatViewModel chatViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private WelcomeViewModel welcomeViewModel;
    private WelcomeView welcomeView;
    private JoinGroupView joinGroupView;
    private CreateGroupView  createGroupView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel, cardLayout, cardPanel);
        cardPanel.add(signupView,   signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel, cardLayout, cardPanel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addWelcomeView() {
        welcomeViewModel = new WelcomeViewModel();
        welcomeView =  new WelcomeView(welcomeViewModel, cardLayout, cardPanel);
        cardPanel.add(welcomeView, welcomeView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

//    /**
//     * Adds the Signup Use Case to the application.
//     * @return this builder
//     */
//    public AppBuilder addSignupUseCase() {
//        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
//                signupViewModel, loginViewModel);
//        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
//                userDataAccessObject, signupOutputBoundary, userFactory);
//
//        final SignupController controller = new SignupController(userSignupInteractor);
//        signupView.setSignupController(controller);
//        return this;
//    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

//    /**
//     * Adds the Change Password Use Case to the application.
//     * @return this builder
//     */
//    public AppBuilder addChangePasswordUseCase() {
//        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
//                new ChangePasswordPresenter(loggedInViewModel);
//
//        final ChangePasswordInputBoundary changePasswordInteractor =
//                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);
//
//        final ChangePasswordController changePasswordController =
//                new ChangePasswordController(changePasswordInteractor);
//        loggedInView.setChangePasswordController(changePasswordController);
//        return this;
//    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
//    public AppBuilder addLogoutUseCase() {
//        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
//                loggedInViewModel, loginViewModel);
//
//        final LogoutInputBoundary logoutInteractor =
//                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);
//
//        final LogoutController logoutController = new LogoutController(logoutInteractor);
//        loggedInView.setLogoutController(logoutController);
//        return this;
//    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        // Create view models
        LoginViewModel loginViewModel = new LoginViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        WelcomeViewModel welcomeViewModel = new WelcomeViewModel();
        ChatViewModel chatViewModel = new ChatViewModel();

        // Initialize Presenter
        ChatPresenter chatPresenter = new ChatPresenter(chatViewModel);

        // Initialize Interactor
        ChatInteractor chatInteractor = new ChatInteractor(chatPresenter);

        // Initialize Controller
        ChatController chatController = new ChatController(chatInteractor);

        ArrayList<String> testMembers = new ArrayList<>();
        testMembers.add("Alice");
        testMembers.add("Bob");
        testMembers.add("Charlie");
        chatViewModel.setCurrentUser("Charlie");
        chatViewModel.setMembers(testMembers);


        // Create views, passing cardLayout and cardPanel to enable switching views
        LoginView loginView = new LoginView(loginViewModel, cardLayout, cardPanel);
        SignupView signupView = new SignupView(signupViewModel, cardLayout, cardPanel);
        WelcomeView welcomeView = new WelcomeView(welcomeViewModel, cardLayout, cardPanel);
        JoinGroupView joinGroupView = new JoinGroupView(cardLayout, cardPanel);
        CreateGroupView createGroupView = new CreateGroupView(cardLayout, cardPanel);
        ChatView chatView = new ChatView(chatViewModel, chatController,"Test Group", testMembers, cardLayout, cardPanel);

        // Add views to cardPanel with unique names
        cardPanel.add(loginView, "login");
        cardPanel.add(signupView, "signup");
        cardPanel.add(welcomeView, "welcome");
        cardPanel.add(joinGroupView, "join_group");
        cardPanel.add(createGroupView, "create_group");
        cardPanel.add(chatView, "chat");

        // Create and configure the main application frame
        JFrame application = new JFrame("Application");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set fixed size
        application.setSize(1280, 720);
        application.setMinimumSize(new Dimension(900, 600));
        application.setMaximumSize(new Dimension(900, 600));
        application.setResizable(false); // Prevent resizing

        // Center the window on the screen
        application.setLocationRelativeTo(null);

        // Add cardPanel to the frame and make it visible
        application.add(cardPanel);
        application.setVisible(true);

        return application;
    }
}
