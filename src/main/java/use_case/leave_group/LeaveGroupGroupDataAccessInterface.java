package use_case.leave_group;

public interface LeaveGroupGroupDataAccessInterface {
    /**
     * Remove username from group with groupID in database
     * @param groupID GroupID input from user
     * @param username Username from the user
     */
    public void removeMember(String groupID, String username);
}
