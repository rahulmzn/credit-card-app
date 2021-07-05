package com.bank.credit.card.constraints;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessage {
    public static final String CARD_LENGTH_EXCEED = "Credit card number can be maximum of 19 digit";
    public static final String CARD_NUMBER_EMPTY= "Credit card number can not be empty";
    public static final String CARD_NUMBER_NULL= "Credit card number can not be null";
    public static final String CARD_NUMBER_ONLY_DIGIT= "Credit card must contain only digits and must be positive number";
    public static final String CARD_NUMBER_INVALID= "Invalid credit card number";

}
