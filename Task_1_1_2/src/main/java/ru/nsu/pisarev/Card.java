package ru.nsu.pisarev;

public class Card {
    private int points;
    private String name;
    private String suit;

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

    public void setPoints(int points) {
        if (points >= 1)
            this.points = points;
        else {
            System.err.println("Invalid points");
            this.points = 1;
        }
    }

    public String getName() {
        return name;
    }

    public String getSuit() {
        return suit;
    }
}
