package interface_adapter.join_group;

import interface_adapter.ViewModel;

public class JoinGroupViewModel extends ViewModel<JoinGroupState> {

    public JoinGroupViewModel() {
        super("join-group");
        setState(new JoinGroupState());
    }
}
