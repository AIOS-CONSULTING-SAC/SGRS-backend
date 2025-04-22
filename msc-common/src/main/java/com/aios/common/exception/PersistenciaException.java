package com.aios.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenciaException extends Exception {

    static final Logger log = LoggerFactory.getLogger(PersistenciaException.class);

    private static final long serialVersionUID = 1L;

    public PersistenciaException() {

    }

    public PersistenciaException(String arg0) {
        super(arg0);

    }

    public PersistenciaException(Throwable arg0) {
        super(arg0);
        log.error("PersistenciaException: {} ", arg0);
    }

    public PersistenciaException(String arg0, Throwable arg1) {
        super(arg0, arg1);

    }
}

