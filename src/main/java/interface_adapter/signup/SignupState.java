package interface_adapter.signup;

/**
 * The state for the Signup View Model.
 */
public class SignupState {
    private String username = "";
    private String email = "";
    private String password = "";
    private String passwordRepeat = "";
    private String signupError = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getSignupError() {
        return signupError;
    }

    public void setSignupError(String signupError) {
        this.signupError = signupError;
    }
}
