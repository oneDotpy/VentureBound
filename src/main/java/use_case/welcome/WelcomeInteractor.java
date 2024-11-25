package use_case.welcome;

public class WelcomeInteractor implements WelcomeInputBoundary {
    private final WelcomeOutputBoundary welcomePresenter;

    public WelcomeInteractor(WelcomeOutputBoundary welcomePresenter) {
        this.welcomePresenter = welcomePresenter;
    }

    @Override
    public void switchToCreateGroupView(WelcomeInputData welcomeInputData) {
        WelcomeOutputData welcomeOutputData = new WelcomeOutputData(welcomeInputData.getUser());
        welcomePresenter.switchToCreateGroupView(welcomeOutputData);
    }

    @Override
    public void switchToJoinGroupView(WelcomeInputData welcomeInputData) {
        WelcomeOutputData welcomeOutputData = new WelcomeOutputData(welcomeInputData.getUser());
        welcomePresenter.switchToJoinGroupView(welcomeOutputData);
    }
}
