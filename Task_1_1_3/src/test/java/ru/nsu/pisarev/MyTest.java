package ru.nsu.pisarev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MyTest {
    @Test
    void checkExpressionPrint() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x"))); // (3+(2*x))
        String rightAnswer = "(3+(2*(x)))";
        assertEquals(rightAnswer,e.toString());
        assertTrue(true);
    }

    @Test
    void checkDerivativeExpressionPrint() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x"))); // (3+(2*x))
        Expression de = e.derivative("x");
        String rightAnswer = "(0+((0*(x))+(2*1)))";
        assertEquals(rightAnswer,de.toString());
        assertTrue(true);
    }
    @Test
    void checkEval(){

        Expression e3 = new Add(new Number(3), new Mul(new Variable("yyy"),
                new Variable("x")));
        int result = e3.eval("x = 10; yyy = 13");
        if (result!=133)
            fail();
        Expression e4 = new Div(new Variable("x"), new Variable("y"));
        try {
            e4.eval("x = 1; y = 0");
        } catch(ArithmeticException e){
            System.out.println(e.getMessage());
        }
        assertTrue(true);
    }


    @Test
    void checkSimplification(){

        Expression e4 = new Add(new Sub(new Number(5),new Variable("x")), new Mul(new Number(5), new Div(new Number(12),new Number(2))));

        String rightAnswer = "((5-(x))+30)";
        Expression se4 = e4.simplification();
        assertEquals(rightAnswer,se4.toString());
        assertTrue(true);
    }

    @Test
    void checkExpressionParser(){

        Expression e4 = ExpressionParser.parse("5-x+30");
        String rightAnswer = "((5-(x))+30)";
        assertEquals(rightAnswer,e4.toString());
        assertTrue(true);
    }
}

