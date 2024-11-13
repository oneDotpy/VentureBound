package app;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.chat.*;
import interface_adapter.group.GroupController;
import interface_adapter.group.GroupPresenter;
import interface_adapter.group.GroupState;
import interface_adapter.group.GroupViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
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

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private ChatViewModel chatViewModel;
    private GroupViewModel groupViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private WelcomeViewModel welcomeViewModel;
    private WelcomeView welcomeView;
    private JoinGroupView joinGroupView;
    private CreateGroupView createGroupView;
    private VacationBotPresenter vacationBotPresenter;
    private VacationBotInteractor vacationBotInteractor;
    private VacationBotController vacationBotController;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel, cardLayout, cardPanel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel, cardLayout, cardPanel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addWelcomeView() {
        welcomeViewModel = new WelcomeViewModel();
        welcomeView = new WelcomeView(welcomeViewModel, cardLayout, cardPanel);
        cardPanel.add(welcomeView, welcomeView.getViewName());
        return this;
    }

    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addSignupUseCase() {
        SignupPresenter signupPresenter = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        SignupInteractor signupInteractor = new SignupInteractor(userDataAccessObject, signupPresenter, userFactory);
        SignupController signupController = new SignupController(signupInteractor);
        signupView.setSignupController(signupController);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        LoginPresenter loginPresenter = new LoginPresenter(viewManagerModel, loggedInViewModel, loginViewModel);
        LoginInteractor loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter);
        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addChangePasswordUseCase() {
        ChangePasswordPresenter changePasswordPresenter = new ChangePasswordPresenter(loggedInViewModel);
        ChangePasswordInteractor changePasswordInteractor = new ChangePasswordInteractor(userDataAccessObject, changePasswordPresenter, userFactory);
        ChangePasswordController changePasswordController = new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    public AppBuilder addLogoutUseCase() {
        LogoutPresenter logoutPresenter = new LogoutPresenter(viewManagerModel, loggedInViewModel, loginViewModel);
        LogoutInteractor logoutInteractor = new LogoutInteractor(userDataAccessObject, logoutPresenter);
        LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    public JFrame build() {
        loginViewModel = new LoginViewModel();
        signupViewModel = new SignupViewModel();
        welcomeViewModel = new WelcomeViewModel();
        chatViewModel = new ChatViewModel();
        groupViewModel = new GroupViewModel();

        ChatState chatState = new ChatState();
        ChatPresenter chatPresenter = new ChatPresenter(chatViewModel, chatState);
        VacationBotPresenter vacationBotPresenter = new VacationBotPresenter(chatViewModel);

        ChatInteractor chatInteractor = new ChatInteractor(chatPresenter, chatState);
        VacationBotInteractor vacationBotInteractor = new VacationBotInteractor(vacationBotPresenter, chatInteractor);
        VacationBotController vacationBotController = new VacationBotController(vacationBotInteractor);
        ChatController chatController = new ChatController(chatInteractor, vacationBotInteractor);

        GroupPresenter groupPresenter = new GroupPresenter(groupViewModel);
        GroupInteractor groupInteractor = new GroupInteractor(groupPresenter, new GroupState());
        GroupController groupController = new GroupController(groupInteractor);

        ArrayList<String> testMembers = new ArrayList<>();
        testMembers.add("Alice");
        testMembers.add("Charlie");
        chatInteractor.setCurrentUser("Charlie");
        chatInteractor.setMembers(testMembers);

        loginView = new LoginView(loginViewModel, cardLayout, cardPanel);
        signupView = new SignupView(signupViewModel, cardLayout, cardPanel);
        welcomeView = new WelcomeView(welcomeViewModel, cardLayout, cardPanel);

        createGroupView = new CreateGroupView(groupViewModel, groupController,cardLayout, cardPanel);
        joinGroupView = new JoinGroupView(groupViewModel, groupController, cardLayout, cardPanel);

        ChatView chatView = new ChatView(chatViewModel, chatController, "Test Group", testMembers, cardLayout, cardPanel);

        cardPanel.add(loginView, "login");
        cardPanel.add(signupView, "signup");
        cardPanel.add(welcomeView, "welcome");
        cardPanel.add(joinGroupView, "join_group");
        cardPanel.add(createGroupView, "create_group");
        cardPanel.add(chatView, "chat");

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
}
