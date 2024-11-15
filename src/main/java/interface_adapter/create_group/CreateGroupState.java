package interface_adapter.create_group;

import entity.User;

public class CreateGroupState {
    private User user;

    public void setUser(User user) {this.user = user;}

    public User setUser() {return user;}
}
