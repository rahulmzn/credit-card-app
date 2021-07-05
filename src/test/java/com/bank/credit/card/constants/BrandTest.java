package com.bank.credit.card.constants;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrandTest {

    @BeforeClass
    public static void setUp() {

    }
    @Test
    public void testDetection() {
        assertEquals(Brand.VISA, Brand.detect("4000056655665556"));
        assertEquals(Brand.VISA, Brand.detect("4242424242424242"));

        assertEquals(Brand.MASTERCARD, Brand.detect("5105105105105100"));
        assertEquals(Brand.MASTERCARD, Brand.detect("5200828282828210"));
        assertEquals(Brand.MASTERCARD, Brand.detect("5555555555554444"));

        assertEquals(Brand.AMERICAN_EXPRESS, Brand.detect("371449635398431"));
        assertEquals(Brand.AMERICAN_EXPRESS, Brand.detect("378282246310005"));

        assertEquals(Brand.DISCOVER, Brand.detect("6011000990139424"));
        assertEquals(Brand.DISCOVER, Brand.detect("6011111111111117"));

        assertEquals(Brand.DINERS_CLUB, Brand.detect("30569309025904"));
        assertEquals(Brand.DINERS_CLUB, Brand.detect("38520000023237"));

        assertEquals(Brand.JCB, Brand.detect("3530111333300000"));
        assertEquals(Brand.JCB, Brand.detect("3566002020360505"));

        assertEquals(Brand.UNKNOWN, Brand.detect("0000000000000000"));
    }

}
