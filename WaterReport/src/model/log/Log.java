package model.log;

import model.Users.Account;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

/**
 * Created by Alexandra on 10/27/2016.
 * A representation of a Log
 */
public abstract class Log {
    private final LocalDateTime timestamp;
    private final Account responsibleAccount;

    // Class must be public for DB to correctly load data.

    /**
     * Makes a new Log with a timestamp and the responsible account
     * @param responsibleAccount the Account that initiated the action
     */
    public Log(Account responsibleAccount) {
        timestamp = LocalDateTime.now();
        this.responsibleAccount = responsibleAccount;
    }
    /**
     * Makes a new Log with a timestamp and the responsible account
     * @param timeStamp String formatted Time Stamp
     * @param responsibleAccount the Account that initiated the action
     */
    public Log(String timeStamp, Account responsibleAccount) {
        this.timestamp = LocalDateTime.parse(timeStamp);
        this.responsibleAccount = responsibleAccount;
    }

    /**
     * Gets the complete timestamp of this log creation
     * @return LocalDateTime representing the time this log was created
     */
    public ChronoLocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the Account responsible for log creation
     * @return Account responsible for log creation
     */
    public Account getResponsibleAccount() {
        return responsibleAccount;
    }


    @Override
    public String toString() {
        return timestamp.toString() + responsibleAccount.getEmail();
    }
}
