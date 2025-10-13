package ru.nsu.pisarev;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Test {
    @Test
    void checkExpressionPrint() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x"))); // (3+(2*x))
        String rightAnswer = "(3+(2*x))";
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        try {
            System.setOut(printStream);
            e.print();
        } finally {
            System.setOut(originalOut);
        }
        String output = outputStream.toString();
        if (!output.equals(rightAnswer))
            fail();
        assertTrue(true);
    }

    @Test
    void checkDerivateExpressionPrint() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x"))); // (3+(2*x))
        Expression de = e.derivative("x");
        String rightAnswer = "(3+(2*x))";
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        try {
            System.setOut(printStream);
            de.print();
        } finally {
            System.setOut(originalOut);
        }
        String output = outputStream.toString();
        if (!output.equals(rightAnswer))
            fail();
        assertTrue(true);
    }
    @Test
    void checkEval(){

        Expression e3 = new Add(new Number(3), new Mul(new Variable("yyy"),
                new Variable("x")));
        int result = e3.eval("x = 10; yyy = 13");
        if (result!=133)
            fail();
        assertTrue(true);
    }


    @Test
    void checkSimplification(){

        Expression e4 = new Add(new Sub(new Number(5),new Variable("x")), new Mul(new Number(5), new Div(new Number(12),new Number(2))));

        String rightAnswer = "((5-x)+30)";
        Expression se4 = e4.simplification();
        se4.print();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        try {
            System.setOut(printStream);
            se4.print();
        } finally {
            System.setOut(originalOut);
        }
        String output = outputStream.toString();
        if (!output.equals(rightAnswer))
            fail();
        assertTrue(true);
    }
}
