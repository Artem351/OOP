package ru.nsu.pisarev;

public class Player {
        public Card[] cards;

    public Player(Card[] cards) {
        this.cards = cards;
    }

    public void showCards(){
        System.out.print("Ваши карты:");
        cards[0].showCards(cards);
        System.out.print(" => ");
        cards[0].showSum(cards);
        System.out.println();
    }

    public void changeAceValue() {
        cards[0].changeAceValue(cards);
    }
}
