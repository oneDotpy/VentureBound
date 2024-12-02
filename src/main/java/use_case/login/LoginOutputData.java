package use_case.login;

import entity.CommonUser;
import entity.Group;
import entity.User;

import java.util.List;

/**
 * Output data class for login.
 * Contains the user object and the login status
 */
public class LoginOutputData {

    private final User user;
    private final boolean useCaseFailed;

    /**
     * Constructor for new instance of LoginOutputData.
     * @param username The username of the user logging in.
     * @param email The email of the user logging in.
     * @param group The group of the user logging in.
     * @param useCaseFailed The state of the login.
     */
    public LoginOutputData(String username, String email, Group group, boolean useCaseFailed) {
        if (group != null) {
            this.user = new CommonUser(username, "", email, group, group.getGroupID());
        }
        else{
            this.user = new CommonUser(username, "", email, null);
        }
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Gets the user object of the user logging in
     * @return The user object obtained after logging in
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets the group object of the user after they log in
     * @return The group object of the user logging in
     */
    public Group getGroup() {
        return user.getGroup();
    }
}
