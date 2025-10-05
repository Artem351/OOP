package ru.nsu.pisarev;

public class Card extends Cards{

    public Card(int nameId,String cardName, String cardSuit) {
        this.cardName = cardName;
        this.cardSuit = cardSuit;
        if (nameId<9)
            this.points = nameId+2;
        else
        {
            if (nameId<12)
                this.points = 10;
            else
                this.points = 11;
        }
    }
    public int getPoints() {
        return points;
    }

    public String getCardName() {
        return cardName;
    }

    public void setPoints(int points) {
        if (points>=2)
            this.points = points;
        else {
            System.err.println("Invalid points");
            this.points = 2;
        }
    }

    public void showCard(){
        System.out.print("<"+this.cardName+" "+this.cardSuit+" ("+this.points+")> ");
    }
}
