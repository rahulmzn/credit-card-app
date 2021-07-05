package com.bank.credit.card.utility;


import com.bank.credit.card.dto.CreditCardDetailsDto;
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
     * @param creditCardDetailsDto : com.bank.credit.card.dto.CardDetailsDto Object
     * @return : com.bank.credit.card.repository.entity.Card
     */
    public Card cardDetailsDtoToCardEntity(CreditCardDetailsDto creditCardDetailsDto){
        Card card = new Card();
        if(Objects.nonNull(creditCardDetailsDto)){
            card.setOwnerName(creditCardDetailsDto.getOwnerName());
            card.setNumber(creditCardDetailsDto.getNumber());
            card.setBalance(creditCardDetailsDto.getBalance());
            card.setBrand(creditCardDetailsDto.getBrand());
            card.setCurrency(creditCardDetailsDto.getCurrency());
            card.setLimit(creditCardDetailsDto.getLimit());
        }
        return card;
    }

    /**
     * This function can be used to transform Card type into CardDetailsDto
     * @param card : com.bank.credit.card.repository.entity.Card
     * @return : com.bank.credit.card.dto.CardDetailsDto Object
     */
    public CreditCardDetailsDto cardEntityToCardDetailsDto(Card card){
        CreditCardDetailsDto creditCardDetailsDto = new CreditCardDetailsDto();
        if(Objects.nonNull(card)){
            creditCardDetailsDto.setId(card.getId());
            creditCardDetailsDto.setOwnerName(card.getOwnerName());
            creditCardDetailsDto.setNumber(card.getNumber());
            creditCardDetailsDto.setBalance(card.getBalance());
            creditCardDetailsDto.setBrand(card.getBrand());
            creditCardDetailsDto.setCurrency(card.getCurrency());
            creditCardDetailsDto.setLimit(card.getLimit());
        }
        return creditCardDetailsDto;
    }
}
