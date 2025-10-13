package TaskVault.task_1_1_3;

public abstract class Expression {
    public abstract void print();
    public abstract Expression derivative(String var);
    public abstract int eval(String vars);
    public abstract Expression simplification();
}
