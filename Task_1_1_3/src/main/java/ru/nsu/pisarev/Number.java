package ru.nsu.pisarev;

public record Number(int n) implements Expression {

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
        return new Number(this.n());
    }


    @Override
    public String toString() {
        return Integer.toString(n);
    }
}

