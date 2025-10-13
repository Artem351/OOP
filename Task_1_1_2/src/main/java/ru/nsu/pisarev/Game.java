package ru.nsu.pisarev;

import java.util.Random;

public class Game {
    public static final String[] SUIT_ARRAY = {"Пики", "Черви", "Бубны", "Трефы"};
    public static final String[] NAMES_ARRAY = {"Двойка", "Тройка", "Четвёрка", "Пятёрка", "Шестёрка", "Семёрка", "Восьмёрка", "Девятка", "Десятка",
            "Валет", "Дама", "Король", "Туз"};

    private static final int MAXIMAL_CARDS = 20;

    public static Player createPlayer() {
        Card[] cards = new Card[MAXIMAL_CARDS];
        cards[0] = createCard();
        cards[1] = createCard();
        return new Player(cards);
    }

    public static Dealer createDealer() {
        Card[] cards = new Card[MAXIMAL_CARDS];
        cards[0] = createCard();
        cards[1] = createCard();
        Dealer dealer = new Dealer(cards);
        boolean decision = false;
        decision = dealer.moreCardsNeeded();
        while (decision) {
            if (decision) {
                Card dnewcard = createCard();
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

    public static Card AddCardPlayer(Player player) {
        Card playerCardN = Game.createCard();
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

    private static Card createCard() {
        Random rd = new Random();
        int typeId = rd.nextInt(4);
        int nameId = rd.nextInt(13);
        return new Card(nameId, NAMES_ARRAY[nameId], SUIT_ARRAY[typeId]);
    }

    static Card createCard(int nameId) {
        Random rd = new Random();
        int typeId = rd.nextInt(4);
        return new Card(nameId, NAMES_ARRAY[nameId], SUIT_ARRAY[typeId]);
    }
}
