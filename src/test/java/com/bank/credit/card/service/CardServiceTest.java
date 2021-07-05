package com.bank.credit.card.service;

import com.bank.credit.card.test.data.helper.CreditCardBuilder;
import com.bank.credit.card.constants.Brand;
import com.bank.credit.card.dto.CardDetailsDto;
import com.bank.credit.card.exception.CardAlreadyExistsException;
import com.bank.credit.card.repository.CardRepository;
import com.bank.credit.card.repository.entity.Card;
import com.bank.credit.card.utility.DtoMapper;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private CardRepository cardRepository;

    @Mock
    private DtoMapper mapper;

    @InjectMocks
    CreditCardServiceImpl cardService;

    @Test
    void shouldAddCardInRepository() {
        Card card = CreditCardBuilder.builder().ownerName("Unit Test").number("4386280033772018").balance(10).limit(1000).brand(Brand.VISA).build().buildCard();

        when(cardRepository.existsCardsByNumber("4386280033772018")).thenReturn(false);
        when(cardRepository.save(any())).thenReturn(card);
        when(mapper.cardDetailsDtoToCardEntity(any((CardDetailsDto.class)))).thenReturn(card);
        CardDetailsDto cardDetailsDto = CardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number("4386280033772018").currency("£").build();
        cardService.addCard(cardDetailsDto);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void shouldReturnCardAlreadyExistsError() {
        Card card = CreditCardBuilder.builder().ownerName("Unit Test").number("4386280033772018").balance(10).limit(1000).brand(Brand.VISA).build().buildCard();

        when(cardRepository.existsCardsByNumber("4386280033772018")).thenReturn(true);

        CardDetailsDto cardDetailsDto = CardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number("4386280033772018").currency("£").build();
        try {
            cardService.addCard(cardDetailsDto);
        }catch (Exception e){
            assertNotNull(e);
            assertEquals(e.getClass().getSimpleName(),CardAlreadyExistsException.class.getSimpleName());
            verify(cardRepository, times(0)).save(card);
        }

    }

    @Test
    void shouldReturnAllCards() {
        CardDetailsDto cardDetailsDto = CardDetailsDto.builder().brand(Brand.VISA).limit(1000).ownerName("Rahul Kumar").number("4386280033772018").currency("£").build();

        when(cardRepository.findAll(Sort.by("id"))).thenReturn(Arrays.asList(
                CreditCardBuilder.builder().ownerName("Unit Test").number("30569309025904").balance(10).limit(1000).brand(Brand.VISA).build().buildCard(),
                CreditCardBuilder.builder().ownerName("Unit Test").number("6011111111111117").balance(10).limit(1000).brand(Brand.VISA).build().buildCard()
        ));
        when(mapper.cardEntityToCardDetailsDto(any((Card.class)))).thenReturn(cardDetailsDto);

        List<CardDetailsDto> allCards = cardService.getAllCards();
        verify(cardRepository, times(1)).findAll(Sort.by("id"));
        assertNotNull(allCards);
        assertEquals(2, allCards.size());
    }
}
