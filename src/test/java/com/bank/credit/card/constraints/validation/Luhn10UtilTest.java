package com.bank.credit.card.constraints.validation;

import com.bank.credit.card.utility.Luhn10Util;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @ValueSource(strings = {"274637rrr845","6759310784561226","3543-6933-8731-4139","4852 7891 0697 922 0261"})
    void shouldFailWhenNumberHasOtherThanDigit(String number) {
        assertFalse(Luhn10Util.calculateLuhnMod10Check(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678903555", "012850003580200", "1358954993914435", "5277029120773860","4556-0690-9685-2293", "4852789106979220268"})
    void testValidateSuccessWhenValidCardNumberGiven(String number) {
        assertTrue(Luhn10Util.calculateLuhnMod10Check(number));
    }

}
