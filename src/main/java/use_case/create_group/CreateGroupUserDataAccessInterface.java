package use_case.create_group;

public interface CreateGroupUserDataAccessInterface {
    /**
     * Set GroupID in database
     * @param GroupID groupID from the group
     * @param username username of the user
     */
    public void setGroupID(String GroupID, String username);
}
