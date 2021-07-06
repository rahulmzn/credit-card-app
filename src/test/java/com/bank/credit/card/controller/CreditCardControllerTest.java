package com.bank.credit.card.controller;

import com.bank.credit.card.constants.Brand;
import com.bank.credit.card.dto.CreditCardDetailsDto;
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
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CreditCardController.class)
@Slf4j
class CreditCardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CardService cardService;

    @MockBean
    CardRepository repository;

    /**
     * Here  test should store new credit card successfully
     */
    @Test
    void shouldAddCreditCardSuccessfully() throws Exception {
        // Given
        CreditCardDetailsDto card = CreditCardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("test Add card successfully").number("4716651077977392").currency("£").build();

        //When Run Then Verify
        mockMvc.perform(post(CARDS_ROOT)
                .content(new ObjectMapper().writeValueAsString(card))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    /**
     * Here test should be failed for below given incorrect credit card numbers as combination
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "-1", "12q12", "123456789012345678901234"})
    void shouldValidateCreditCardAndReturnHttpErrorResponse(String invalidCardNumber) throws Exception {
        // Given
        CreditCardDetailsDto card = CreditCardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Should Fail with Error 422").number(invalidCardNumber).currency("£").build();

        //When Run Then Verify
        mockMvc.perform(post(CARDS_ROOT)
                .content(new ObjectMapper().writeValueAsString(card))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());

    }

    /**
     * Here Validate API should only json type of data and other kind of data should be throw as exception
     */
    @Test
    void shouldValidateInvalidContentTypeAndReturnHttpErrorResponse() throws Exception {
        // Given
        CreditCardDetailsDto card = CreditCardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Validate Invalid Content in Header").number("66666666666").currency("£").build();

        //When Run Then Verify
        mockMvc.perform(post(CARDS_ROOT)
                .content(new ObjectMapper().writeValueAsString(card))
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE))
                .andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());

    }

    /**
     * Here test should be fail, as test will pass invalid card number which will be in negative value
     */
    @ParameterizedTest
    @ValueSource(ints = {-1})
    void whenFaultedCreditCardLimitThenReturns(int creditCardLimit) throws Exception {
        // Given
        CreditCardDetailsDto card = CreditCardDetailsDto.builder().brand(Brand.VISA).limit(creditCardLimit).ownerName("In Valid Credit card limit").number("12345678903555").currency("£").build();

        //When Run Then Verify
        mockMvc.perform(post(CARDS_ROOT)
                .content(new ObjectMapper().writeValueAsString(card))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    /**
     * This test should verify if any exception thrown from service layer of application
     */
    @Test
    void shouldSuccessfullyVerifyServiceExecution() throws Exception {
        // Given
        CreditCardDetailsDto card = CreditCardDetailsDto.builder().brand(Brand.VISA).limit(100).ownerName("Rahul Kumar").number("12345678903555").currency("£").build();

        //When Run
        mockMvc.perform(post(CARDS_ROOT)
                .content(new ObjectMapper().writeValueAsString(card))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //Then verify
        ArgumentCaptor<CreditCardDetailsDto> cardCaptor = ArgumentCaptor.forClass(CreditCardDetailsDto.class);
        verify(cardService, times(1)).addCard(cardCaptor.capture());
        assertThat(cardCaptor.getValue().getLimit()).isEqualTo(card.getLimit());
        assertThat(cardCaptor.getValue().getNumber()).isEqualTo(card.getNumber());
        assertThat(cardCaptor.getValue().getOwnerName()).isEqualTo(card.getOwnerName());
    }

    /**
     * Here test should verify service exception content to make sure error message are returning properly from api
     */
    @Test
    void shouldSuccessfullyValidateErrorJsonResponse() throws Exception {
        // Given
        CreditCardDetailsDto card = CreditCardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number(null).currency("£").build();

        //When Run Then Verify
        mockMvc.perform(post(CARDS_ROOT)
                .content(new ObjectMapper().writeValueAsString(card))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.exception").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isArray());
    }

    /**
     * This test should try to fetch all existing card from service, for achieving this we have mocked card details which will be return when service will be called
     */
    @Test
    void shouldReturnAllCardsFromDatabase() throws Exception {
        // Given
        when(cardService.getAllCards()).thenReturn(Arrays.asList(CreditCardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number("305693090259043").currency("£").build(), CreditCardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number("305693090259043").currency("£").build()));

        //When Run Then Verify
        mockMvc.perform(get(CARDS_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].limit").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].number").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());

    }

    /**
     * Test should return empty result when nothing found in database - data is mocked
     */
    @Test
    void shouldValidateEmptyResponseWhenReturnEmptyFromDatabase() throws Exception {
        // Given
        when(cardService.getAllCards()).thenReturn(Collections.emptyList());

        //When Run Then Verify
        mockMvc.perform(get(CARDS_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());

    }

}

