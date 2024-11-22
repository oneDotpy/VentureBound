package use_case.welcome;

import entity.User;

public class WelcomeInputData {
    private final User user;


    public WelcomeInputData(User user) {
        this.user = user;
    }

    public User getUser() {return user;}
}
