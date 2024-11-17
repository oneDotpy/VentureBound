package use_case.join_group;

import entity.User;

public interface JoinGroupInputBoundary {
    /**
     * Join group that has already been in database
     * @param groupID groupID of the group
     * @param user the User
     */
    void joinGroup(String groupID, User user);
}
