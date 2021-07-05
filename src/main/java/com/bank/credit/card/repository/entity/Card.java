package com.bank.credit.card.repository.entity;


import com.bank.credit.card.constants.Brand;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Name of the card holder
     */
    @Column(name = "owner_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String ownerName;

    /**
     *Credit card number
     * It must not be empty
     */
    @Column(name = "number", nullable = false, columnDefinition = "VARCHAR(255)")
    private String number;

    /**
     * Limit of card
     * It must be empty
     */
    @Column(name = "total_limit", nullable = false, columnDefinition = "INT")
    private int limit;

    /**
     * Balance of card
     */
    @Column(name = "balance", columnDefinition = "INT")
    @ColumnDefault("0")
    private int balance ;

    /**
     * Brand of card
     */
    @Column(name = "brand", nullable = false, columnDefinition = "VARCHAR(255)")
    private Brand brand;

    @Column(name = "currency", columnDefinition = "VARCHAR(20)")
    private String currency;

}
