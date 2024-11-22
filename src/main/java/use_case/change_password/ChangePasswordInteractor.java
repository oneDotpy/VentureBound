package use_case.change_password;

import data_access.FirestoreUserDataAccessObject;
import entity.User;
import entity.UserFactory;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * The Change Password Interactor
 */
public class ChangePasswordInteractor implements ChangePasswordInputBoundary{
    private final ChangePasswordDataAccessInterface userDataAccessObject;
    private final ChangePasswordOutputBoundary changePasswordPresenter;
    private final UserFactory userFactory;

    public ChangePasswordInteractor(ChangePasswordDataAccessInterface userDataAccessObject, ChangePasswordOutputBoundary changePasswordPresenter, UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.changePasswordPresenter = changePasswordPresenter;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(ChangePasswordInputData changePasswordInputData) {
        final String username = changePasswordInputData.getUsername();
        final String currentPassword = changePasswordInputData.getCurrentPassword();
        final String newPassword = changePasswordInputData.getNewPassword();

        final User userDb = userDataAccessObject.get(username);

        if (userDb != null) {
            if (!userDb.getPassword().equals(currentPassword)) {
                changePasswordPresenter.prepareFailView("Current password is invalid");
            } else {
                userDataAccessObject.changePassword(username, newPassword);
                changePasswordPresenter.prepareSuccessView(new ChangePasswordOutputData(false));
            }
        } else {
            changePasswordPresenter.prepareFailView(username + ": Account does not exist.");
        }

    }
}
