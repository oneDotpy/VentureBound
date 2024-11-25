package use_case.change_password;

/**
 * The input boundary for actions which are related to changing password
 */
public interface ChangePasswordInputBoundary {

    /**
     * Executes the change password use case
     * @param changePasswordInputData the input data
     */
    void execute(ChangePasswordInputData changePasswordInputData);
}
