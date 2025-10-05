package ru.nsu.pisarev;

public class Dealer {
    public Card[] dealerCards;

    public Dealer(Card[] dealerCards) {
        this.dealerCards = dealerCards;
    }

    public void setDealerCards(Card[] dealerCards) {
        this.dealerCards = dealerCards;
    }

    public void showCardsBeforeOpen(){
        System.out.print("Карты дилера:");
        dealerCards[0].showCard();
        System.out.println("<закрытая карта>");
    }
    public void showFirstCardOpen(){
        System.out.print("Ход дилера\n--------\n");
        System.out.print("Дилер открывает закрытую карту:");
        dealerCards[1].showCard();
        System.out.println();
    }
    public void showDealerCardsAfterOpen(){
        System.out.print("Карты дилера:");
        dealerCards[0].showCards(dealerCards);
        System.out.println("=> "+dealerCards[0].sumCards(dealerCards));

    }
    public boolean needPickCards(){
        int decision;
        decision = dealerCards[0].sumCards(dealerCards);
        if (decision<=16){
            return true;
        }
        return false;
    }

}
