package ru.nsu.pisarev;

public class Variable extends Expression {
    private String var;
    public Variable(String var) {
        this.var = var;
    }

    @Override
    public void print() {
        System.out.print(this.var);
    }

    @Override
    public Expression derivative(String difVar) {
        if (difVar.equals(this.var))
            return new Number(1);
        return new Number(0);
    }

    @Override
    public int eval(String vars) {
        int begin = vars.indexOf(this.var +"=");
        if (begin==-1)
            begin = vars.indexOf(this.var +" =");
        begin+=this.var.length()+2;
        String substring = vars.substring(begin);
        int nextVar = substring.indexOf(';');
        String intSubstring;
        if (nextVar != -1)
            intSubstring = substring.substring(0,nextVar);
        else
            intSubstring= substring;
        intSubstring= intSubstring.replaceAll("^\\s+", "");
        int parsedInt = Integer.parseInt(intSubstring);
        return parsedInt;
    }

    @Override
    public Expression simplification() {
        return new Variable(this.var);
    }
}

