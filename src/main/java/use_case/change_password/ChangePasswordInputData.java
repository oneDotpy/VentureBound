package use_case.change_password;

/**
 * The input data for change password
 */
public class ChangePasswordInputData {
    private final String username;
    private final String currentPassword;
    private final String newPassword;

    public ChangePasswordInputData(String username, String currentPassword, String newPassword) {
        this.username = username;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
