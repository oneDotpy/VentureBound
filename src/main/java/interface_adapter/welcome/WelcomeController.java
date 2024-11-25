package interface_adapter.welcome;

import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_group.CreateGroupViewModel;
import interface_adapter.join_group.JoinGroupViewModel;
import use_case.welcome.WelcomeInputBoundary;
import use_case.welcome.WelcomeInputData;
import view.JoinGroupView;

import use_case.welcome.WelcomeInteractor;



public class WelcomeController {
    private final WelcomeInputBoundary welcomeInteractor;

    public WelcomeController(WelcomeInputBoundary welcomeInteractor) {
        this.welcomeInteractor = welcomeInteractor;
    }

    public void switchToCreateGroupView(User user){
        WelcomeInputData welcomeInputData = new WelcomeInputData(user);
        welcomeInteractor.switchToCreateGroupView(welcomeInputData);
    }

    public void switchToJoinGroupView(User user){
        WelcomeInputData welcomeInputData = new WelcomeInputData(user);
        welcomeInteractor.switchToJoinGroupView(welcomeInputData);
    }

}

