package com.bank.credit.card.constraints.validation;

import com.bank.credit.card.constraints.CardNumber;
import com.bank.credit.card.utility.Luhn10Util;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

import static com.bank.credit.card.constraints.ErrorMessage.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Validate given card number against LUHN 10 Algorithm
 * See for ref {@link Luhn10Util#calculateLuhnMod10Check(String) LUHN Algorithm custom implementation }
 */
public class CardNumberValidator implements ConstraintValidator<CardNumber, String> {

    private static final Logger LOG = getLogger(CardNumberValidator.class);

    /**
     * <p>
     * This method will perfom check on given card number to validate for Given card number
     * </p>
     * <p>is not null</p>
     * <p>is not empty or blank</p>
     * <p>not more than 19 digits</p>
     * <p>Only has digits</>
     * <p>Validate by LUHN 10 Algorithm</p>
     *
     * @param cardNumber : series of numbers
     * @param context    : constraints context
     * @return : {@code true} if given card number validated, {@code false} otherwise
     */
    public boolean isValid(final String cardNumber, final ConstraintValidatorContext context) {
        LOG.debug("Class {} isValid called", CardNumberValidator.class.getSimpleName());
        // When card number is null
        if (Objects.isNull(cardNumber)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(CARD_NUMBER_NULL).addConstraintViolation();
            return false;
        }
        // When card number is empty
        if (Strings.isBlank(cardNumber)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(CARD_NUMBER_EMPTY).addConstraintViolation();
            return false;
        }
        // When card number is more than 19 digits
        if (cardNumber.length() > 19) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(CARD_LENGTH_EXCEED).addConstraintViolation();
            return false;
        }
        // When card number has other than digits
        if (!Luhn10Util.hasOnlyDigits(cardNumber)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(CARD_NUMBER_ONLY_DIGIT).addConstraintViolation();
            return false;
        }

        // Validate card number
        return Luhn10Util.calculateLuhnMod10Check(cardNumber);
    }





}

