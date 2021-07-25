package com.epam.flyingdutchman.exception;

public class ServiceException extends Exception {
    /**
     * Instantiates a new Service Exception.
     */
    public ServiceException() {
        super();
    }

    /**
     * Instantiates a new Service Exception.
     *
     * @param message the message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Service Exception.
     *
     * @param message   the message
     * @param throwable the throwable
     */
    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Instantiates a new Service Exception.
     *
     * @param throwable the throwable
     */
    public ServiceException(Throwable throwable) {
        super(throwable);
    }
}
