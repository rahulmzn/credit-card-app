package com.bank.credit.card.constraints.validation;

import com.bank.credit.card.constants.Brand;
import com.bank.credit.card.test.data.helper.Constants;
import com.bank.credit.card.dto.CreditCardDetailsDto;
import org.junit.BeforeClass;
import org.junit.Test;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.bank.credit.card.constraints.ErrorMessage.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * These test will validate different combinations on credit card number
 */

public class CreditCardNumberTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkNoErrorWhenValidCardNumber() {
        //Given
        CreditCardDetailsDto card = CreditCardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number("30569309025904").currency("£").build();

        //When Run
        Set<ConstraintViolation<CreditCardDetailsDto>> constraintViolations =
                validator.validate( card );
        // Then verify
        assertEquals( 0, constraintViolations.size() );
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void checkErrorWhenInvalidCardNumber() {
        //Given
        CreditCardDetailsDto card = CreditCardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number("4386280033772").currency("£").build();

        //When Run
        Set<ConstraintViolation<CreditCardDetailsDto>> constraintViolations =
                validator.validate( card );
        // Then verify
        assertEquals( 1, constraintViolations.size() );
        assertEquals(
                Constants.EXCEPTION_MSG,
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void checkErrorWhenCardNumberIsMoreThan19Char() {
        //Given
        CreditCardDetailsDto card = CreditCardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number("43862800337721111111111111").currency("£").build();

        // When Run
        Set<ConstraintViolation<CreditCardDetailsDto>> constraintViolations =
                validator.validate( card );
        // Then verify
        assertEquals( 1, constraintViolations.size() );
        assertEquals(CARD_LENGTH_EXCEED, constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void checkErrorWhenCardNumberIsNull() {
        // Given
        CreditCardDetailsDto card = CreditCardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number(null).currency("£").build();

        //When Run
        Set<ConstraintViolation<CreditCardDetailsDto>> constraintViolations =
                validator.validate( card );
        // Then verify
        assertEquals( 1, constraintViolations.size() );
        assertEquals(CARD_NUMBER_NULL, constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void checkErrorWhenCardNumberContainsOtherThanDigitAlso() {
        // Given
        CreditCardDetailsDto card = CreditCardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number("43862800337sss111").currency("£").build();

        // When Run
        Set<ConstraintViolation<CreditCardDetailsDto>> constraintViolations =
                validator.validate( card );
        // Then verify
        assertEquals( 1, constraintViolations.size() );
        assertEquals(CARD_NUMBER_ONLY_DIGIT, constraintViolations.iterator().next().getMessage());
    }


}
