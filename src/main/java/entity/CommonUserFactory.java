package entity;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactory implements UserFactory {

    @Override
    public User create(String name, String password, String email) {
        return new CommonUser(name, password, email, "");
    }

    public User create(String name, String password, String email, String group) {
        return new CommonUser(name, password, email, group);
    }

    public User create(String name, String password) {return null;}
}
