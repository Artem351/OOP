package ru.nsu.pisarev;


import java.util.Objects;
import java.util.Random;

public class Card {
    public static final String[] NAMES_ARRAY = {"Двойка", "Тройка", "Четвёрка", "Пятёрка", "Шестёрка", "Семёрка", "Восьмёрка", "Девятка", "Десятка",
            "Валет", "Дама", "Король", "Туз"};
    public static final String[] SUIT_ARRAY = {"Пики", "Черви", "Бубны", "Трефы"};
    public static final String ACE = "Туз";

    public static final Random rd = new Random();

    private int points;
    private final String name;
    private final String suit;

    public Card(int nameId, String name, String suit) {
        this.name = name;
        this.suit = suit;
        if (nameId < 9)
            this.points = nameId + 2;
        else {
            if (nameId < 12)
                this.points = 10;
            else
                this.points = 11;
        }
    }

    public int getPoints() {
        return points;
    }


    public void decreaseAceValue() {
        if (Objects.equals(this.name, ACE)) {
            this.points = 1;
        }
    }

    /*to show dealer first card-ace*/
    public void increaseAceValue() {
        if (Objects.equals(this.name, ACE)) {
            this.points = 11;
        }
    }

    public String getName() {
        return name;
    }

    public String getSuit() {
        return suit;
    }

    static Card createCard() {
        int typeId = rd.nextInt(4);
        int nameId = rd.nextInt(13);
        return new Card(nameId, NAMES_ARRAY[nameId], SUIT_ARRAY[typeId]);
    }

    private static Card createCard(int nameId) {
        Random rd = new Random();
        int typeId = rd.nextInt(4);
        return new Card(nameId, NAMES_ARRAY[nameId], SUIT_ARRAY[typeId]);
    }
}
