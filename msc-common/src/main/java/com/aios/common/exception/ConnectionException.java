package com.aios.common.exception;

public class ConnectionException extends Exception {

    private static final long serialVersionUID = 449971587231274127L;

    public ConnectionException() {
        this("Conexión al microservicio");
    }

    public ConnectionException(String message) {
        this(message, null);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}