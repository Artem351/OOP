package ru.nsu.pisarev;

public record Add(Expression e1, Expression e2) implements Expression {

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
    public Expression simplification() throws RuntimeException {
        try {
            Expression expr1 = e1.simplification();
            Expression expr2 = e2.simplification();
            if (expr1 instanceof Number && expr2 instanceof Number) {
                return new Number(((Number) expr1).n() + ((Number) expr2).n());
            }
            return new Add(expr1, expr2);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public String toString() {
        return "(" + e1 + "+" + e2 + ")";
    }
}
