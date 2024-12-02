package use_case.signup;

import entity.User;
import entity.UserFactory;
import use_case.encryption.PasswordEncryption;

/**
 * Interactor for managing the Signup functionality.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary signupPresenter;
    private final UserFactory userFactory;

    /**
     * Constuctor to initialize the SignupInteractor
     * @param userDataAccessObject
     * @param signupOutputBoundary
     * @param userFactory
     */
    public SignupInteractor(SignupUserDataAccessInterface userDataAccessObject, SignupOutputBoundary signupOutputBoundary, UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.signupPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    /**
     * Executes the signup process to sign up the user.
     * @param signupInputData the input data of the signup
     */
    @Override
    public void execute(SignupInputData signupInputData) {
        final String username = signupInputData.getUsername();
        final String email = signupInputData.getEmail();

        if (!signupInputData.getPassword().equals(signupInputData.getPasswordRepeat())) {
            signupPresenter.prepareFailView("Password does not match");

        } else {
            PasswordEncryption passwordEncryption = new PasswordEncryption();
            final String password = passwordEncryption.execute(signupInputData.getPassword());
            final User userDb = userDataAccessObject.get(username);

            if (userDb != null) {

                signupPresenter.prepareFailView("Username " + username + " is already taken");

            } else {
                // Create a new user object
                User user = userFactory.create(username, password, email);
                userDataAccessObject.save(user);

                final SignupOutputData signupOutputData = new SignupOutputData(
                        user.getName(),
                        user.getEmail(),
                        false);
                signupPresenter.prepareSuccessView(signupOutputData);

            }
        }
    }

    /**
     * Switch the view to login.
     */
    @Override
    public void switchToLoginView() {
        signupPresenter.switchToLoginView();
    }

}
