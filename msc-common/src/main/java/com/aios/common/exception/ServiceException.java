package com.aios.common.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private final String serviceName;
    private final String methodName;
    public ServiceException(String serviceName, String methodName, String message, Throwable cause) {
        super(message, cause);
        this.serviceName = serviceName;
        this.methodName = methodName;
    }

    public ServiceException(String mensaje, Exception cause) {
        super(cause);
        this.serviceName = mensaje;
        this.methodName = mensaje;
    }
}