package use_case.create_group;

import entity.Group;
import entity.User;

public class CreateGroupOutputData {
    private final Group group;
    private final User user;


    public CreateGroupOutputData(Group group, User user) {
        this.group = group;
        this.user = user;
    }

    public Group getGroup() {return group;}

    public User getUser() {return user;}
}
