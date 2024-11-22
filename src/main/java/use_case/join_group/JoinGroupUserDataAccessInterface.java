package use_case.join_group;

public interface JoinGroupUserDataAccessInterface {
    /**
     * Set GroupID in database
     * @param GroupID groupID from the group
     * @param username username of the user
     */
    public void setGroupID(String GroupID, String username);
}
