package use_case.join_group;

import entity.User;

public interface JoinGroupInputBoundary {
    /**
     * Join group that has already been in database
     * @param joinGroupInputData input data from user
     */
    void joinGroup(JoinGroupInputData joinGroupInputData);

    /**
     * Switch to Welcome View
     * @param joinGroupInputData input data from user
     */
    void switchToWelcomeView(JoinGroupInputData joinGroupInputData);
}
