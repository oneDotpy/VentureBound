package use_case.login;

/**
 * The Input Data for the Login Use Case.
 * Contains information about the username and password
 */
public class LoginInputData {

    private final String username;
    private final String password;

    /**
     * Constructor for an instance of LoginInputData
     * @param username The username of the person logging in
     * @param password The password of the person logging in
     */
    public LoginInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username of the person logging in
     * @return The username of the person logging in
     */
    String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user logging in
     * @return The password of the user logging in
     */
    String getPassword() {
        return password;
    }

}
