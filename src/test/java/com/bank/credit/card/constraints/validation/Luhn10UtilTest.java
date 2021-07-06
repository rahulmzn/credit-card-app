package com.bank.credit.card.constraints.validation;

import com.bank.credit.card.utility.Luhn10Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Validate Card Number Unit tests against Luhn10 Algo
 */
class Luhn10UtilTest {

    @ParameterizedTest
    @ValueSource(strings = {"274637845"})
    void shouldFailWhenInvalidCardNumber(String ccNumber) {
        assertFalse(Luhn10Util.calculateLuhnMod10Check(ccNumber));
    }

    @ParameterizedTest
    @ValueSource(strings = {"274637rrr845"})
    void shouldFailWhenNumberHasOtherThanDigit(String ccNumber) {
        assertFalse(Luhn10Util.calculateLuhnMod10Check(ccNumber));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678903555", "012850003580200", "1358954993914435"})
    void testValidateSuccessWhenValidCardNumberGiven(String ccNumber) {
        assertTrue(Luhn10Util.calculateLuhnMod10Check(ccNumber));
    }
}
