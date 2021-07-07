package com.bank.credit.card.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Luhn10Util {

    /**
     * This function can be used for validating card if given card details qualify by LUHN-10 Algorithm
     *
     * @param number: Card number
     * @return {@code true} if card number pass luhn 10 check result , {@code false} otherwise
     */
    public static boolean calculateLuhnMod10Check(String number) {
        /*
         * Remove all character other than digits so that if works with combination as 1234-1234-1234-1234, 1234 1234 1234 1234
         */
        number = convertToValidDigitsString(number);

        /*
         * As per Luhn Rule every 1st digit should be multiply by 1 and second should be multiply 2,
         * Hence factor will have a on demand stream of 1,2,1,2,1,2.
         */
        PrimitiveIterator.OfInt factor =
                IntStream.iterate(1, i -> 3 - i).iterator();
        /*
         * 1: Prepare revers string of given number e.g 123456 -> 654321
         * 2: convert each char in string into int value of that by first map() of IntStream
         * 3: new use factor with IntStream map to double each second digit of given reversed set of digits.
         * 4: using reduce it will create a square sum for all these numbers by if has 2 digits then div /10 if single digit then mod by 10
         * 5: now its check if number mod is 0 or not -> if mod =0 then valid number otherwise invalid number
         */
        int sum = new StringBuilder(number).reverse()
                .toString().chars()
                .map(c -> c - '0')
                .map(i -> i * factor.nextInt())
                .reduce(0, (a, b) -> a + b / 10 + b % 10);

        return (sum % 10) == 0;
    }

    /**
     * returns a string after removing character other than digits from string.
     * @param input : any string with number
     * @return : string of digits only
     */
    private static String convertToValidDigitsString(String input) {
        return input.replaceAll("[^\\d]", "");
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
