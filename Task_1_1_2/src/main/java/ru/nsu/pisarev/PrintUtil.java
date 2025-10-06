package ru.nsu.pisarev;

public final class PrintUtil {
    static void printCardsDealerAndPlayer(Player player,Dealer dealer,boolean isEnd){

        if (!isEnd){
            player.showCards();
            dealer.showCardsBeforeOpen();
        }
        else{
            dealer.showFirstCardOpen();
            player.showCards();
            dealer.showDealerCardsAfterOpen();
            boolean decision = false;
            decision = dealer.needPickCards();
            while(decision) {
                if (decision){
                    System.out.println();
                    System.out.print("Дилер открывает карту:");
                    Card dnewcard = createCard();
                    dnewcard.showCard();
                    int i=-1;
                    for (Card dealerCard : dealer.dealerCards) {
                        i++;
                        if (dealerCard==null)
                            break;
                    }
                    dealer.dealerCards[i]=dnewcard;
                    System.out.println();
                }
                player.showCards();
                dealer.showDealerCardsAfterOpen();
                decision = dealer.needPickCards();
            }
        }
    }

    private PrintUtil() {
        throw new UnsupportedOperationException();
    }
}
