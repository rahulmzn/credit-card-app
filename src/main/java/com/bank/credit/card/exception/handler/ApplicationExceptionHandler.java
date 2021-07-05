package com.bank.credit.card.exception.handler;


import com.bank.credit.card.exception.InvalidRequestException;
import com.bank.credit.card.exception.model.ErrorResource;
import com.bank.credit.card.exception.model.FieldErrorResource;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * RestResponseEntityExceptionHandler class will be responsible  to handle all the error or failure scenario as part of error framework implementation.
 */
@Api(tags = {"Errors"})
@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * handleInvalidRequest is the function which we have created to handle field level exception as given in assignment,
     * one of the scenario can be  custom validation on credit card number written using annotation CardNumber
     *
     * @param e       : Exception object
     * @param request : Http servlet request
     * @return : return handled error response
     */
    @ExceptionHandler({InvalidRequestException.class})
    @ResponseBody
    protected ResponseEntity<ErrorResource> handleInvalidRequest(RuntimeException e, HttpServletRequest request) {
        InvalidRequestException ire = (InvalidRequestException) e;
        List<FieldErrorResource> fieldErrorResources;
        List<FieldError> fieldErrors = ire.getErrors().getFieldErrors();
        fieldErrorResources = fieldErrors.stream().map(fieldError -> FieldErrorResource.builder().parameter(fieldError.getField()).code(fieldError.getCode()).message(fieldError.getDefaultMessage()).build()).collect(Collectors.toList());
        return errorResponse(e, fieldErrorResources, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    /**
     * All the Runtime exception will be handle in this method and will generate Internal server error Http status
     *
     * @param e       : Cause at runtime
     * @param request : Http request data
     * @return : Internal server http response
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResource> handleError(Exception e, HttpServletRequest request) {
        return errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Common method to populate data
     *
     * @param body   : Object which will  be return back to user
     * @param status : Http status code
     * @param <T>    : Type of Object
     * @return : Final response
     */
    protected <T> ResponseEntity<T> response(T body, HttpStatus status) {
        log.debug("response status {}", status);
        return new ResponseEntity<>(body, new HttpHeaders(), status);
    }

    protected ResponseEntity<ErrorResource> errorResponse(Throwable throwable, HttpStatus status, HttpServletRequest request) {
        log.debug("Error Response with status {}", status);
        if (Objects.nonNull(throwable)) {
            ErrorResource errorResource = new ErrorResource(throwable);
            log.error("error  {}  captured from ip {} ==> {}", errorResource.getCode(), request.getRemoteAddr(), errorResource, throwable);
            return response(errorResource, status);
        }
        log.debug("error for ip {} ==> {}", request.getRemoteAddr(), status);
        return response(null, status);
    }

    protected ResponseEntity<ErrorResource> errorResponse(Throwable throwable, List<FieldErrorResource> fieldErrors, HttpStatus status, HttpServletRequest request) {
        log.debug("Error Response with status {} when field level error happens", status);
        if (Objects.nonNull(throwable)) {
            ErrorResource errorResource = new ErrorResource(throwable, fieldErrors);
            log.error("error  {}  captured from ip {} ==> {}", errorResource.getCode(), request.getRemoteAddr(), errorResource, throwable);
            return response(errorResource, status);
        }
        log.error("error for ip {} ==> {}", request.getRemoteAddr(), status);
        return response(null, status);
    }
}
