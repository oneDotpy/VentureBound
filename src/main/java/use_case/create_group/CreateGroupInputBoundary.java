package use_case.create_group;

import entity.Group;
import entity.User;

public interface CreateGroupInputBoundary {
    /**
     * Create a new group and store the new group in database
     * @param createGroupInputData input data to create a new group
     */
    public void createGroup(CreateGroupInputData createGroupInputData);


    /**
     * Switch to a welcome view
     * @param createGroupInputData input data to switch a new group
     */
    public void switchToWelcomeView(CreateGroupInputData createGroupInputData);
}
