package com.bank.credit.card.controller;


import com.bank.credit.card.constants.ResourcePaths;
import com.bank.credit.card.dto.CreditCardDetailsDto;
import com.bank.credit.card.exception.InvalidRequestException;
import com.bank.credit.card.exception.model.ErrorResource;
import com.bank.credit.card.service.CardService;
import io.swagger.annotations.*;
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
@Api(tags = {"CreditCard"})
@RequestMapping(value = ResourcePaths.Card.V1.CARDS_ROOT)
@AllArgsConstructor
public class CreditCardController {

    private final CardService cardService;

    @ApiOperation(httpMethod = "POST",value = "Add credit card details", notes = "Add a new credit card details", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
            @ApiResponse(code = 201, message = "Card Created Successfully" ,response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResource.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = ErrorResource.class)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addCreditCard(
            @ApiParam(required = true, name = "creditCard", value = "Add credit Card")
            @RequestBody @Valid CreditCardDetailsDto card, BindingResult bindingResult) {
        //Process errors
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid " + card.getClass().getSimpleName(), bindingResult);
        }
        // Process storing credit card information in data base
        cardService.addCard(card);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(httpMethod = "GET",value = "Fetch all credit card",
            notes = "Retrieving the collection of credit card",
            response = CreditCardDetailsDto[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResource.class),
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CreditCardDetailsDto>> getAllCreditCards() {
        List<CreditCardDetailsDto> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards);
    }
}
