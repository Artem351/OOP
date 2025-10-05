package ru.nsu.pisarev;

public class Player {
        public Card[] playerCards;

    public Player(Card[] playerCards) {
        this.playerCards = playerCards;
    }

    public void showCards(){
        System.out.print("Ваши карты:");
        playerCards[0].showCards(playerCards);
        System.out.print(" => ");
        playerCards[0].showSum(playerCards);
        System.out.println();
    }

}
