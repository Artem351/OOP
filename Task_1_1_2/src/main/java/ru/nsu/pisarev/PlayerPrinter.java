package ru.nsu.pisarev;

public final class PlayerPrinter {
    public static void ShowPlayerNewCard(Card playerCardN){
        System.out.print("Вы открыли карту:");
        CardPrinter.showCard(playerCardN);
        System.out.println();
    }

    private PlayerPrinter() {
        throw new UnsupportedOperationException();
    }
}


