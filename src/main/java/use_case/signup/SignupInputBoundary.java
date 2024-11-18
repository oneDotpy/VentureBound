package use_case.signup;

/**
 * Input Boundary for actions which are related to sign up.
 */
public interface SignupInputBoundary {

    /**
     * Executes the signup usecase
     * @param signupInputData the input data
     */
    void execute(SignupInputData signupInputData);
}
