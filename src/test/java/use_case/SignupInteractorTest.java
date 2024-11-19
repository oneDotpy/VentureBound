package use_case;

import data_access.FirestoreGroupDataAccessObject;
import data_access.FirestoreUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.Test;
import use_case.login.*;
import use_case.signup.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SignupInteractorTest {

    @Test
    void successTest() {
        SignupInputData inputData = new SignupInputData("Ken2", "ken@mail.com", "1234", "1234");
        UserFactory userFactory = new CommonUserFactory();
        GroupFactory groupFactory = new CommonGroupFactory();
        ResponseFactory resposeFactory = new CommonResponseFactory();
        MessageFactory messageFactory = new CommonMessageFactory();
        RecommendationFactory recommendationFactory = new CommonRecommendationFactory();

        FirestoreGroupDataAccessObject groupRepository = new FirestoreGroupDataAccessObject(
                groupFactory,
                resposeFactory,
                messageFactory,
                recommendationFactory);

        SignupUserDataAccessInterface userRepository = new FirestoreUserDataAccessObject(userFactory, groupRepository);

        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {

            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                System.out.println("Success");
            }

            @Override
            public void prepareFailView(String error) {
                System.out.println(error);
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToLoginView() {

            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, successPresenter, userFactory);
        interactor.execute(inputData);
    }
}
