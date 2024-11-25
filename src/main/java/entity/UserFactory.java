package entity;

/**
 * Factory for creating users.
 */
public interface UserFactory {
    /**
     * Creates a new User.
     * @param name the name of the new user
     * @param password the password of the new user
     * @param email the email of the new user
     * @return the new user
     */
    User create(String name, String password, String email);

    /**
     * Creates a new User.
     * @param name the name of the new user
     * @param password the password of the new user
     * @param email the email of the new user
     * @param group the group of the new user
     * @return the new user
     */
    User create(String name, String password, String email, Group group);

    /**
     * Creates a new user that already join a group
     * Creates a new User.
     * @param name the name of the new user
     * @param password the password of the new user
     * @param email the email of the new user
     * @param group the group of the new user
     * @param groupID the groupID of the user
     * @return the new user
     */
    User create(String name, String password, String email, Group group, String groupID);
}
