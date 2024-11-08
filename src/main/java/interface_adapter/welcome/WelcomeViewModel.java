package interface_adapter.welcome;

import interface_adapter.ViewModel;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.group.GroupState;

public class WelcomeViewModel extends ViewModel<LoggedInState> {
    public static final String TITLE_LABEL = "Sign Up View";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String PASSWORD_LABEL = "Choose password";
    public static final String REPEAT_PASSWORD_LABEL = "Enter password again";

    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public static final String TO_LOGIN_BUTTON_LABEL = "Go to Login";

    public WelcomeViewModel() {
        super("welcome");
        setState(new LoggedInState());
    }
}
