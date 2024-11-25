package entity;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;
    private final String password;
    private final String email;
    private final Group group;
    private String groupID;

    public CommonUser(String name, String password, String email, Group group) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.group = group;
        this.groupID = "";
    }

    public CommonUser(String name, String password, String email, Group group, String groupID) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.group = group;
        this.groupID = groupID;
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
    public Group getGroup() { return group; }

    public String getGroupID() {return groupID;}
}
