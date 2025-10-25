package ru.nsu.pisarev;

public class Dealer {
    private Card[] cards;

    public Dealer(Card[] cards) {
        this.cards = cards;
    }

    public boolean moreCardsNeeded() {
        Cards.changeAceValue(cards);
        int decision = Cards.sumCards(cards);
        return decision <= 16;
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

