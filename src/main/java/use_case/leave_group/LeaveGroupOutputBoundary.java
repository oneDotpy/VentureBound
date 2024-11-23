package use_case.leave_group;

import use_case.join_group.JoinGroupOutputData;

public interface LeaveGroupOutputBoundary {
    /**
     * Switch to welcome view
     * @param response output data from interactor
     */
    void switchToWelcomeView(LeaveGroupOutputData response);
}
