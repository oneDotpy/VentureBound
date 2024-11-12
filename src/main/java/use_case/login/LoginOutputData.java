package use_case.login;

import entity.Group;

import java.util.List;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final String email;
    private final Group group;
    private final boolean useCaseFailed;

    public LoginOutputData(String username, String email, Group group, boolean useCaseFailed) {
        this.username = username;
        this.email = email;
        this.group = group;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Group getGroup() {
        return group;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
