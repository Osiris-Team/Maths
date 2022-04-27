package com.osiris.maths;

import com.osiris.maths.binary.Bits;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Initialised CLI, available commands:");
        System.out.println("exit   | Exits the CLI");
        System.out.println("to bin <decimal number>   | Converts provided whole decimal (+ or -) to binary.");
        System.out.println("to bin <decimal number> debug   | Same as above, but shows the steps.");
        System.out.println("logic-add <decimal number1> <decimal number2>   | Performs a logical AND operation for each of the bits.");
        System.out.println("logic-or <decimal number1> <decimal number2>   | Performs a logical AND operation for each of the bits.");
        System.out.println("logic-xor <decimal number1> <decimal number2>   | Performs a logical AND operation for each of the bits.");
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while (!exit){
            String line = scanner.nextLine();
            if(line.equals("exit")) exit = true;
            else if (line.startsWith("to bin ") ){
                if(line.contains("debug")) System.out.println(Bits.from(Integer.parseInt(line.substring("to bin ".length() - 1, line.indexOf("debug")).trim()), true));
                else System.out.println(Bits.from(Integer.parseInt(line.substring("to bin ".length() - 1).trim())));
            } else if(line.startsWith("logic-add ")){
                String[] s = line.split(" ");
                System.out.println(Bits.from(Integer.parseInt(s[1])).and(Bits.from(Integer.parseInt(s[2]))));
            } else if(line.startsWith("logic-or ")){
                String[] s = line.split(" ");
                System.out.println(Bits.from(Integer.parseInt(s[1])).or(Bits.from(Integer.parseInt(s[2]))));
            } else if(line.startsWith("logic-xor ")){
                String[] s = line.split(" ");
                System.out.println(Bits.from(Integer.parseInt(s[1])).xor(Bits.from(Integer.parseInt(s[2]))));
            }
            else{
                System.err.println("Unknown command: "+line);
            }
        }
    }

}
