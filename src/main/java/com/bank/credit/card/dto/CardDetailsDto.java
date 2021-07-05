package com.bank.credit.card.dto;

import com.bank.credit.card.constants.Brand;
import com.bank.credit.card.constraints.CardNumber;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * POJO to collect request data as data transfer
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CardDetailsDto {

    /**
     * Unique ID created by hibernate while storing data,
     */
    @JsonProperty("id")
    private String id;

    /**
     * Name of the card holder
     * It must not be empty
     */
    @JsonProperty("name")
    @NotNull(message = "Credit owner name is mandatory.")
    private String ownerName;

    /**
     * Credit card number
     * It must not be empty
     * This will be validated with custom card number LUHN-10 validation
     */
    @JsonProperty("number")
    @CardNumber
    private String number;

    /**
     * Total limit of card
     * It must be empty
     */
    @JsonProperty("limit")
    @NotNull(message = "Credit Card limit is mandatory.")
    @PositiveOrZero(message = "Credit Card limit should be positive or zero.")
    private int limit;

    /**
     * Balance of card
     */
    @JsonProperty("balance")
    private int balance;

    /**
     * Brand of card
     */
    @JsonProperty("brand")
    private Brand brand;

    @JsonProperty("currency")
    @NotNull(message = "Credit currency is mandatory.")
    private String currency;

}
