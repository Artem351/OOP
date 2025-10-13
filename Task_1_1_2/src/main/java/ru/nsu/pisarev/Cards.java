package ru.nsu.pisarev;

public final class Cards {
    public static final String ACE = "Туз";

    public static int sumCards(Card[] cards) {
        int sum = 0;
        for (Card card : cards) {
            if (card != null)
                sum += card.getPoints();
        }
        return sum;
    }

    public static void changeAceValue(Card[] cards) {
        while (Cards.sumCards(cards) > 21) {
            int countAce = 0;
            int countOnePointAce = 0;
            boolean firstAceInHand = false;
            for (Card playerCard : cards) {
                if (playerCard != null && playerCard.getName().equals(ACE)) {
                    countAce += 1;
                    if (playerCard.getPoints() > 1 && !firstAceInHand) {
                        playerCard.setPoints(1);
                        countOnePointAce += 1;
                        firstAceInHand = true;
                    } else if (playerCard.getPoints() == 1 && !firstAceInHand) {
                        countOnePointAce += 1;
                    }
                }
            }
            if (countAce == 0)
                break;
            if (countAce - countOnePointAce == 0)
                break;
        }
    }
}
