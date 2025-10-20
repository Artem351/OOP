package ru.nsu.pisarev;

public final class DealerPrinter {
    public static void showCardsBeforeOpen(Dealer dealer, boolean isEnd){
        System.out.print("Карты дилера:");
        if (dealer.getCards()[0].getName().equals("Туз") && dealer.getCards()[0].getPoints()==1 && !isEnd){
            dealer.getCards()[0].setPoints(11);
            CardPrinter.showCard(dealer.getCards()[0]);
            dealer.getCards()[0].setPoints(1);
        }

        System.out.println("<закрытая карта>");
    }
    public static void showFirstCardOpen(Dealer dealer){
        System.out.print("Ход дилера\n--------\n");
        System.out.print("Дилер открывает закрытую карту:");
        CardPrinter.showCard(dealer.getCards()[1]);
        System.out.println();
    }
    public static void printNeedAmountOfDealerCards(Dealer dealer, int amount){
        System.out.print("Карты дилера:");
        int i=0;
        for (Card card : dealer.getCards()) {
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
        while (dealer.getCards()[i]!=null) {
            System.out.print("Дилер открывает карту:");
            CardPrinter.showCard(dealer.getCards()[i]);
            i++;
            System.out.println();
        }
    }

    public static void showDealerCardsAfterOpen(Dealer dealer){
        System.out.print("Карты дилера:");
        CardPrinter.showCards(dealer.getCards());
        System.out.println("=> "+ Cards.sumCards(dealer.getCards()));
    }

    private DealerPrinter() {
        throw new UnsupportedOperationException();
    }
}
