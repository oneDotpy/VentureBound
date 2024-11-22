package use_case.join_group;

import entity.Group;

public interface JoinGroupDataAccessInterface {
    /**
     * Check whether the ID exist in the database
     * @param groupID GroupID input from user
     * @return True if it exist
     */
    public boolean existByID(String groupID);

    /**
     * Join the group with the groupID in the database
     * @param groupID GroupID input from user
     * @param username Username from the user
     */
    public void join(String groupID, String username);

    /**
     * Get the group with the groupID from the database
     * @param groupID GroupID input from user
     * @return Group Entity
     */
    public Group get(String groupID);
}
