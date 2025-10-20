package ru.nsu.pisarev;

public final class PlayerPrinter {
    public void ShowPlayerNewCard(Card playerCardN){
        System.out.print("Вы открыли карту:");
        CardPrint.showCard(playerCardN);
        System.out.println();
    }

    private PlayerPrinter() {
        throw new UnsupportedOperationException();
    }
}

