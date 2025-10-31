package ru.nsu.pisarev;

/**
 * Expression allows manipulations (creation, derivative, simplification and printing) with arithmetic expressions.
 * Descendants of this class are Addition, Subtraction, Multiplication, Division, Number constant and Variable.
 * Programmers can create their own descendants of this interface.
 * Subclasses must throw ArithmeticException when expression evaluation error occurs.

 * Subclasses must implement toString method to write expression in a line as part of bigger expression,
 */
public interface Expression {

    /**
     * Returns expression derivative
     * @param var derivative variable
     */
    Expression derivative(String var);

    /**
     * Calculate value of expression
     * @param vars variables in form of Var1 = Value1; Var2 = Value2
     * @return result of expression evaluation
     * @throws ArithmeticException in case of error during calculation
     */
    int eval(String vars);

    /**
     * Simplify expression
     */
    Expression simplification();
}
