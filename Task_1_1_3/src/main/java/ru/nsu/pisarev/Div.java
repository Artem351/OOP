package ru.nsu.pisarev;

public record Div(Expression e1, Expression e2) implements Expression {

    @Override
    public Expression derivative(String var) {
        Expression eDerivative1 = this.e1.derivative(var);
        Expression eDerivative2 = this.e2.derivative(var);
        return new Div
                (
                        new Sub
                                (
                                        new Mul(eDerivative1, e2),
                                        new Mul(e1, eDerivative2)
                                ),
                        new Mul
                                (
                                        e2,
                                        e2
                                )
                );
    }

    @Override
    public int eval(String vars) {
        return this.e1.eval(vars) / this.e2.eval(vars);
    }

    @Override
    public Expression simplification() {
        Expression expr1 = this.e1.simplification();
        Expression expr2 = this.e2.simplification();
        if (expr1 instanceof Number && expr2 instanceof Number) {
            return new Number(((Number) expr1).n() / ((Number) expr2).n());
        }
        return new Div(expr1, expr2);
    }

    @Override
    public String toString() {
        return "(" + e1 + "/" + e2 + ")";
    }
}

