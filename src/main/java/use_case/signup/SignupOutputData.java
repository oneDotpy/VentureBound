package use_case.signup;

import entity.Group;

/**
 * Output Data for the Signup Use Case.
 */

public class SignupOutputData {
    private final String username;
    private final String email;
    private final boolean useCaseFailed;

    public SignupOutputData(String username, String email, boolean useCaseFailed) {
        this.username = username;
        this.email = email;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isUseCaseFailed() {
            return useCaseFailed;
        }

}
