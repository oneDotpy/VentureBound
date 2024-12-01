package use_case.login;

import data_access.FirestoreGroupGroupDataAccessObject;
import entity.User;
import use_case.encryption.PasswordEncryption;

/**
 * Interactor for managing the Login function
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final FirestoreGroupGroupDataAccessObject groupDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    /**
     * Constructor to initialize the LoginInteractor
     * @param userDataAccessInterface The data access interface for user data
     * @param groupDataAccessObject The data access interface for group data
     * @param loginOutputBoundary The presenter to present the output
     */
    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface, FirestoreGroupDataAccessObject groupDataAccessObject,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.groupDataAccessObject = groupDataAccessObject;
        this.loginPresenter = loginOutputBoundary;
    }

    /**
     * Executes the login process to log in the user
     * @param loginInputData the input data of the login
     */
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
                        groupDataAccessObject.existByID(userDb.getGroupID()));
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }

    public void switchToChatView(LoginOutputData loginOutputData) {

        loginPresenter.switchToChatView(loginOutputData);
    }

    public void switchToLoginView(LoginOutputData loginOutputData) {

        loginPresenter.switchToWelcomeView(loginOutputData);

    }

}
