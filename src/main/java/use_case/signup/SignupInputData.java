package use_case.signup;

/**
 * Input data for the Signup Use Case
 */
public class SignupInputData {
    private final String username;
    private final String email;
    private final String password;
    private final String passwordRepeat;

    /**
     *
     * @param username The username of the user signing up
     * @param email The email of the user signing up
     * @param password The password of the user signing up
     * @param passwordRepeat The repeat password of the user signing up for confirmation
     */
    public SignupInputData(String username, String email, String password, String passwordRepeat) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordRepeat = passwordRepeat;

    }

    /**
     * Gets the username of the person signing up
     * @return The username of the person signing up
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the email of the person signing up
     * @return The email of the person signing up
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the password of the person signing up
     * @return The password of the person signing up
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the repeated password of the person signing up
     * @return The repeated password of the person signing up
     */
    public String getPasswordRepeat() {
        return this.passwordRepeat;
    }
}
