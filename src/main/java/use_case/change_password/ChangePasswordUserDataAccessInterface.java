package use_case.change_password;

import entity.User;

/**
 * The interface of the DOA for the Change of Password Use case
 */
public interface ChangePasswordUserDataAccessInterface {
    /**
     * Updates the system to record this user's password.
     * @param user the user whose password is to be updated
     */
    void changePassword(User user);
}
