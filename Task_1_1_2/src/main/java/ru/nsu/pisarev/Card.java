package ru.nsu.pisarev;


import java.util.Objects;

public class Card {
    public static final String ACE = "Туз";
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
        if (Objects.equals(this.name, ACE)){
            this.points = 1;
        }
    }
    /*to show dealer first card-ace*/
    public void increaseAceValue() {
        if (Objects.equals(this.name, ACE)){
            this.points = 11;
        }
    }
    public String getName() {
        return name;
    }

    public String getSuit() {
        return suit;
    }
}
