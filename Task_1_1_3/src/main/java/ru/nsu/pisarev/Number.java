package ru.nsu.pisarev;

public class Number extends Expression {
    private int n;
    public Number(int n) {
        this.n = n;
    }
    public int getN() {
        return n;
    }

    @Override
    public void print() {
        System.out.print(this.n);
    }
    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    @Override
    public int eval(String vars) {
        return this.n;
    }

    @Override
    public Expression simplification() {
        return new Number(this.getN());
    }
}

