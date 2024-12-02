package use_case.signup;

/**
 * Input Boundary for actions which are related to sign up.
 */
public interface SignupInputBoundary {

    /**
     * Executes the signup use case
     * @param signupInputData the input data
     */
    void execute(SignupInputData signupInputData);

    /**
     * Switch the view of the app to Login View
     */
    void switchToLoginView();
}
