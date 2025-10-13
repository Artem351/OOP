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

            player.changeAceValue();

            Dealer dealer = Game.createDealer();

            GamePrint.printCardsDealerAndPlayer(player, dealer, isEnd);
            int inp=1;
            int pSum= player.sumCards();
            int dSum;
            while(inp==1 && pSum<21) {
                do {
                    System.out.println("Ваш ход\n------\nВведите \"1\", чтобы взять карту, и \"0\",чтобы остановиться...");
                    inp = sc.nextInt();
                }while (inp!=1 && inp!=0);
                if (inp == 1) {
                    Card newCard = Game.AddCardPlayer(player);
                    GamePrint.printCardsDealerAndPlayer(player, dealer, isEnd);
                } else {
                    System.out.println("--------------------------------");
                    isEnd = true;
                    dealer.changeAceValue();
                    GamePrint.printCardsDealerAndPlayer(player, dealer, isEnd);
                }
                pSum=player.sumCards();
            }
            int result = GamePrint.showPlayerRoundResult(player,dealer,playerWins,dealerWins);
            if (result == 1)
                playerWins+=1;
            else if (result == 0)
                dealerWins+=1;
            roundNumber+=1;
            System.out.println("Введи \"0\", если хочешь уйти, и \"1\",чтобы остаться");
            if (sc.nextInt() == 0)
                break;
        }
    }



}
