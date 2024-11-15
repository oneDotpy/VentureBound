package use_case.signup;

import data_access.FirestoreUserDataAccessObject;
import entity.User;
import entity.UserFactory;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final FirestoreUserDataAccessObject firestoreUserDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SignupInteractor(FirestoreUserDataAccessObject firestoreUserDataAccessObject,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.firestoreUserDataAccessObject = firestoreUserDataAccessObject;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else {
            final User user = userFactory.create(signupInputData.getUsername(), signupInputData.getPassword(), signupInputData.getRepeatPassword());

            firestoreUserDataAccessObject.save(user);

            final SignupOutputData signupOutputData = new SignupOutputData(user.getName(), false);
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
