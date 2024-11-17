package use_case.join_group;

import entity.User;

public class JoinGroupInputData {
    private final User user;
    private final String groupID;


    public JoinGroupInputData(User user, String groupID) {
        this.user = user;
        this.groupID = groupID;
    }

    public User getUser() {return user;}

    public String getGroupID() {return groupID;}
}
