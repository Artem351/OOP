package ru.nsu.pisarev;

public final class DealerPrint {
    public static void showCardsBeforeOpen(Dealer dealer, boolean isEnd){
        System.out.print("Карты дилера:");
        if (dealer.cards[0].getName().equals("Туз") && dealer.cards[0].getPoints()==1 && !isEnd){
            dealer.cards[0].setPoints(11);
            CardPrint.showCard(dealer.cards[0]);
            dealer.cards[0].setPoints(1);
        }

        System.out.println("<закрытая карта>");
    }
    public static void showFirstCardOpen(Dealer dealer){
        System.out.print("Ход дилера\n--------\n");
        System.out.print("Дилер открывает закрытую карту:");
        CardPrint.showCard(dealer.cards[1]);
        System.out.println();
    }
    public static void printNeedAmountOfDealerCards(Dealer dealer, int amount){
        System.out.print("Карты дилера:");
        int i=0;
        for (Card card : dealer.cards) {
            i++;
            if (i<amount){

                CardPrint.showCard(card);
            }
            else{
                break;
            }
        }
        System.out.println();
    }

    public static void simulateOpenDealerAllCards(Dealer dealer){
        System.out.println();
        int i=2;
        while (dealer.cards[i]!=null) {
            System.out.print("Дилер открывает карту:");
            CardPrint.showCard(dealer.cards[i]);
            i++;
            System.out.println();
        }
    }

    public static void showDealerCardsAfterOpen(Dealer dealer){
        System.out.print("Карты дилера:");
        CardPrint.showCards(dealer.cards);
        System.out.println("=> "+ Cards.sumCards(dealer.cards));
    }

    private DealerPrint() {
        throw new UnsupportedOperationException();
    }
}
