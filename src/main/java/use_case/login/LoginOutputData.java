package use_case.login;

import entity.CommonUser;
import entity.Group;
import entity.User;

import java.util.List;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final User user;
    private final boolean useCaseFailed;

    public LoginOutputData(String username, String email, Group group, boolean useCaseFailed) {
        if (group != null) {
            this.user = new CommonUser(username, "", email, group, group.getGroupID());
        }
        else{
            this.user = new CommonUser(username, "", email, null);
        }
        this.useCaseFailed = useCaseFailed;
    }

    public User getUser() {
        return user;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    public Group getGroup() {
        return user.getGroup();
    }
}
