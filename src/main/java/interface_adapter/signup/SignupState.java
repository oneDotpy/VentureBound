package interface_adapter.signup;

import entity.Group;

/**
 * The state for the Signup View Model.
 */
public class SignupState {
    private String username = "";
    private String usernameError;
    private String email = "";
    private String passwordError;
    private String password = "";
    private Group group;
    private String repeatPasswordError;

    public String getUsername() {
        return username;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPasswordError() {
        return repeatPasswordError;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRepeatPasswordError(String repeatPasswordError) {
        this.repeatPasswordError = repeatPasswordError;
    }

    public void setGroup(Group group) { this.group = group; }

    public Group getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "SignupState{"
                + "username='" + username + '\''
                + ", password='" + email + '\''
                + ", repeatPassword='" + password + '\''
                + '}';
    }
}
