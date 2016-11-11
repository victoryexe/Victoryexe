package model.log;

import model.Users.Account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Alexandra on 10/27/2016.
 * A representation of a Log
 */
abstract class Log {
    private final LocalDateTime timestamp;
    private final Account responsibleAccount;

    /**
     * Makes a new Log with a timestamp and the responsible account
     * @param responsibleAccount the Account that initiated the action
     */
    Log(Account responsibleAccount) {
        timestamp = LocalDateTime.now();
        this.responsibleAccount = responsibleAccount;
    }

    /**
     * Gets the complete timestamp of this log creation
     * @return LocalDateTime representing the time this log was created
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the Account responsible for log creation
     * @return Account responsible for log creation
     */
    public Account getResponsibleAccount() {
        return responsibleAccount;
    }
}
