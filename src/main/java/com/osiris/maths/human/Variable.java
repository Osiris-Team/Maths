package com.osiris.maths.human;

public class Variable {
    public String name;
    public Double value;
    public Operator operator;

    public Variable(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public static Variable parse(String s){
        s = s.trim();
        Operator operator = Operator.PLUS;
        if(s.contains("-")) operator = Operator.MINUS;
        else if(s.contains("*")) operator = Operator.MULTIPLY;
        else if(s.contains("/")) operator = Operator.DIVIDE;
        String name = s.replaceAll("[0-9.,+\\-*/]", "");
        Double value = Double.parseDouble(s.replaceAll("[^0-9.,]", ""));
        Variable var = new Variable(name, value);
        var.operator = operator;
        return var;
    }

    public enum Operator{
        PLUS,MINUS,MULTIPLY,DIVIDE,
    }
}
