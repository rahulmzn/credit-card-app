package com.bank.credit.card.exception;

public class CardAlreadyExistsException extends RuntimeException {

    public CardAlreadyExistsException(String cardNumber) {
        super("Given card number " + cardNumber + " already exists.");
    }
}
