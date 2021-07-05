package com.bank.credit.card.service;

import com.bank.credit.card.dto.CreditCardDetailsDto;

import java.util.List;

/**
 * Card service will be responsible to define common functionality which can be applied to all type of card services
 */

public interface CardService {

    /**
     * Perform add card operation for given service, this operation can be used to store new card details in backend system
     * @param card : Card object which need to be store in backend system
     */
    void addCard(CreditCardDetailsDto card);

    /**
     * Fetch all stored card from backend system without any filter
     * @return : List of all available #Card from backend system
     */
    List<CreditCardDetailsDto> getAllCards();

}
