package use_case.leave_group;

public interface LeaveGroupUserDataAccessInterface {
    /**
     * Set GroupID in database
     * @param GroupID groupID from the group
     * @param username username of the user
     */
    public void setGroupID(String GroupID, String username);
}
