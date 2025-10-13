package TaskVault.task_1_1_3;

public class Mul extends Expression {
    private Expression e1;
    private Expression e2;
    public Mul(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print() {
        System.out.print("(");
        this.e1.print();
        System.out.print("*");
        this.e2.print();
        System.out.print(")");
    }
    @Override
    public Expression derivative(String var) {
        Expression eDerivative1 = this.e1.derivative(var);
        Expression eDerivative2 = this.e2.derivative(var);
        return new Add(new Mul(eDerivative1,e2),
                    new Mul(e1,eDerivative2));
    }

    @Override
    public int eval(String vars) {
        return this.e1.eval(vars)*this.e2.eval(vars);
    }

    @Override
    public Expression simplification() {
        Expression expr1 = this.e1.simplification();
        Expression expr2 = this.e2.simplification();
        if (expr1 instanceof Number && expr2 instanceof Number){
            return new Number(((Number) expr1).getN()*((Number) expr2).getN());
        }
        if (expr1 instanceof Number && expr1.equals(new Number(1))){
            return expr2;
        }
        if (expr2 instanceof Number && expr2.equals(new Number(1))){
            return expr1;
        }
        if (expr1 instanceof Number && expr1.equals(new Number(0))
                || expr2 instanceof Number && expr2.equals(new Number(0))){
            return new Number(0);
        }
        return new Mul(expr1,expr2);
    }
}
