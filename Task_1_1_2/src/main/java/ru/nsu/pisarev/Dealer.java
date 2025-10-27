package ru.nsu.pisarev;

import static ru.nsu.pisarev.Game.MAXIMAL_CARDS;

public class Dealer {
    private final Card[] cards = new Card[MAXIMAL_CARDS];
    private int cardsCount = 0;

    public static Dealer create(Card c1, Card c2) {
        Dealer dealer = new Dealer();
        dealer.addCards(c1, c2);
        return dealer;
    }

    public boolean moreCardsNeeded() {
        Cards.changeAceValue(cards, cardsCount);
        int decision = Cards.sumCards(cards, cardsCount);
        return decision <= 16;
    }

    public void changeAceValue() {
        Cards.changeAceValue(cards, cardsCount);
    }

    public int sumCards() {
        return Cards.sumCards(cards, cardsCount);
    }

    public int getCardsCount() {
        return cardsCount;
    }

    public Card[] getCards() {
        return cards;
    }

    public void addCard(Card newCard) {
        cards[cardsCount ++] = newCard;
    }

    public void addCards(Card c1, Card c2) {
        addCard(c1);
        addCard(c2);
    }
}

