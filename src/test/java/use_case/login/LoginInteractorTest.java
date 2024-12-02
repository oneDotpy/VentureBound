package use_case.login;

import data_access.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LoginInteractorTest {
    @Test
    void successTest() {
        LoginInputData inputData = new LoginInputData("user", "password");

        InMemoryGroupDataAccessObject groupRepository = new InMemoryGroupDataAccessObject();

        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData outputData) {
                assertEquals("user", outputData.getUser().getName());
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
        LoginInputData inputData = new LoginInputData("user", "password1");

        InMemoryGroupDataAccessObject groupRepository = new InMemoryGroupDataAccessObject();

        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"user\".", error);
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
        LoginInputData inputData = new LoginInputData("user1", "password1");

        InMemoryGroupDataAccessObject groupRepository = new InMemoryGroupDataAccessObject();

        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("user1: Account does not exist.", error);
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
}
