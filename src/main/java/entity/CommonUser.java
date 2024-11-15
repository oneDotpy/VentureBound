package entity;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;
    private final String password;
    private final String email;
    private final String group;

    public CommonUser(String name, String password, String email, String group) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.group = group;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getEmail() { return email; }

    @Override
    public String getGroup() { return group; }

}
