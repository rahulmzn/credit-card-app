package com.bank.credit.card.controller;


import com.bank.credit.card.constants.ResourcePaths;
import com.bank.credit.card.dto.CardDetailsDto;
import com.bank.credit.card.exception.InvalidRequestException;
import com.bank.credit.card.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/*
 * Credit card controller is responsible to handle all credit card related requests
 * <p> Adding new card into database</p>
 * <p> Fetch all cards from database</p>
 */


@RestController
@RequestMapping(value = ResourcePaths.Card.V1.CARDS_ROOT)
@AllArgsConstructor
public class CreditCardController {

    private final CardService cardService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addCreditCard(@RequestBody @Valid CardDetailsDto card, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid " + card.getClass().getSimpleName(), bindingResult);
        }
        // Process storing credit card information in data base
        cardService.addCard(card);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CardDetailsDto>> getAllCreditCards() {
        List<CardDetailsDto> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards);
    }
}
