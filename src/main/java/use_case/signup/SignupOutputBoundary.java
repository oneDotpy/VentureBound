package use_case.signup;
/**
 * The output boundary for actions related to sign up
 */
public interface SignupOutputBoundary {
    /**
     * Prepares the success view for the Signup Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(SignupOutputData outputData);

    /**
     * Prepares the failure view for the Signup Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Prepares the login view for switching to login view.
     */
    void switchToLoginView();
}
