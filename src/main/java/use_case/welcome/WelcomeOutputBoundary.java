package use_case.welcome;

public interface WelcomeOutputBoundary {
    /**
     * Switch to CreateGroup View
     * @param welcomeOutputData Output data from interactor
     */
    public void switchToCreateGroupView(WelcomeOutputData welcomeOutputData);

    /**
     * Switch to Create JoinGroup View
     * @param welcomeOutputData Output data from interactor
     */
    public void switchToJoinGroupView(WelcomeOutputData welcomeOutputData);
}
