package ru.nsu.pisarev;

public final class PlayerPrinter {
    public static void showPlayerNewCard(Card playerCardN) {
        System.out.print("Вы открыли карту:");
        CardPrinter.showCard(playerCardN);
        System.out.println();
    }

    public static void showCards(Player player) {
        System.out.print("Ваши карты:");
        CardPrinter.showCards(player.getCards());
        System.out.print(" => ");
        CardPrinter.showSum(player.getCards());
        System.out.println();
    }

    private PlayerPrinter() {
        throw new UnsupportedOperationException();
    }
}


