package use_case.login;

import entity.Group;
import entity.User;

/**
 * DAO for the Login Use Case.
 */

public interface LoginGroupDataAccessInterface {
    /**
     * Returns the user with the given username.
     * @param groupId the groupId to look up
     * @return the user with the given username
     */
    Group get(String groupId);

    boolean existByID(String groupID);
}
