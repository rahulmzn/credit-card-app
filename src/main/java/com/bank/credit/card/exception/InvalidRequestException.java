package com.bank.credit.card.exception;

import org.springframework.validation.Errors;

/**
 * InvalidRequestException will be responsible to return the error response if any property of card or any other entity rule violet,
 * For example, null or invalid credit card number
 */
public class InvalidRequestException extends RuntimeException {
    private static final long serialVersionUID = -589229703599785149L;
    private final Errors errors;

    public InvalidRequestException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
