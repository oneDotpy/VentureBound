package use_case.join_group;

import entity.Group;
import entity.User;

public class JoinGroupOutputData {
    private final Group group;
    private final User user;


    public JoinGroupOutputData(Group group, User user) {
        this.group = group;
        this.user = user;
    }

    public Group getGroup() {return group;}

    public User getUser() {return user;}
}
