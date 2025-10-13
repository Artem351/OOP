package TaskVault.task_1_1_3;

public class Sub extends Expression {
    private Expression e1;
    private Expression e2;
    public Sub(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print() {
        System.out.print("(");
        this.e1.print();
        System.out.print("-");
        this.e2.print();
        System.out.print(")");
    }
    @Override
    public Expression derivative(String var) {
        Expression eDerivative1 = this.e1.derivative(var);
        Expression eDerivative2 = this.e2.derivative(var);
        return new Sub(eDerivative1,eDerivative2);
    }

    @Override
    public int eval(String vars) {
        return this.e1.eval(vars)-this.e2.eval(vars);
    }
    @Override
    public Expression simplification() {
        Expression expr1 = this.e1.simplification();
        Expression expr2 = this.e2.simplification();
        if (expr1 instanceof Number && expr2 instanceof Number){
            return new Number(((Number) expr1).getN() - ((Number) expr2).getN());
        }
        return new Sub(expr1,expr2);
    }
}
