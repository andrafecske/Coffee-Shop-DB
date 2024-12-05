package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cards")
public class Card implements HasID {

    /**
     * The current points available on the card.
     */
    private int currentPoints;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Integer card_id;

    public Card() {
        this.currentPoints = 0;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    @Override
    public Integer getId() {
        return card_id;
    }

    @Override
    public String toString() {
        return "Card ID=" + card_id + ", Current Points=" + currentPoints;
    }
}
