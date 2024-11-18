package interface_adapter.welcome;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_group.CreateGroupViewModel;
import interface_adapter.join_group.JoinGroupViewModel;
import view.JoinGroupView;

import use_case.welcome.WelcomeInteractor;



public class WelcomeController {
    private final WelcomeInteractor welcomeInteractor;

    public WelcomeController(WelcomeInteractor welcomeInteractor) {
        this.welcomeInteractor = welcomeInteractor;
    }

    public void switchToCreateGroupView(){
        welcomeInteractor.switchToCreateGroupView();
    }

    public void switchToJoinGroupView(){
        welcomeInteractor.switchToJoinGroupView();
    }

}

