package com.aios.common.exception;

import java.io.IOException;

public class DeserealizarException extends IOException {

    private static final long serialVersionUID = 449971587231274127L;

    public DeserealizarException() {
        this("Conexión al microservicio");
    }

    public DeserealizarException(String message) {
        this(message, null);
    }

    public DeserealizarException(String message, Throwable cause) {
        super(message, cause);
    }

}