package ru.nsu.pisarev;

import java.util.Random;

public class Game {
    public static final String[] SUIT_ARRAY = {"Пики", "Черви", "Бубны", "Трефы"};
    public static final String[] NAMES_ARRAY = {"Двойка", "Тройка", "Четвёрка", "Пятёрка", "Шестёрка", "Семёрка", "Восьмёрка", "Девятка", "Десятка",
            "Валет", "Дама", "Король", "Туз"};

    public static final int MAXIMAL_CARDS = 20;


    public static Dealer createDealer() {
        Card[] cards = new Card[MAXIMAL_CARDS];
        cards[0] = Card.createCard();
        cards[1] = Card.createCard();
        Dealer dealer = new Dealer(cards);
        boolean decision = false;
        decision = dealer.moreCardsNeeded();
        while (decision) {
            if (decision) {
                Card dnewcard = Card.createCard();
                int i = -1;
                for (Card dealerCard : dealer.getCards()) {
                    i++;
                    if (dealerCard == null)
                        break;
                }
                dealer.getCards()[i] = dnewcard;
                System.out.println();
            }
            decision = dealer.moreCardsNeeded();
        }
        return dealer;
    }

    public static Card addPlayerCard(Player player) {
        Card playerCardN = Card.createCard();
        int i = -1;
        for (Card card : player.getCards()) {
            i++;
            if (card == null)
                break;
        }
        player.getCards()[i] = playerCardN;
        player.changeAceValue();
        return playerCardN;
    }


}
