package com.bank.credit.card.service;

import com.bank.credit.card.dto.CardDetailsDto;
import com.bank.credit.card.exception.CardAlreadyExistsException;
import com.bank.credit.card.repository.CardRepository;
import com.bank.credit.card.repository.entity.Card;
import com.bank.credit.card.utility.DtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * CreditCardServiceImpl service is responsible for service credit card data.
 * <p> This service serves functionality to add new credit card in data repository</p>
 * <p> This service serves functionality to fetch all existing credit card from data repository</p>
 */

@Service
@Slf4j
public class CreditCardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final DtoMapper mapper;

    @Autowired
    CreditCardServiceImpl(CardRepository cardRepository, DtoMapper mapper) {
        this.cardRepository = cardRepository;
        this.mapper = mapper;
    }

    /**
     * This operation should be used when new credit card is to be store in backend system.
     * @param cardDetailsDto : Credit Card object which need to be store in backend system
     */
    @Override
    public void addCard(CardDetailsDto cardDetailsDto) {
        log.debug("Add credit card started");
        boolean cardExistsForNumber = cardRepository.existsCardsByNumber(cardDetailsDto.getNumber());
        if (cardExistsForNumber) {
            throw new CardAlreadyExistsException(cardDetailsDto.getNumber());
        }
        cardRepository.save(mapper.cardDetailsDtoToCardEntity(cardDetailsDto));
    }

    /**
     * This operation should be used when all credit card is to be fetch from backend system
     * @return : List of all credit card
     */
    @Override
    public List<CardDetailsDto> getAllCards() {
        log.debug("Fetch all credit card started");
        List<Card> cards = cardRepository.findAll(Sort.by("id").ascending());
        return cards.stream().map(mapper::cardEntityToCardDetailsDto).collect(Collectors.toList());
    }

}
