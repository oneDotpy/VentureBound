package interface_adapter.join_group;

import entity.User;

public class JoinGroupState {
    private User user;
    private String groupError;

    public void setGroupError(String groupError) {this.groupError = groupError;}

    public void setUser(User user) {this.user = user;}

    public String getGroupError() {return this.groupError;}

    public User getUser() {return user;}
}
