package ru.nsu.pisarev;

import java.util.Random;

public class Game {
    final static String[] SUIT_ARRAY = {"Пики","Черви","Бубны","Трефы"};
    final static String[] NAMES_ARRAY = {"Двойка","Тройка","Четвёрка","Пятёрка","Шестёрка","Семёрка","Восьмёрка","Девятка","Десятка",
            "Валет","Дама","Король","Туз"};

    private static final int MAXIMAL_CARDS = 10;

    public static Player createPlayer() {
        Card[] cards = new Card[MAXIMAL_CARDS];
        cards[0] = createCard();
        cards[1] = createCard();
        return new Player(cards);
    }

    private static Card createCard(){
        Random rd = new Random();
        int typeId = rd.nextInt(4);
        int nameId = rd.nextInt(13);
        return new Card(nameId, NAMES_ARRAY[nameId] ,SUIT_ARRAY[typeId]);
    }

    static Card createCard(int nameId){
        Random rd = new Random();
        int typeId = rd.nextInt(4);
        return new Card(nameId, NAMES_ARRAY[nameId] ,SUIT_ARRAY[typeId]);
    }
}
