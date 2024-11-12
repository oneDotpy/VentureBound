package use_case.login;

import data_access.FirestoreGroupDataAccessObject;
import data_access.FirestoreUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    @Test
    void successTest() {
        LoginInputData inputData = new LoginInputData("Bob", "1234");
        UserFactory userFactory = new CommonUserFactory();
        GroupFactory groupFactory = new CommonGroupFactory();

        FirestoreGroupDataAccessObject groupRepository = new FirestoreGroupDataAccessObject(groupFactory);

        LoginUserDataAccessInterface userRepository = new FirestoreUserDataAccessObject(userFactory, groupRepository);

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Bob", user.getUsername());
                System.out.println(user);
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