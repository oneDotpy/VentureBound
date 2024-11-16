package use_case.create_group;

import entity.User;

public class CreateGroupInputData {
    private final User user;
    private final String groupname;

    public User getUser() {
        return user;
    }

    public String getGroupname() {
        return groupname;
    }


    public CreateGroupInputData(String groupName, User user) {
        this.user = user;
        this.groupname = groupName;
    }


}
