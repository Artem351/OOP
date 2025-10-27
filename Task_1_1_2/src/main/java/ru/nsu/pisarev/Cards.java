package ru.nsu.pisarev;

import static ru.nsu.pisarev.Card.ACE;

public final class Cards {

    public static int sumCards(Card[] cards, int cardsCount) {
        int sum = 0;
        for (int i = 0; i < cardsCount; i++) {
            sum += cards[i].getPoints();
        }
        return sum;
    }

    public static void changeAceValue(Card[] cards, int cardsCount) {
        while (Cards.sumCards(cards, cardsCount) > 21) {
            int countAce = 0;
            int countOnePointAce = 0;
            boolean firstAceInHand = false;
            for (Card playerCard : cards) {
                if (playerCard != null && playerCard.getName().equals(ACE)) {
                    countAce += 1;
                    if (playerCard.getPoints() > 1 && !firstAceInHand) {
                        playerCard.decreaseAceValue();
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
