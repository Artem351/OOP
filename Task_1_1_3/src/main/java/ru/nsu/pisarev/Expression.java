package ru.nsu.pisarev;

public interface Expression {
    String print();
    Expression derivative(String var);
    int eval(String vars);
    Expression simplification();
}
