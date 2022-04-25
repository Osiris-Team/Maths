package com.osiris.maths.binary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * String of bytes that can represent anything. <br>
 * Any amount of bits/bytes can be added/removed, there is no size limit. <br>
 * There are useful methods available to show the provided binary data in human-readable form: <br>
 * - Infinitely big whole number.
 */
public class Bits {
    public enum State{
        /**
         * The bytes have not been modified/complemented.
         */
        UNMODIFIED,
        /**
         * The bytes have been modified to 1s complement,
         * which means that each bit was inverted.
         */
        COMPLEMENT_1,
        /**
         * The bytes have been modified to 2s complement,
         * which means that each bit was inverted (1s complement) and
         * 1 was added.
         */
        COMPLEMENT_2
    }
    public State state = State.UNMODIFIED;
    /**
     * Bit at index 0 in the list is LSB (least significant bit)
     * and the last bit is MSB (most significant bit).
     * In real world it's the other way around. We do it like this however bc of performance.
     * Which means that you would read this list from last index to 0 index, for correct data representation. <br>
     */
    public List<Bit> bits;

    public Bits(List<Bit> bits) {
        this.bits = bits;
    }

    public Bits(int number) {
        this.bits = from(number);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int i = 1;
        for (int j = bits.size()-1; j >= 0 ; j--) {
            s.append(bits.get(j));
            if(i==8){
                s.append(" ");
                i = 0;
            }
            i++;
        }
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode())+" MSB->LSB "+s.toString();
    }

    public String toStringLSBtoMSB() {
        StringBuilder s = new StringBuilder();
        int i = 1;
        for (Bit b : bits) {
            s.append(b);
            if(i==8){
                s.append(" ");
                i = 0;
            }
            i++;
        }
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode())+" LSB->MSB "+s.toString();
    }

    public void append(Bit... bits){
        Collections.addAll(this.bits, bits);
    }

    public void append(Byte... bytes){
        for (Byte b8 :
                bytes) {
            Collections.addAll(this.bits, b8.bits);
        }
    }

    /**
     * Converts the provided string to bits. <br>
     * Expects an string looking something like this: <br>
     * 0101010000111010101 etc... <br>
     * Expects bit order MSB -> LSB.
     */
    public static Bits fromBinaryString(String bits){
        String s = bits;
        List<Bit> actualBits = new ArrayList<>();
        for (int i = s.length()-1; i >= 0; i--) {
            if(s.charAt(i) == '1') actualBits.add(new Bit(true));
            else actualBits.add(new Bit(false));
        }
        return new Bits(actualBits);
    }

    /**
     * Converts the provided decimal number to {@link #bits} and returns it.
     */
    public List<Bit> from(int decimalNum){
        return from(decimalNum, false);
    }

    /**
     * Converts the provided decimal number to {@link #bits} and returns it.
     */
    public List<Bit> from(int decimalNum, boolean printSteps){
        List<Bit> bits = new ArrayList<>();
        int quotient = decimalNum;
        int remainder = 0;
        do {
            if(printSteps) System.out.print(quotient + "/2=");
            remainder = quotient % 2;
            if (remainder == 0) bits.add(new Bit(false));
            else bits.add(new Bit(true));
            quotient = quotient / 2;
            if(printSteps) System.out.println(quotient + "  r:" + remainder);
        } while (quotient != 0);
        return bits;
    }

    /**
     * Converts the current number/{@link #bits}
     * to the new state and returns the new number.
     */
    public Bits to(State newState){
        List<Bit> copy = new ArrayList<>();
        Collections.copy(copy, bits);
        Bits newNum = new Bits(copy);
        newNum.state = state;
        switch (newState){
            case COMPLEMENT_1:
                if(state==State.UNMODIFIED){
                    toComplement1(newNum);
                }
                else if(state == State.COMPLEMENT_1){}
                else if(state == State.COMPLEMENT_2){ // TODO

                }
                newNum.state = State.COMPLEMENT_1;
                break;
            case COMPLEMENT_2:
                newNum.state = State.COMPLEMENT_2;
                break;
            default: // case UNMODIFIED
                if(state==State.UNMODIFIED){}
                else if(state == State.COMPLEMENT_1){}
                else if(state == State.COMPLEMENT_2){ // TODO

                }
                break;
        }
        return newNum;
    }
    private void toComplement1(Bits num){
        for (Bit b : num.bits) {
            b.value = !b.value;
        }
    }
    private void toComplement2(Bits num){
        toComplement1(num);
        add(new Bits(1));
    }

    /**
     * Adds the provided number to the current one.
     */
    public Bits add(Bits num){
        Bit bOverflow = new Bit(false);
        // Fill missing bits of smaller list
        if(bits.size() != num.bits.size()){
            if(bits.size() < num.bits.size()){
                for (int i = 0; i <= num.bits.size() - bits.size(); i++) {
                    bits.add(new Bit(false));
                }
            } else{
                for (int i = 0; i <= bits.size() - num.bits.size(); i++) {
                    num.bits.add(new Bit(false));
                }
            }
        }
        System.out.println("Addition:");
        System.out.println(num);
        System.out.println("+");
        System.out.println(this);
        System.out.println("=");
        boolean lastOverflow = false;
        boolean nextOverflow = false;
        for (int i = 0; i < this.bits.size(); i++) {
            boolean b0 = this.bits.get(i).value;
            boolean b1 = num.bits.get(i).value;
            if(!b0 && !b1){ // 0 0
                System.out.println("["+i+"] 0+0=0");
                this.bits.set(i, new Bit(false));
            }
            else if(b0 && b1){ // 1 1
                System.out.println("["+i+"] 1+1=0 overflow:1");
                this.bits.set(i, new Bit(false));
                nextOverflow = true;
            }
            else{ // 0 1 or 1 0
                System.out.println("["+i+"] 1+0 or 0+1 =1");
                this.bits.set(i, new Bit(true));
            }
            // Add last overflow to result if existing
            if(lastOverflow){
                if(this.bits.get(i).value){ // 1 1
                    System.out.println("["+i+"/overflow] 1+1=0 overflow:1");
                    this.bits.set(i, new Bit(false));
                    nextOverflow = true;
                }
                else{ // 0 1 or 1 0
                    System.out.println("["+i+"/overflow] 1+0 or 0+1 =1");
                    this.bits.set(i, new Bit(true));
                }
            }
            lastOverflow = nextOverflow;
            bOverflow.value = lastOverflow;
            nextOverflow = false; // Reset
        }
        return this;
    }
}
