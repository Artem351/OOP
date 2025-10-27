package ru.nsu.pisarev;

import java.util.Random;

public class Game {
    public static final String[] SUIT_ARRAY = {"Пики", "Черви", "Бубны", "Трефы"};
    public static final String[] NAMES_ARRAY = {"Двойка", "Тройка", "Четвёрка", "Пятёрка", "Шестёрка", "Семёрка", "Восьмёрка", "Девятка", "Десятка",
            "Валет", "Дама", "Король", "Туз"};

    public static final int MAXIMAL_CARDS = 20;

    public static Dealer createDealer() {
        Dealer dealer = new Dealer();
        dealer.addCard(Card.createCard());
        dealer.addCard(Card.createCard());
        boolean decision = dealer.moreCardsNeeded();
        while (decision) {
            Card dNewCard = Card.createCard();
            dealer.addCard(dNewCard);
            System.out.println();
            decision = dealer.moreCardsNeeded();
        }
        return dealer;
    }

    public static Card addPlayerCard(Player player) {
        Card playerCardN = Card.createCard();
        player.addCard(playerCardN);
        player.changeAceValue();
        return playerCardN;
    }
}
