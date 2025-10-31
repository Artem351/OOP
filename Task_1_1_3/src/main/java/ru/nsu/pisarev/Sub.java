package ru.nsu.pisarev;

public record Sub(Expression e1, Expression e2) implements Expression {

    @Override
    public Expression derivative(String var) {
        Expression eDerivative1 = this.e1.derivative(var);
        Expression eDerivative2 = this.e2.derivative(var);
        return new Sub(eDerivative1, eDerivative2);
    }

    @Override
    public int eval(String vars) {
        return this.e1.eval(vars) - this.e2.eval(vars);
    }

    @Override
    public Expression simplification() {
        Expression expr1 = this.e1.simplification();
        Expression expr2 = this.e2.simplification();
        if (expr1 instanceof Number && expr2 instanceof Number) {
            return new Number(((Number) expr1).n() - ((Number) expr2).n());
        }
        return new Sub(expr1, expr2);
    }

    @Override
    public String toString() {
        return "(" + e1 + "-" + e2 + ")";
    }
}

