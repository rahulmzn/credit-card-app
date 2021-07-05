package com.bank.credit.card.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Luhn10Util {

    /**
     * This function can be used for validating card if given card details qualify by LUHN-10 Algorithm
     *
     * @param number: Card number
     * @return {@code true} if card number pass luhn 10 check result , {@code false} otherwise
     */
    public static boolean calculateLuhnMod10Check(String number) {
        if(hasOnlyDigits(number)) {
            int sum = 0;
            boolean alternate = false;
            for (int i = number.length() - 1; i >= 0; i--) {
                int n = Integer.parseInt(number.substring(i, i + 1));
                if (alternate) {
                    n *= 2;
                    if (n > 9) {
                        n = (n % 10) + 1;
                    }
                }
                sum += n;
                alternate = !alternate;
            }
            return (sum % 10 == 0);
        }
        return false;
    }


    /**
     * Parses the {@link String} value as a {@link List} of {@link Integer} objects
     *
     * @param value card number
     * @return {@code false} if given card number does not have digits, {@code true} when has only digits
     */
    public static boolean hasOnlyDigits(final String value) {
        char[] chars = value.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }
}
