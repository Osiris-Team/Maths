package com.osiris.maths;

import com.osiris.maths.binary.Bits;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // TODO Add N(negative) O(overflow) C(carry) Z(all zero) flags. Understand meaning of carry flag and its value for the result
        System.out.println("Initialised CLI, available commands:");
        System.out.println("exit   | Exits the CLI");
        System.out.println("from bin <bits>   | Converts provided bits to whole decimal.");
        System.out.println("to state <state>   | Changes the last bits to the demanded state: '1s' or '2s' or 'none'");
        System.out.println("to bin <decimal number>   | Converts provided whole decimal (+ or -) to binary.");
        System.out.println("to bin <decimal number> debug   | Same as above, but shows the steps.");
        System.out.println("logic-add <decimal number1> <decimal number2>   | Performs a logical AND operation for each of the bits.");
        System.out.println("logic-or <decimal number1> <decimal number2>   | Performs a logical AND operation for each of the bits.");
        System.out.println("logic-xor <decimal number1> <decimal number2>   | Performs a logical AND operation for each of the bits.");
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        Bits lastBits = null;
        while (!exit){
            String line = scanner.nextLine();
            if(line.equals("exit")) exit = true;
            else if (line.startsWith("to bin ") ){
                if(line.contains("debug")) lastBits = Bits.from(Integer.parseInt(line.substring("to bin ".length() - 1, line.indexOf("debug")).trim()), true);
                else lastBits = Bits.from(Integer.parseInt(line.substring("to bin ".length() - 1).trim()));
                System.out.println(lastBits);
            } else if (line.startsWith("from bin ") ){
                lastBits = Bits.from(line.substring("from bin ".length() - 1).replaceAll(" ", "").trim());
                System.out.println(lastBits);
            } else if (line.startsWith("to state") ){
                if(lastBits==null) System.err.println("Last bits is null!");
                else {
                    String state = line.split(" ")[2].trim().toLowerCase(Locale.ROOT);
                    if(state.equals("1s")){
                        lastBits.to(Bits.State.COMPLEMENT_1);
                    } else if(state.equals("2s")){
                        lastBits.to(Bits.State.COMPLEMENT_2);
                    } else if(state.equals("none")){
                        lastBits.to(Bits.State.UNMODIFIED);
                    } else{
                        System.err.println("Unknown state: "+state);
                    }
                }
                System.out.println(lastBits);
            } else if(line.startsWith("logic-add ")){
                String[] s = line.split(" ");
                lastBits = Bits.from(Integer.parseInt(s[1])).and(Bits.from(Integer.parseInt(s[2])));
                System.out.println(lastBits);
            } else if(line.startsWith("logic-or ")){
                String[] s = line.split(" ");
                lastBits = Bits.from(Integer.parseInt(s[1])).or(Bits.from(Integer.parseInt(s[2])));
                System.out.println(lastBits);
            } else if(line.startsWith("logic-xor ")){
                String[] s = line.split(" ");
                lastBits = Bits.from(Integer.parseInt(s[1])).xor(Bits.from(Integer.parseInt(s[2])));
                System.out.println(lastBits);
            }
            else{
                System.err.println("Unknown command: "+line);
            }
        }
    }

}
