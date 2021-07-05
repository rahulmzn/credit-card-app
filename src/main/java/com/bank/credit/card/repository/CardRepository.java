package com.bank.credit.card.repository;

import com.bank.credit.card.repository.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

    Boolean existsCardsByNumber(String number);

}
