package use_case.login;

import entity.User;
import use_case.encryption.PasswordEncryption;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();
        final User userDb = userDataAccessObject.get(username);

        if (userDb == null) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            PasswordEncryption passwordEncryption = new PasswordEncryption();

            if (!passwordEncryption.verify(password, userDb.getPassword())) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {
                final LoginOutputData loginOutputData = new LoginOutputData(
                        userDb.getName(),
                        userDb.getEmail(),
                        userDb.getGroup(),
                        false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}
