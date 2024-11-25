package use_case.welcome;

public interface WelcomeInputBoundary {
    /**
     * Switch to Create Group View
     * @param welcomeInputData inputData from state
     */
    public void switchToCreateGroupView(WelcomeInputData welcomeInputData);

    /**
     * Switch to Join Group View
     * @param welcomeInputData inputData from state
     */
    public void switchToJoinGroupView(WelcomeInputData welcomeInputData);
}
