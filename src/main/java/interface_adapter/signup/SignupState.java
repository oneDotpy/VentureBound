package interface_adapter.signup;

/**
 * The state for the Signup View Model.
 */
public class SignupState {
    private String username = "";
    private String usernameError;
    private String email = "";
    private String emailError;
    private String password = "";
    private String passwordError;
    private String passwordRepeat = "";
    private String passwordRepeatError;


    public String getUsername() {
        return username;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public String getEmail() {
        return email;
    }

    public String getEmailError() {
        return emailError;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getRepeatPassword() {
        return passwordRepeat;
    }

    public String getRepeatPasswordError() {
        return passwordRepeatError;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public void setEmail(String email) {this.email = email;}

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.passwordRepeat = repeatPassword;
    }

    public void setRepeatPasswordError(String repeatPasswordError) {
        this.passwordRepeatError = repeatPasswordError;
    }

    @Override
    public String toString() {
        return "SignupState{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + passwordRepeat + '\'' +
                '}';
    }
}
