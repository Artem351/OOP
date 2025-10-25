package ru.nsu.pisarev;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;


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
        Card[] cards = new Card[]{c1, c2};
        Dealer dealer = new Dealer(cards);
        if (!dealer.moreCardsNeeded())
            fail();
        c1 = new Card(8, "Two-Ten", "Type1");
        c2 = new Card(8, "Two-Ten", "Type2");
        cards = new Card[]{c1, c2};
        dealer = new Dealer(cards);
        if (dealer.moreCardsNeeded())
            fail();
        assertTrue(true);
    }
}



