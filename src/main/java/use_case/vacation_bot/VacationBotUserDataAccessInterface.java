package use_case.vacation_bot;

import com.google.cloud.Timestamp;

public interface VacationBotUserDataAccessInterface {
    /**
     * Returns the user with the given username.
     * @param username the username.
     */
    Timestamp getTimestamp(String username);
}
