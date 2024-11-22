package use_case.welcome;

import entity.User;

public class WelcomeOutputData {
    private final User user;

    public WelcomeOutputData(User user) {
        this.user = user;
    }

    public User getUser() {return user;}
}
