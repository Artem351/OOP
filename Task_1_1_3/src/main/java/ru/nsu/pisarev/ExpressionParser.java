package ru.nsu.pisarev;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class ExpressionParser {

    public static final Set<String> OPERATORS = Set.of("+", "-", "*", "/");

    public static Expression parse(String input) {
        List<String> tokens = tokenize(input);
        return parseExpression(tokens);
    }


    private static List<String> tokenize(String s) {
        List<String> tokens = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c))
                continue;
            if (OPERATORS.contains(String.valueOf(c))) {
                if (!cur.isEmpty()) {
                    tokens.add(cur.toString());
                    cur.setLength(0);
                }
                tokens.add(String.valueOf(c));
            } else {
                cur.append(c);
            }
        }
        if (!cur.isEmpty())
            tokens.add(cur.toString());
        return tokens;
    }

    // Парсер с приоритетами: * / выше + -
    private static Expression parseExpression(List<String> tokens) {
        Stack<Object> output = new Stack<>();
        Stack<String> ops = new Stack<>();

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);

            if (token.equals("(")) {
                ops.push(token);
            }
            else if (token.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("("))
                    processOp(output, ops.pop());
                if (!ops.isEmpty() && ops.peek().equals("(")) ops.pop();
            }
            else if (OPERATORS.contains(token)) {
                // обработка унарного минуса
                if (token.equals("-") && (i == 0 || (OPERATORS.contains(tokens.get(i - 1)) || tokens.get(i - 1).equals("(")))) {
                    // унарный минус → 0 - expr
                    output.push(new Number(0));
                }
                while (!ops.isEmpty() && priority(ops.peek()) >= priority(token)) {
                    processOp(output, ops.pop());
                }
                ops.push(token);
            }
            else {
                output.push(parseAtom(token));
            }
        }
        while (!ops.isEmpty()) processOp(output, ops.pop());
        return (Expression) output.pop();
    }

    private static int priority(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }

    private static void processOp(List<Object> stack, String op) {
        Expression right = (Expression) stack.remove(stack.size() - 1);
        Expression left = (Expression) stack.remove(stack.size() - 1);
        switch (op) {
            case "+":
                stack.add(new Add(left, right));
                break;
            case "-":
                stack.add(new Sub(left, right));
                break;
            case "*":
                stack.add(new Mul(left, right));
                break;
            case "/":
                stack.add(new Div(left, right));
                break;
        }
    }

    private static Expression parseAtom(String token) {
        if (token.matches("-?\\d+"))
            return new Number(Integer.parseInt(token));
        else
            return new Variable(token);
    }
}
