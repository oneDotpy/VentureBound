package interface_adapter.create_group;

import entity.User;

public class CreateGroupState {
    private User user;
    private String groupName;
    private String groupError;

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupError(String groupError) {this.groupError = groupError;}

    public void setUser(User user) {this.user = user;}

    public User getUser() {return user;}

    public String getGroupError() {return groupError;}

    public String getGroupName() {
        return groupName;
    }
}
