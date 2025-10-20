package ru.nsu.pisarev;

public final class GamePrinter {
    static void printCardsDealerAndPlayer(Player player,Dealer dealer,boolean isEnd){

        if (!isEnd){
            player.showCards();
            DealerPrinter.showCardsBeforeOpen(dealer,isEnd);
        }
        else{
            DealerPrinter.showFirstCardOpen(dealer);
            player.showCards();
            DealerPrinter.printNeedAmountOfDealerCards(dealer,3);
            DealerPrinter.simulateOpenDealerAllCards(dealer);

            System.out.println("Дилер завершает свой ход.");
            player.showCards();
            DealerPrinter.showDealerCardsAfterOpen(dealer);

        }
    }
    public static int showPlayerRoundResult(Player player, Dealer dealer, int playerWins,int dealerWins){

        System.out.println();
        if (winPlayer(player,dealer)==1){
            System.out.println("Вы выиграли раунд!");
            playerWins+=1;
        }
        else if (winPlayer(player,dealer) == 0){
            System.out.println("Дилер выиграл раунд!");
            dealerWins+=1;
        }else{
            System.out.println("Ничья!");
        }
        System.out.print("Счет ");
        if (playerWins > dealerWins) {
            System.out.println(playerWins + ":" + dealerWins + " в вашу пользу");
        } else {
            if (playerWins == dealerWins) {
                System.out.println(playerWins + ":" + dealerWins);
            } else {
                System.out.println(dealerWins + ":" + playerWins+" в пользу Дилера");
            }
        }
        return winPlayer(player,dealer);
    }
    private static int winPlayer(Player player,Dealer dealer){
        int playerSum= player.sumCards();
        int dealerSum = dealer.sumCards();
        if (playerSum<=21 && dealerSum>21)
            return 1;
        if (playerSum ==21 && dealerSum!=21)
            return 1;
        if (playerSum> 21 && dealerSum<=21)
            return 0;
        if (playerSum !=21 && dealerSum == 21)
            return 0;
        return playerSum > dealerSum ? 1: (playerSum==dealerSum ?2:0);

    }

    private GamePrinter() {
        throw new UnsupportedOperationException();
    }
}

