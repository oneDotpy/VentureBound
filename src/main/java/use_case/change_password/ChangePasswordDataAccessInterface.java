package use_case.change_password;

import entity.User;

/**
 * The data access interface for change password use case
 */
public interface ChangePasswordDataAccessInterface {
    /**
     * Change the password of user
     * @param username the username to change the password
     * @param password the new password
     */
    void changePassword(String username, String password);

    /**
     * Get the user with specified username
     * @param username
     * @return
     */
    User get(String username);

}
