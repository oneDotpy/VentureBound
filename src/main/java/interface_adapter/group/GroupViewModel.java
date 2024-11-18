package interface_adapter.group;

import interface_adapter.ViewModel;

public class GroupViewModel extends ViewModel<GroupState> {

    public GroupViewModel() {
        super("group");
        setState(new GroupState());
    }
}
