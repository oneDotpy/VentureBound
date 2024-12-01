import data_access.FirestoreDataAccessObject;
import data_access.FirestoreGroupDataAccessObject;
import data_access.FirestoreUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.Test;
import use_case.login.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    @Test
    void successTest() {
        final FirestoreDataAccessObject firestoreDataAccessObject = new FirestoreDataAccessObject();
        LoginInputData inputData = new LoginInputData("Ken", "1234");
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

        LoginUserDataAccessInterface userRepository = new FirestoreUserDataAccessObject(userFactory, groupRepository);

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData outputData) {
                assertEquals("ken", outputData.getUser().getName());
                System.out.println(outputData.getUser().toString());
            }

            @Override
            public void prepareFailView(String error) {
                System.out.println(error);
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToChatView(LoginOutputData loginOutputData) {

            }

            @Override
            public void switchToWelcomeView(LoginOutputData loginOutputData) {

            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, groupRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failurePasswordIncorrect() {
        final FirestoreDataAccessObject firestoreDataAccessObject = new FirestoreDataAccessObject();
        LoginInputData inputData = new LoginInputData("Ken", "3456");
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

        LoginUserDataAccessInterface userRepository = new FirestoreUserDataAccessObject(userFactory, groupRepository);

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"Ken\".", error);
            }

            @Override
            public void switchToChatView(LoginOutputData loginOutputData) {

            }

            @Override
            public void switchToWelcomeView(LoginOutputData loginOutputData) {

            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, groupRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureUserDoesNotExists() {
        final FirestoreDataAccessObject firestoreDataAccessObject = new FirestoreDataAccessObject();
        LoginInputData inputData = new LoginInputData("Ken", "1234");
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

        LoginUserDataAccessInterface userRepository = new FirestoreUserDataAccessObject(userFactory, groupRepository);

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Ken: Account does not exist.", error);
            }

            @Override
            public void switchToChatView(LoginOutputData loginOutputData) {

            }

            @Override
            public void switchToWelcomeView(LoginOutputData loginOutputData) {

            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, groupRepository,successPresenter);
        interactor.execute(inputData);
    }
}