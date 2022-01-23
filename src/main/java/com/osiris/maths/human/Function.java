package com.osiris.maths.human;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Function {
    public String string;
    public String name;
    public List<Variable> variables = new ArrayList<>();

    /**
     * Creates a function object out of the provided string.
     */
    public Function(String string) throws Exception {
        this.string = string;
        // Convert the provided string into a Function object aka parse it:
        int indexOfFirstBrace = string.indexOf("(");
        if (indexOfFirstBrace==-1) throw new Exception("Failed to find function name! Example input: 'f(x) = ...' Example name: 'f'");
        name = string.substring(0, indexOfFirstBrace);
        int indexOfEqualSign = string.indexOf("=");
        if (indexOfEqualSign==-1) throw new Exception("Failed to find a '=' in the provided function!");
        String function = string.substring(indexOfEqualSign+1).replaceAll(" ", "");
        try(BufferedReader reader = new BufferedReader(new StringReader(function))){
            int charAsInt = 0;
            char c = 0;
            char cBefore = 0;
            boolean cIsDigit = false;
            boolean cIsDigitBefore = false;
            int i = 0;
            StringBuilder number = new StringBuilder();
            while ((charAsInt = reader.read()) != -1){
                c = (char) charAsInt;
                cIsDigit = Utils.isDigit(c);
                if (cIsDigit || c == ',' || c == '.' || c == '-' || c == '+'){
                    number.append(c);
                }
                else{ // not a digit, but variable name
                    if (!cIsDigitBefore){ // ax
                        variables.add(new Variable(c, 1.0));
                    } else {
                        variables.add(new Variable(c, Double.parseDouble(number.toString())));
                        number = new StringBuilder();
                    }
                }
                cIsDigitBefore = cIsDigit;
                i++;
            }
            String lastNumber = number.toString().trim();
            if(!lastNumber.isEmpty()) variables.add(new Variable((char)0, Double.parseDouble(lastNumber)));
        }
    }

    public void print(){
        print(System.out);
    }

    public void print(PrintStream out){
        out.println("Details for object: "+this);
        out.println("Provided string: "+string);
        out.println("Name: "+name);
        out.print("Variables: ");
        for (Variable var :
                variables) {
            out.print(var.name+"="+var.value+" ");
        }
        out.println();
    }
}
