package app;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordEncryption {

    public String execute(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public boolean verify(String password, String encryptedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), encryptedPassword);
        return result.verified;
    }
}
