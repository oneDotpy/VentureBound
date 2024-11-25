package interface_adapter.welcome;

import entity.User;

public class WelcomeState {
    private User user;

    public void setUser(User user) {this.user = user;}

    public User getUser() {return user;}
}
