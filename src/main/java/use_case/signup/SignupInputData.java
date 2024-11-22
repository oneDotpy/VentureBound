package use_case.signup;

/**
 * Input data for the Signup Use Case
 */
public class SignupInputData {
    private final String username;
    private final String email;
    private final String password;
    private final String passwordRepeat;

    public SignupInputData(String username, String email, String password, String passwordRepeat) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordRepeat = passwordRepeat;

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordRepeat() {
        return this.passwordRepeat;
    }
}
