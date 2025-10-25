package ru.nsu.pisarev;

import static ru.nsu.pisarev.Game.MAXIMAL_CARDS;

public class Player {
    private final Card[] cards;

    public Player(Card[] cards) {
        this.cards = cards;
    }

    public static Player create() {
        Card[] cards = new Card[MAXIMAL_CARDS];
        cards[0] = Card.createCard();
        cards[1] = Card.createCard();
        return new Player(cards);
    }

    public void changeAceValue() {
        Cards.changeAceValue(cards);
    }

    public int sumCards() {
        return Cards.sumCards(cards);
    }

    public Card[] getCards() {
        return cards;
    }
}


