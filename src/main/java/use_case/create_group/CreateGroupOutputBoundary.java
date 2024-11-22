package use_case.create_group;

public interface CreateGroupOutputBoundary {
    /**
     * Preparing chatView and switch to chatView
     * @param createGroupOutputData Output to be used in presenter
     */
    public void prepareChatView(CreateGroupOutputData createGroupOutputData);

    /**
     * present fail view if there is error from interactor
     * @param message the error message
     */
    public void presentFailView(String message);

    /**
     * Switch to Welcome View
     * @param createGroupOutputData Output data to switch to welcome view
     */
    public void switchToWelcomeView(CreateGroupOutputData createGroupOutputData);
}
