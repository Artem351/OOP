package ru.nsu.pisarev;

import static ru.nsu.pisarev.Game.MAXIMAL_CARDS;

public class Player {
    private final Card[] cards = new Card[MAXIMAL_CARDS];
    private int cardsCount = 0;

    public static Player create() {
        Player player = new Player();
        player.addCard(Card.createCard());
        player.addCard(Card.createCard());
        return player;
    }

    public void changeAceValue() {
        Cards.changeAceValue(cards, cardsCount);
    }

    public int sumCards() {
        return Cards.sumCards(cards, cardsCount);
    }

    public Card[] getCards() {
        return cards;
    }

    public void addCard(Card newCard) {
        cards[cardsCount ++] = newCard;
    }
}


