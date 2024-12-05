package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Clients")
public class Client extends Person {

    /**
     * The card ID associated with this client.
     */
    @OneToOne(fetch = FetchType.EAGER)  // Use @ManyToOne or @OneToOne depending on the relationship
    @JoinColumn(name = "card_id")  // Specifies the foreign key column in the Clients table
    private Card card;

    /**
     * Constructs a new {@code Client} with the specified ID, age, and name,
     * and initializes a new {@code Card} for the client.
     *
     * @param age  the age of the client
     * @param name the name of the client
     */
    public Client(int age, String name) {
        super(age, name);
    }

    public Client() {
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "Client " +
                "ID=" + ID +
                ", Age=" + age +
                ", Name='" + name + '\'' +
                ", Card ID=" + (card != null ? card.getId() : "No Card");
    }
}
