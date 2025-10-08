package ru.nsu.pisarev;

public class Dealer {
    public Card[] cards;

    public Dealer(Card[] cards) {
        this.cards = cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }


    public boolean needPickCards(){
        int decision;
        Cards.changeAceValue(cards);
        decision = Card.sumCards(cards);
        return decision<=16;
    }

    public void changeAceValue() {
        Card.changeAceValue(cards);
    }

    public int sumCards() {
        return Card.sumCards(cards);
    }
}
