package use_case.leave_group;

import entity.User;

public class LeaveGroupInputData {
    private final User user;

    public LeaveGroupInputData(User user) {
        this.user = user;
    }

    public User getUser() {return user;}
}
