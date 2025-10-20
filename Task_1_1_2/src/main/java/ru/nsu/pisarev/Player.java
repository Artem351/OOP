package ru.nsu.pisarev;

public class Player {
    private final Card[] cards;

    public Player(Card[] cards) {
        this.cards = cards;
    }

    public void showCards(){
        System.out.print("Ваши карты:");
        CardPrint.showCards(cards);
        System.out.print(" => ");
        CardPrint.showSum(cards);
        System.out.println();
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

