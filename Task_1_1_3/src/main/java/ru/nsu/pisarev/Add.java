package ru.nsu.pisarev;

public class Add implements Expression {
    private final Expression e1;
    private final Expression e2;

    public Add(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Expression derivative(String var) {
        Expression eDerivative1 = e1.derivative(var);
        Expression eDerivative2 = e2.derivative(var);
        return new Add(eDerivative1, eDerivative2);
    }

    @Override
    public int eval(String vars) {
        return e1.eval(vars)
                + e2.eval(vars);
    }

    @Override
    public Expression simplification() {
        Expression expr1 = e1.simplification();
        Expression expr2 = e2.simplification();
        if (expr1 instanceof Number && expr2 instanceof Number) {
            return new Number(((Number) expr1).getN() + ((Number) expr2).getN());
        }
        return new Add(expr1, expr2);
    }

    @Override
    public String toString() {
        return "(" + e1 + "+" + e2 + ")";
    }
}
