package ru.nsu.pisarev;

public final class CardPrint {

    public static void showCard(Card card){
        System.out.print("<"+card.cardName+" "+card.cardSuit+" ("+card.points+")> ");
    }
    public static void showCards(Card[] cards){
        for (Card card : cards) {
            if (card !=null)
                CardPrint.showCard(card);
        }
    }

    public static void showSum(Card[] cards){
        int sum=0;
        for (Card card : cards) {
            if (card != null)
                sum+= card.getPoints();
        }
        System.out.print(sum);
    }

    private CardPrint() {
        throw new UnsupportedOperationException();
    }
}
