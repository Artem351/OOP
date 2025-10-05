package ru.nsu.pisarev;

import java.util.Random;
import java.util.Scanner;

public class Runner {
    final static String[] SUIT_ARRAY = {"Пики","Черви","Бубны","Трефы","d","a","g","h"};
    final static String[] NAMES_ARRAY = {"Двойка","Тройка","Четвёрка","Пятёрка","Шестёрка","Семёрка","Восьмёрка","Девятка","Десятка",
            "Валет","Дама","Король","Туз"};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int roundNumber=1;
        int playerWins = 0;
        int dealerWins = 0;
        System.out.println("Добро пожаловать в Блэкджек!");
        while (true) {
            boolean isEnd = false;
            System.out.println("Раунд " + roundNumber);
            Card pcard = createCard();
            Card pcard2 = createCard();
            Card[] playerCards = new Card[10];
            playerCards[0] = pcard;
            playerCards[1] = pcard2;
            Player player = new Player(playerCards);
            Card dcard = createCard();
            Card dcard2 = createCard();
            Card[] dealerCards = new Card[10];
            dealerCards[0] = dcard;
            dealerCards[1] = dcard2;
            Dealer dealer = new Dealer(dealerCards);
            playerCards[0].changeAceValue(playerCards);
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
        int playerSum= player.playerCards[0].sumCards(player.playerCards);
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
    private static void printCardsDealerAndPlayer(Player player,Dealer dealer,boolean isEnd){

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


    private static Card createCard(){
        Random rd = new Random();
        int typeId = rd.nextInt(4);
        int nameId = rd.nextInt(13);
        Card card = new Card(nameId, NAMES_ARRAY[nameId] ,SUIT_ARRAY[typeId]);
        return card;
    }
    private static Card createCard(int nameId){
        Random rd = new Random();
        int typeId = rd.nextInt(4);
        Card card = new Card(nameId, NAMES_ARRAY[nameId] ,SUIT_ARRAY[typeId]);
        return card;
    }
}
