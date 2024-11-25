package use_case.leave_group;

import entity.User;

public class LeaveGroupOutputData {
    private final User user;

    public LeaveGroupOutputData(User user) {
        this.user = user;
    }

    public User getUser() {return user;}
}
