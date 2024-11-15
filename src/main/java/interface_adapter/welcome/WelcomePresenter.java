package interface_adapter.welcome;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_group.CreateGroupState;
import interface_adapter.create_group.CreateGroupViewModel;
import interface_adapter.join_group.JoinGroupState;
import interface_adapter.join_group.JoinGroupViewModel;
import use_case.welcome.WelcomeOutputBoundary;

public class WelcomePresenter implements WelcomeOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final CreateGroupViewModel createGroupViewModel;
    private final JoinGroupViewModel joinGroupViewModel;
    private final WelcomeViewModel welcomeViewModel;

    public WelcomePresenter(ViewManagerModel viewManagerModel, CreateGroupViewModel createGroupViewModel, JoinGroupViewModel joinGroupViewModel, WelcomeViewModel welcomeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.createGroupViewModel = createGroupViewModel;
        this.joinGroupViewModel = joinGroupViewModel;
        this.welcomeViewModel = welcomeViewModel;
    }

    @Override
    public void switchToCreateGroupView() {
        CreateGroupState createGroupState = createGroupViewModel.getState();
        createGroupState.setUser(welcomeViewModel.getState().getUser());
        viewManagerModel.setState(createGroupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToJoinGroupView() {
        JoinGroupState joinGroupState = joinGroupViewModel.getState();
        joinGroupState.setUser(welcomeViewModel.getState().getUser());
        viewManagerModel.setState(joinGroupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
