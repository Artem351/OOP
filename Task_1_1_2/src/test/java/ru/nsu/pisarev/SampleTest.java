package ru.nsu.pisarev;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.nsu.pisarev.printer.DealerPrinter;
import ru.nsu.pisarev.printer.PlayerPrinter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


class SampleTest {

    @Test
    void checkGame() {
        String simulatedInput = "1 0 9 1 0 9 1 0 0";
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            Runner.main(new String[]{});
        } finally {
            System.setIn(originalIn);
        }
        assertTrue(true);
    }

    @Test
    void checkSecondGame() {
        String simulatedInput = "1 0 9 1 0 9 1 0 9 1 0 9 1 0 9 0 9 0 0";
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            Runner.main(new String[]{});
        } finally {
            System.setIn(originalIn);
        }
        assertTrue(true);
    }

    @Test
    void checkCardCreation() {
        Card card = new Card(2, "Four", "Type");
        assertEquals("Four", card.getName());
        assertEquals(4, card.getPoints());
        assertEquals("Type", card.getSuit());
    }

    @Test
    void checkCardGetPoints() {
        Card card = new Card(2, "Four", "Type");
        assertEquals(4,card.getPoints());
        assertTrue(true);
    }

    @Test
    void dealerMoreCardsNeeded() {
        Card c1 = new Card(0, "Two-Ten", "Type1");
        Card c2 = new Card(0, "Two-Ten", "Type2");
        Dealer dealer = new Dealer();
        dealer.addCards(c1, c2);
        if (!dealer.moreCardsNeeded())
            fail();
        c1 = new Card(8, "Two-Ten", "Type1");
        c2 = new Card(8, "Two-Ten", "Type2");
        dealer = new Dealer();
        dealer.addCards(c1, c2);
        if (dealer.moreCardsNeeded())
            fail();
        assertTrue(true);
    }
    @Test
    public void testShowPlayerNewCard(){
        Card playerCardN =  new Card(8, "Two-Ten", "Type1");
        PlayerPrinter.showPlayerNewCard(playerCardN);
    }
    @Test
    public void testDealerPrinter(){
        Card c1 = new Card(0, Card.ACE, "Type1");
        Card c2 = new Card(0, "Two", "Type2");
        Card c3 = new Card(0, "Four", "Type3");
        Dealer dealer = Dealer.create(c1, c2);
        Dealer dealer2 = Dealer.create(c3, c2);
        DealerPrinter.showCardsBeforeOpen(dealer,false);
        DealerPrinter.showCardsBeforeOpen(dealer,true);
        DealerPrinter.showCardsBeforeOpen(dealer2,false);
        DealerPrinter.showCardsBeforeOpen(dealer2,true);
        DealerPrinter.showFirstCardOpen(dealer);
        DealerPrinter.showFirstCardOpen(dealer2);
    }
}



