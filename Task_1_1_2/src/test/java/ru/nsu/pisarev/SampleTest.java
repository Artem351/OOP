package ru.nsu.pisarev;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;


class SampleTest {

    @Test
    void checkGame(){
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
    void checkSecondGame(){
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
    void checkCardCreation(){
        Card card = new Card(2,"Four","Type");
        if (card.cardName!="Four" || card.points!=4 || card.cardSuit!="Type")
            fail();
        assertTrue(true);
    }

    @Test
    void checkCardGetPoints() {
        Card card = new Card(2,"Four","Type");
        if (card.getPoints()!=4)
            fail();
        assertTrue(true);
    }
}

