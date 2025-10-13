package ru.nsu.pisarev;

public final class PlayerPrint {
    public void ShowPlayerNewCard(Card pcardn){
        System.out.print("Вы открыли карту:");
        CardPrint.showCard(pcardn);
        System.out.println();
    }

    private PlayerPrint() {
        throw new UnsupportedOperationException();
    }
}
