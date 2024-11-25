package use_case.join_group;

public interface JoinGroupOutputBoundary {
    /**
     * Prepare chatView
     * @param joinGroupOutputData Output data from interactor
     */
    void presentChatView(JoinGroupOutputData joinGroupOutputData);

    /**
     * Prepare fail View with given error message if there is error from interactor
     * @param message error message from interactor
     */
    void presentFailView(String message);

    /**
     * Switch to welcome view
     * @param joinGroupOutputData output data from interactor
     */
    void switchToWelcomeView(JoinGroupOutputData joinGroupOutputData);
}
