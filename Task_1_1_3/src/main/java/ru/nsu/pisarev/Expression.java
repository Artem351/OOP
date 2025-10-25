package ru.nsu.pisarev;

public abstract class Expression {
    public abstract String print();
    public abstract Expression derivative(String var);
    public abstract int eval(String vars);
    public abstract Expression simplification();
}

