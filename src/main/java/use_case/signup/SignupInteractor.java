package use_case.signup;

import entity.User;
import entity.UserFactory;

/**
 * The Signup Interactor
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary signupPresenter;
    private final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface userDataAccessObject, SignupOutputBoundary signupOutputBoundary, UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.signupPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        final String username = signupInputData.getUsername();
        final String email = signupInputData.getEmail();
        final String password = signupInputData.getPassword();

        final User userDb = userDataAccessObject.get(username);

        if (userDb != null) {
            // Create a new user object
            User user = userFactory.create(username, password, email);
            userDataAccessObject.save(user);

            final SignupOutputData signupOutputData = new SignupOutputData(
                    user.getName(),
                    user.getEmail(),
                    false);
            signupPresenter.prepareSuccessView(signupOutputData);

        } else {
            signupPresenter.prepareFailView("Username" + username + " is already taken");
        }
    }


}
