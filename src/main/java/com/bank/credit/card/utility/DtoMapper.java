package com.bank.credit.card.utility;


import com.bank.credit.card.dto.CardDetailsDto;
import com.bank.credit.card.repository.entity.Card;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * This class can be used to extend mapper or transformation on DTO's
 */

@Component
public class DtoMapper {

    /**
     * This function can be used to transform CardDetailsDto into Card type
     * @param cardDetailsDto : com.bank.credit.card.dto.CardDetailsDto Object
     * @return : com.bank.credit.card.repository.entity.Card
     */
    public Card cardDetailsDtoToCardEntity(CardDetailsDto cardDetailsDto){
        Card card = new Card();
        if(Objects.nonNull(cardDetailsDto)){
            card.setOwnerName(cardDetailsDto.getOwnerName());
            card.setNumber(cardDetailsDto.getNumber());
            card.setBalance(cardDetailsDto.getBalance());
            card.setBrand(cardDetailsDto.getBrand());
            card.setCurrency(cardDetailsDto.getCurrency());
            card.setLimit(cardDetailsDto.getLimit());
        }
        return card;
    }

    /**
     * This function can be used to transform Card type into CardDetailsDto
     * @param card : com.bank.credit.card.repository.entity.Card
     * @return : com.bank.credit.card.dto.CardDetailsDto Object
     */
    public CardDetailsDto cardEntityToCardDetailsDto(Card card){
        CardDetailsDto cardDetailsDto = new CardDetailsDto();
        if(Objects.nonNull(card)){
            cardDetailsDto.setOwnerName(card.getOwnerName());
            cardDetailsDto.setNumber(card.getNumber());
            cardDetailsDto.setBalance(card.getBalance());
            cardDetailsDto.setBrand(card.getBrand());
            cardDetailsDto.setCurrency(card.getCurrency());
            cardDetailsDto.setLimit(card.getLimit());
        }
        return cardDetailsDto;
    }
}
