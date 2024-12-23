package use_case.signup;

import entity.User;

/**
 *  Data Access Interface for the Signup Use Case
 */
public interface SignupUserDataAccessInterface {
    /**
     * Saves the user
     * @param user the user to signup
     * @return the user with the given username
     */
    void save(User user);

    /**
     * Get the user with specified username
     * @param username
     * @return
     */
    User get(String username);
}
