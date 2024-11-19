package use_case;

import data_access.FirestoreGroupDataAccessObject;
import data_access.FirestoreUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.Test;
import use_case.login.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    @Test
    void successTest() {
        LoginInputData inputData = new LoginInputData("Ken2", "1234");
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
                assertEquals("ken@mail.com", outputData.getUser().getEmail());
                System.out.println(outputData.getUser().toString());
            }

            @Override
            public void prepareFailView(String error) {
                System.out.println(error);
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }
}