package use_case.change_password;

/**
 * The output boundary for actions related to changing password
 */
public interface ChangePasswordOutputBoundary {
    /**
     * Prepares the success view for the Change Password Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(use_case.change_password.ChangePasswordOutputData outputData);

    /**
     * Prepares the failure view for the Change Password Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

}
