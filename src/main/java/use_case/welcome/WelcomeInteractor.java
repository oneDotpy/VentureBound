package use_case.welcome;

public class WelcomeInteractor implements WelcomeInputBoundary {
    private final WelcomeOutputBoundary welcomePresenter;

    public WelcomeInteractor(WelcomeOutputBoundary welcomePresenter) {
        this.welcomePresenter = welcomePresenter;
    }

    @Override
    public void switchToCreateGroupView() {
        welcomePresenter.switchToCreateGroupView();
    }

    @Override
    public void switchToJoinGroupView() {
        welcomePresenter.switchToJoinGroupView();
    }
}
