package ru.nsu.pisarev;

public record Variable(String var) implements Expression {


    @Override
    public Expression derivative(String difVar) {
        if (difVar.equals(this.var))
            return new Number(1);
        return new Number(0);
    }

    @Override
    public int eval(String vars) {
        int begin = vars.indexOf(this.var + "=");
        if (begin == -1)
            begin = vars.indexOf(this.var + " =");
        begin += this.var.length() + 2;
        String substring = vars.substring(begin);
        int nextVar = substring.indexOf(';');
        String intSubstring;
        if (nextVar != -1)
            intSubstring = substring.substring(0, nextVar);
        else
            intSubstring = substring;
        intSubstring = intSubstring.replaceAll("^\\s+", "");
        return Integer.parseInt(intSubstring);

    }

    @Override
    public Expression simplification() {
        return new Variable(this.var);
    }

    @Override
    public String toString() {
        return "(" + var + ")";
    }
}

