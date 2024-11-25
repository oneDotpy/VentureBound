package interface_adapter.change_password;

import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInputData;

/**
 * Controller for the Change Password Use Case.
 */
public class ChangePasswordController {
    private final ChangePasswordInputBoundary userChangePasswordUseCaseInteractor;

    public ChangePasswordController(ChangePasswordInputBoundary userChangePasswordUseCaseInteractor) {
        this.userChangePasswordUseCaseInteractor = userChangePasswordUseCaseInteractor;
    }

    /**
     * Executes the Change Password Use Case.
     * @param currentPassword the current password
     * @param newPassword the new password
     * @param username the user whose password to change
     */
    public void execute(String username, String currentPassword, String newPassword) {
        final ChangePasswordInputData changePasswordInputData = new ChangePasswordInputData(username, currentPassword, newPassword);

        userChangePasswordUseCaseInteractor.execute(changePasswordInputData);
    }
}
