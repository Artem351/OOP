package ru.nsu.pisarev;

import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int roundNumber=1;
        int playerWins = 0;
        int dealerWins = 0;
        System.out.println("Добро пожаловать в Блэкджек!");
        while (true) {
            boolean isEnd = false;
            System.out.println("Раунд " + roundNumber);
            Player player = Game.createPlayer();
            //TODO
            Card dcard = createCard();
            Card dcard2 = createCard();
            Card[] dealerCards = new Card[10];
            dealerCards[0] = dcard;
            dealerCards[1] = dcard2;
            Dealer dealer = new Dealer(dealerCards);

            player.changeAceValue();
            printCardsDealerAndPlayer(player, dealer, isEnd);
            int inp=1;
            int pSum=playerCards[0].sumCards(playerCards);
            int dSum;
            while(inp==1 && pSum<21) {
                 do {
                    System.out.println("Ваш ход\n------\nВведите \"1\", чтобы взять карту, и \"0\",чтобы остановиться...");
                    inp = sc.nextInt();
                }while (inp!=1 && inp!=0);
                if (inp == 1) {
                    Card pcardn = createCard();
                    int i=-1;
                    for (Card playerCard : playerCards) {
                        i++;
                        if (playerCard==null)
                            break;
                    }
                    playerCards[i]=pcardn;
                    playerCards[0].changeAceValue(playerCards);
                    System.out.print("Вы открыли карту:");
                    pcardn.showCard();
                    System.out.println();
                    printCardsDealerAndPlayer(player, dealer, isEnd);

                } else {
                    System.out.println("--------------------------------");
                    isEnd = true;
                    dealerCards[0].changeAceValue(dealerCards);
                    printCardsDealerAndPlayer(player, dealer, isEnd);
                }
                pSum=playerCards[0].sumCards(playerCards);
            }
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
            roundNumber+=1;
            System.out.println("Введи \"0\", если хочешь уйти, и \"1\",чтобы остаться");
            if (sc.nextInt() == 0)
                break;
        }
    }


    private static int winPlayer(Player player,Dealer dealer){
        int playerSum= player.cards[0].sumCards(player.cards);
        int dealerSum = dealer.dealerCards[0].sumCards(dealer.dealerCards);
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
}
