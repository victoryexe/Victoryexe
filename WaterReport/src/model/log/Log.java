package model.log;

import model.Users.Account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Alexandra on 10/27/2016.
 */
public abstract class Log {
    private LocalDateTime timestamp;
    private Account responsibleAccount;

    /**
     * Makes a new Log with a timestamp and the responsible acount
     * @param responsibleAccount the Account that initiated the action
     */
    public Log(Account responsibleAccount) {
        timestamp = LocalDateTime.now();
        this.responsibleAccount = responsibleAccount;
    }

    /**
     * Gets the date of the log creation
     * @return LocalDate representing the time this log was created
     */
    public LocalDate getDate() {
        return timestamp.toLocalDate();
    }

    /**
     * Gets the time of the log creation
     * @return LocalTime representing the time this log was created
     */
    public LocalTime getTime() {
        return timestamp.toLocalTime();
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
