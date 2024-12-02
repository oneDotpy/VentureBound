package use_case.send_message;

import com.google.cloud.Timestamp;

/**
 * User DAO for the Send Message Use Case.
 */
public interface SendMessageUserDataAccessInterface {

    /**
     * Returns server timestamp
     */
    Timestamp getTimestamp(String username);
}
