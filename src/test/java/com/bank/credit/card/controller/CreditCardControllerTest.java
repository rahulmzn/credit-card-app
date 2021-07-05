package com.bank.credit.card.controller;

import com.bank.credit.card.constants.Brand;
import com.bank.credit.card.dto.CardDetailsDto;
import com.bank.credit.card.repository.CardRepository;
import com.bank.credit.card.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;

import static com.bank.credit.card.constants.ResourcePaths.Card.V1.CARDS_ROOT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CreditCardController.class)
@Slf4j
class CreditCardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CardService  cardService;

    @MockBean
    CardRepository repository;

    @Test
    void shouldAddCreditCardSuccessfully() {
        // Given
        CardDetailsDto card = CardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("test Add card successfully").number("4716651077977392").currency("£").build();

        //Then
        try {
        mockMvc.perform(post(CARDS_ROOT)
                .content(new ObjectMapper().writeValueAsString(card))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        } catch (Exception exception) {
            log.error(exception.toString());
        }
    }
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "-1", "12q12", "123456789012345678901234"})
    void shouldValidateCreditCardAndReturnHttpErrorResponse(String invalidCardNumber) throws Exception{
        // Given
        CardDetailsDto card = CardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Should Fail with Error 422").number(invalidCardNumber).currency("£").build();

        //Then
            mockMvc.perform(post(CARDS_ROOT)
                    .content(new ObjectMapper().writeValueAsString(card))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());

    }
    @Test
    void shouldValidateInvalidContentTypeAndReturnHttpErrorResponse() throws Exception{
        // Given
        CardDetailsDto card = CardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Validate Invalid Content in Header").number("66666666666").currency("£").build();

        //Then
        mockMvc.perform(post(CARDS_ROOT)
                .content(new ObjectMapper().writeValueAsString(card))
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE))
                .andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());

    }
    @ParameterizedTest
    @ValueSource(ints = {-1})
    void whenFaultedCreditCardLimitThenReturns400(int creditCardLimit) throws Exception {
        // Given
        CardDetailsDto card = CardDetailsDto.builder().brand(Brand.VISA).limit(creditCardLimit).ownerName("In Valid Credit card limit").number("12345678903555").currency("£").build();

        //Then
        mockMvc.perform(post(CARDS_ROOT)
                .content(new ObjectMapper().writeValueAsString(card))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void shouldSuccessfullyVerifyServiceExecution() throws Exception {
        // Given
        CardDetailsDto card = CardDetailsDto.builder().brand(Brand.VISA).limit(100).ownerName("Rahul Kumar").number("12345678903555").currency("£").build();

        //When
        mockMvc.perform(post(CARDS_ROOT)
                .content(new ObjectMapper().writeValueAsString(card))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        //Then verify
        // Verify the results
        ArgumentCaptor<CardDetailsDto> cardCaptor = ArgumentCaptor.forClass(CardDetailsDto.class);
        verify(cardService, times(1)).addCard(cardCaptor.capture());
        assertThat(cardCaptor.getValue().getLimit()).isEqualTo(card.getLimit());
        assertThat(cardCaptor.getValue().getNumber()).isEqualTo(card.getNumber());
        assertThat(cardCaptor.getValue().getOwnerName()).isEqualTo(card.getOwnerName());
    }

    @Test
    void shouldSuccessfullyValidateErrorJsonResponse() throws Exception {
        // Given
        CardDetailsDto card = CardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number(null).currency("£").build();

        //Then
        mockMvc.perform(post(CARDS_ROOT)
                .content(new ObjectMapper().writeValueAsString(card))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.exception").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isArray());
    }

    @Test
    void shouldReturnAllCardsFromDatabase() throws Exception {
       // Given
        when(cardService.getAllCards()).thenReturn(Arrays.asList(CardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number("305693090259043").currency("£").build(),CardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number("305693090259043").currency("£").build()));

        // Run the test
        mockMvc.perform(get(CARDS_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].limit").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].number").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());

    }

    @Test
    void shouldValidateEmptyResponseWhenReturnEmptyFromDatabase() throws Exception {
        // Given
        when(cardService.getAllCards()).thenReturn(Collections.emptyList());

        // Run the test
        mockMvc.perform(get(CARDS_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());

    }

}

