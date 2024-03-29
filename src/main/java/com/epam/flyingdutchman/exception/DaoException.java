package com.epam.flyingdutchman.exception;

/**
 * The class represents exceptions in dao layer.
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public class DaoException extends Exception {
    /**
     * Instantiates a new Dao exception.
     */
    public DaoException() {
        super();
    }

    /**
     * Instantiates a new Dao exception.
     *
     * @param message the message
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Dao exception.
     *
     * @param message   the message
     * @param throwable the throwable
     */
    public DaoException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Instantiates a new Dao exception.
     *
     * @param throwable the throwable
     */
    public DaoException(Throwable throwable) {
        super(throwable);
    }
}
