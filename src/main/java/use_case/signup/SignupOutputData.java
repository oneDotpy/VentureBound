package use_case.signup;

/**
 * Output Data for the Signup Use Case.
 */

public class SignupOutputData {
    private final String username;
    private final String email;
    private final boolean useCaseFailed;

    /**
     * Constructor for new instance of SignupOutputData.
     * @param username The username of the user signing up.
     * @param email The email of the user signing up.
     * @param useCaseFailed The state of signup.
     */
    public SignupOutputData(String username, String email, boolean useCaseFailed) {
        this.username = username;
        this.email = email;
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Gets the username of the user signing up
     * @return The username of user after signing up
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the email of the user signing up
     * @return The email of user after signing up
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the signup status of the user signing up
     * @return The signup status of user after signing up
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
