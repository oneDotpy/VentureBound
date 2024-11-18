package use_case.create_group;

import entity.Group;
import entity.User;

public interface CreateGroupInputBoundary {
    public void createGroup(String groupName, User user);
}
