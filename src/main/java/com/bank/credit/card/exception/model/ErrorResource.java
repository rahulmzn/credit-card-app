package com.bank.credit.card.exception.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * ErrorResource is common error resource which will be used across for exception handling framework
 * @author : Rahul Kumar
 * @version 1.0
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// NO SONAR
public class ErrorResource implements Serializable {

    private static final long serialVersionUID = -2415907856747146978L;
    /**
     * Unique code which can be used for tracking or tracing errors.
     */
    private String code;
    /**
     * Details about exception
     */
    private String exception;
    /**
     * Describe error message in detail
     */
    private String message;
    /**
     * List of captured errors
     */
    private transient List<FieldErrorResource> errors;
    /**
     * List of captured global errors
     */
    private transient List<GlobalErrorResource> globalErrors;

    private String getErrorNumber() {
        return Long.toHexString(System.currentTimeMillis());
    }

    public ErrorResource(Throwable throwable) {
        this.code = getErrorNumber();
        this.exception = throwable.getClass().getSimpleName();
        this.message = throwable.getMessage();
    }

    public ErrorResource(Throwable throwable, List<FieldErrorResource> errors) {
        if (throwable == null) {
            this.code = getErrorNumber();
        } else {
            this.code = getErrorNumber();
            this.exception = throwable.getClass().getSimpleName();
            this.message = throwable.getMessage();
        }
        this.errors = errors;
    }

    public ErrorResource(String message) {
        this.code = getErrorNumber();
        this.message = message;
    }

}
