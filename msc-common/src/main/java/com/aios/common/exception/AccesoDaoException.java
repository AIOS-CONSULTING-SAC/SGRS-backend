package com.aios.common.exception;

import lombok.Getter;
import org.springframework.dao.DataAccessException;

@Getter
public class AccesoDaoException extends DataAccessException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public AccesoDaoException(String daoName, String methodName, String message, Throwable cause) {
        super(message, cause);
    }

    public AccesoDaoException(String mensaje, Exception cause) {
        super(String.valueOf(cause));
    }


}