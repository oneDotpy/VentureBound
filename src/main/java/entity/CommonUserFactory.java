package entity;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactory implements UserFactory {

    @Override
    public User create(String name, String password, String email) {
        return new CommonUser(name, password, email, null);
    }

    public User create(String name, String password, String email, Group group) {
        return new CommonUser(name, password, email, group);
    }
}
