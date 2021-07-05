package com.bank.credit.card.test.data.helper;

import com.bank.credit.card.constants.Brand;
import com.bank.credit.card.repository.entity.Card;
import lombok.Builder;
import lombok.With;

import java.util.Objects;


/**
 * CreditCardBuilder is utility class for providing new card Object for given details
 */

@Builder
@With
public class CreditCardBuilder {

    private final Long id;

    private final String ownerName;

    private final String number;

    private final int limit;

    private final int balance;

    private final Brand brand;

    private final String currency;

    /**
     * Generate new card for given details
     * @return new card object
     */
    public Card buildCard() {
        if(Objects.nonNull(id)&& Objects.nonNull(ownerName) && Objects.nonNull(number) && (limit>0)&& Objects.nonNull(brand))
            return new Card(id, ownerName, number, limit, balance, brand,currency);
        else
            return new Card();
    }

}
