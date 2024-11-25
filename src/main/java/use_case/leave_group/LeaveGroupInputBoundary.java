package use_case.leave_group;

public interface LeaveGroupInputBoundary {
    /**
     * Leave group
     * @param leaveGroupInputData input data from state
     */
    void leaveGroup(LeaveGroupInputData leaveGroupInputData);
}
