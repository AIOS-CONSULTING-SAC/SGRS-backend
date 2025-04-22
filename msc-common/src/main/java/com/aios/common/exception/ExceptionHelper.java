package com.aios.common.exception;

import com.aios.common.response.ApiResponseBuilder;
import com.aios.common.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    private final ApiResponseBuilder responseBuilder;

    public ExceptionHelper(ApiResponseBuilder responseBuilder) {
        this.responseBuilder = responseBuilder;
    }

    @ExceptionHandler(value = {ConnectException.class})
    public ResponseEntity<Response<String>> handleConnectException(ConnectException ex) {
        logger.error("ConnectException: {}", ex.getMessage());
        ResponseEntity apiResponse = responseBuilder.errorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), "Se presentó un error al conectarse.");
        ex.printStackTrace();
        return apiResponse;
    }

    @ExceptionHandler(value = {GatewayException.class})
    public ResponseEntity<Response<String>> handleGatewayException(GatewayException ex) {
        logger.error("GatewayException: {}", ex.getMessage());
        ResponseEntity apiResponse = responseBuilder.errorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), "Se presentó un error al conectar con el microservicio desde gateway");
        ex.printStackTrace();
        return apiResponse;
    }


    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity<Response<String>> handlDeserealizarException(IOException ex) {
        logger.error("DeserealizarException: {}", ex.getMessage());
        ResponseEntity apiResponse = responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Se presentó un problema al deserealizar la respuesta", null);

        return apiResponse;
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Response<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        logger.error("ResourceNotFoundException {}", ex.getMessage());
        ResponseEntity apiResponse = responseBuilder.buildResponse(HttpStatus.NOT_FOUND.value(), "Se presentó un error con el recurso", null);
        ex.printStackTrace();
        return apiResponse;
    }


    @ExceptionHandler(value = {AccesoDaoException.class})
    public ResponseEntity<Response<String>> handleAccesoDaoException(AccesoDaoException ex) {
        logger.error("AccesoDaoException {}", ex.getMessage());
        ResponseEntity apiResponse = responseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Se presentó un error en la base de datos", null);
        return apiResponse;
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<Response<String>> handlerNumberFormatException(NumberFormatException ex) {
        logger.error("NumberFormatException {}", ex.getMessage());
        ResponseEntity apiResponse = responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Error en la conversión de dato númerico", null);
        return apiResponse;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        logger.error("MethodArgumentNotValidException {}", ex.getMessage());
        ResponseEntity apiResponse =responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Se presentó un error en las validaciones",errors); // new ErrorResponse("Validation failed", errors);
        return apiResponse;
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Response<String>> handleException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage());
        ResponseEntity apiResponse = responseBuilder.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Se presentó un error de excepción");
        return apiResponse;
    }


}
