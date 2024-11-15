package use_case.create_group;

import entity.Group;

/**
 * DAO for the Signup Use Case.
 */
public interface CreateGroupDataAccessInterface {

    /**
     * Saves the user.
     * @param group the user to save
     */
    void save(Group group);

    Group get(String groupName);
}
