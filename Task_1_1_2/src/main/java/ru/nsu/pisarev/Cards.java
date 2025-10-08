package ru.nsu.pisarev;

public class Cards {
    protected int points;
    protected String cardName;
    protected String cardSuit;



    public static int sumCards(Card[] cards) {
        int sum = 0;
        for (Card card : cards) {
            if (card != null)
                sum += card.points;
        }
        return sum;
    }
    public static void changeAceValue(Card[] cards){
        while (Cards.sumCards(cards) > 21){
            int countAce = 0;
            int countOnePointAce = 0;
            boolean firstAceInHand = false;
            for (Card playerCard : cards) {
                if (playerCard!= null && playerCard.cardName.equals("Туз")){
                    countAce+=1;
                    if (playerCard.points >1 && !firstAceInHand){
                        playerCard.setPoints(1);
                        countOnePointAce += 1;
                        firstAceInHand = true;
                    }
                    else if (playerCard.points == 1 && !firstAceInHand) {
                        countOnePointAce += 1;
                    }
                }
            }
            if (countAce==0)
                break;
            if (countAce-countOnePointAce == 0)
                break;
        }
    }
}
