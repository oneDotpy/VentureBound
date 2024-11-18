package use_case.join_group;

public interface JoinGroupOutputBoundary {
    void presentChatView(JoinGroupOutputData joinGroupOutputData);

    void presentFailView(String message);

    void switchToWelcomeView(JoinGroupOutputData joinGroupOutputData);
}
