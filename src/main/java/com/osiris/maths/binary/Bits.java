package com.osiris.maths.binary;

import com.osiris.maths.binary.gates.AND;
import com.osiris.maths.binary.gates.OR;
import com.osiris.maths.binary.gates.XOR;

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
    public State state;
    /**
     * Bit at index 0 in the list is LSB (least significant bit)
     * and the last bit is MSB (most significant bit).
     * In real world it's the other way around. We do it like this however bc of performance.
     * Which means that you would read this list from last index to 0 index, for correct data representation. <br>
     */
    public List<Bit> bits;
    public Bit overflow = new Bit(false);

    /**
     * Use the static {@link #from(int)} methods to create a {@link Bits} object.
     */
    private Bits() {
    }

    private Bits(List<Bit> bits, State state, Bit overflow) {
        if(bits == null) this.bits = new ArrayList<>();
        else this.bits = bits;

        if(state == null) this.state = State.UNMODIFIED;
        else this.state = state;

        if(overflow==null) this.overflow = new Bit(false);
        else this.overflow = overflow;
    }

    /**
     * Converts the provided string to bits. <br>
     * Expects an string looking something like this: <br>
     * 0101010000111010101 etc... <br>
     * Expects bit order MSB -> LSB.
     */
    public static Bits from(String bits) {
        String s = bits;
        List<Bit> actualBits = new ArrayList<>();
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '1') actualBits.add(new Bit(true));
            else actualBits.add(new Bit(false));
        }
        return new Bits(actualBits, null, null);
    }


    /**
     * Converts the provided decimal number to {@link #bits} and returns it.
     * Note that negative numbers get converted to 2s complement and
     * the {@link #state} gets set accordingly.
     */
    public static Bits from(int decimalNum) {
        return from(decimalNum, false);
    }

    /**
     * Converts the provided decimal number to {@link #bits} and returns it.
     * Note that negative numbers get converted to 2s complement and
     * the {@link #state} gets set accordingly.
     */
    public static Bits from(int decimalNum, boolean printSteps) {
        if(printSteps) System.out.println("From "+decimalNum+" decimal to binary:");
        List<Bit> bits = new ArrayList<>();
        int quotient = decimalNum;
        if((""+decimalNum).contains("-")) quotient = Integer.parseInt((""+decimalNum).replace("-", ""));
        int remainder = 0;
        do {
            if (printSteps) System.out.print(quotient + "/2=");
            remainder = quotient % 2;
            if (remainder == 0) bits.add(new Bit(false));
            else bits.add(new Bit(true));
            quotient = quotient / 2;
            if (printSteps) System.out.println(quotient + "  r:" + remainder);
        } while (quotient != 0);
        Bits b = new Bits(bits, null, null);
        if((""+decimalNum).contains("-")) b.to(State.COMPLEMENT_2, printSteps);
        return b;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int i = 1;
        for (int j = bits.size() - 1; j >= 0; j--) {
            s.append(bits.get(j));
            if (i == 8) {
                s.append(" ");
                i = 0;
            }
            i++;
        }
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " Dec: " +toDecimal() +" "+ state +" MSB->LSB (overflow:"+overflow+") " + s;
    }

    public String toStringLSBtoMSB() {
        StringBuilder s = new StringBuilder();
        int i = 1;
        for (Bit b : bits) {
            s.append(b);
            if (i == 8) {
                s.append(" ");
                i = 0;
            }
            i++;
        }
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " Dec: "+toDecimal() +" "+ state + " LSB->MSB " + s+" (overflow:"+overflow+")";
    }

    public void append(Bit... bits) {
        Collections.addAll(this.bits, bits);
    }

    public void append(Byte... bytes) {
        for (Byte b8 :
                bytes) {
            Collections.addAll(this.bits, b8.bits);
        }
    }

    public int toDecimal(){
        return toDecimal(false);
    }

    public int toDecimal(boolean printSteps){
        int lastMultiplicator = 1;
        int result = 0;
        if(state==State.UNMODIFIED){
            for (Bit b : bits) {
                if(b.value) result += lastMultiplicator;
                lastMultiplicator = lastMultiplicator * 2;
            }
        } else if(state == State.COMPLEMENT_1){
            List<Bit> temp = new ArrayList<>();
            Collections.copy(bits, temp);
            Bits bitsCopy = new Bits(temp, this.state, this.overflow);
            return Integer.parseInt("-"+bitsCopy.to(State.UNMODIFIED, printSteps).toDecimal(printSteps));
        } else if(state == State.COMPLEMENT_2){
            if(this.overflow.value){ // Since there is an overflow we can go through all bits and
                // substract the last overflow bit manually
                for (Bit b: bits) {
                    if(b.value) result += lastMultiplicator;
                    lastMultiplicator = lastMultiplicator * 2;
                }
                result -= lastMultiplicator;
            } else{
                for (int i = 0; i < bits.size() - 1; i++) { // Exclude last value
                    if(bits.get(i).value) result += lastMultiplicator;
                    lastMultiplicator = lastMultiplicator * 2;
                }
                if(bits.get(bits.size()-1).value) result -= lastMultiplicator;
            }
        }
        return result;
    }

    public Bits to(State newState){
        return to(newState, false);
    }

    /**
     * Converts the current number/{@link #bits}
     * to the new state and returns the new number.
     * @return this object for chaining.
     */
    public Bits to(State newState, boolean printSteps) {
        if(printSteps) System.out.println("Converting from "+this.state+" to "+newState+".");
        switch (newState) {
            case COMPLEMENT_1:
                if (state == State.UNMODIFIED) {
                    toComplement1(this, printSteps);
                } else if (state == State.COMPLEMENT_1) {
                } else if (state == State.COMPLEMENT_2) {
                    revertComplement2(this);
                }
                this.state = State.COMPLEMENT_1;
                break;
            case COMPLEMENT_2:
                if (state == State.UNMODIFIED) {
                    toComplement1(this, printSteps);
                    toComplement2(this, printSteps);
                } else if (state == State.COMPLEMENT_1) {
                    toComplement2(this, printSteps);
                } else if (state == State.COMPLEMENT_2) {}
                this.state = State.COMPLEMENT_2;
                break;
            default: // case UNMODIFIED
                if (state == State.UNMODIFIED) {
                } else if (state == State.COMPLEMENT_1) {
                    revertComplement1(this);
                } else if (state == State.COMPLEMENT_2) {
                    revertComplement2(this);
                    revertComplement1(this);
                }
                this.state = State.UNMODIFIED;
                break;
        }
        return this;
    }

    private void toComplement1(Bits num, boolean printSteps) {
        for (Bit b : num.bits) {
            b.value = !b.value;
        }
        if(printSteps) System.out.println("Inverted bits (1s complement): "+this);
    }
    private void revertComplement1(Bits num){
        for (Bit b : num.bits) {
            b.value = !b.value;
        }
    }

    private void toComplement2(Bits num, boolean printSteps) {
        num.add(Bits.from(1));
        if(!num.bits.get(num.bits.size()-1).value)
            num.bits.add(new Bit(true)); // If the MSB is = 0 we add a 1, since all 2s complements need to start with 1
        // otherwise, it displays the wrong value
        if(printSteps) System.out.println("Added 1 (2s complement): "+this);
    }
    private void revertComplement2(Bits num){
        num.add(Bits.from(-1));
    }

    public Bits add(Bits num){
        return add(num, false);
    }

    /**
     * Adds the provided number to the current one.
     * @return the current object for chaining.
     */
    public Bits add(Bits num, boolean printSteps) {
        // Fill missing bits of smaller list
        if (this.bits.size() != num.bits.size()) {
            int missingBits = 0;
            if (bits.size() < num.bits.size()) {
                missingBits = num.bits.size() - bits.size();
                for (int i = 0; i < missingBits; i++) {
                    if(state == State.COMPLEMENT_1 || state == State.COMPLEMENT_2) bits.add(new Bit(true));
                    else bits.add(new Bit(false));
                }
            } else {
                missingBits = bits.size() - num.bits.size();
                for (int i = 0; i < missingBits; i++) {
                    if(state == State.COMPLEMENT_1 || state == State.COMPLEMENT_2) num.bits.add(new Bit(true));
                    else num.bits.add(new Bit(false));
                }
            }
        }
        if(printSteps){
            System.out.println("Addition:");
            System.out.println(num);
            System.out.println("+");
            System.out.println(this);
            System.out.println("=");
        }
        boolean lastOverflow = false;
        boolean nextOverflow = false;
        for (int i = 0; i < this.bits.size(); i++) {
            boolean b0 = this.bits.get(i).value;
            boolean b1 = num.bits.get(i).value;
            if (!b0 && !b1) { // 0 0
                if(printSteps) System.out.println("[" + i + "] 0+0=0");
                this.bits.set(i, new Bit(false));
            } else if (b0 && b1) { // 1 1
                if(printSteps) System.out.println("[" + i + "] 1+1=0 overflow:1");
                this.bits.set(i, new Bit(false));
                nextOverflow = true;
            } else { // 0 1 or 1 0
                if(printSteps) System.out.println("[" + i + "] 1+0 or 0+1 =1");
                this.bits.set(i, new Bit(true));
            }
            // Add last overflow to result if existing
            if (lastOverflow) {
                if (this.bits.get(i).value) { // 1 1
                    if(printSteps) System.out.println("[" + i + "/overflow] 1+1=0 overflow:1");
                    this.bits.set(i, new Bit(false));
                    nextOverflow = true;
                } else { // 0 1 or 1 0
                    if(printSteps) System.out.println("[" + i + "/overflow] 1+0 or 0+1 =1");
                    this.bits.set(i, new Bit(true));
                }
            }
            lastOverflow = nextOverflow;
            this.overflow.value = lastOverflow;
            nextOverflow = false; // Reset
        }
        if(printSteps) System.out.println("Result: "+this);
        return this;
    }

    /**
     * Performs a logical AND operation for each individual bit.
     */
    public Bits and(Bits num) {
        // Fill missing bits of smaller list
        if (this.bits.size() != num.bits.size()) {
            int missingBits = 0;
            if (bits.size() < num.bits.size()) {
                missingBits = num.bits.size() - bits.size();
                for (int i = 0; i < missingBits; i++) {
                    if(state == State.COMPLEMENT_1 || state == State.COMPLEMENT_2) bits.add(new Bit(true));
                    else bits.add(new Bit(false));
                }
            } else {
                missingBits = bits.size() - num.bits.size();
                for (int i = 0; i < missingBits; i++) {
                    if(state == State.COMPLEMENT_1 || state == State.COMPLEMENT_2) num.bits.add(new Bit(true));
                    else num.bits.add(new Bit(false));
                }
            }
        }
        List<Bit> bits = new ArrayList<>();
        for (int i = 0; i < this.bits.size(); i++) {
            Bit b1 = this.bits.get(i);
            Bit b2 = num.bits.get(i);
            Bit bOutput = new Bit(false);
            new AND(bOutput, b1, b2).fire();
            bits.add(bOutput);
        }
        this.bits = bits;
        return this;
    }

    public Bits or(Bits num) {
        // Fill missing bits of smaller list
        if (this.bits.size() != num.bits.size()) {
            int missingBits = 0;
            if (bits.size() < num.bits.size()) {
                missingBits = num.bits.size() - bits.size();
                for (int i = 0; i < missingBits; i++) {
                    if(state == State.COMPLEMENT_1 || state == State.COMPLEMENT_2) bits.add(new Bit(true));
                    else bits.add(new Bit(false));
                }
            } else {
                missingBits = bits.size() - num.bits.size();
                for (int i = 0; i < missingBits; i++) {
                    if(state == State.COMPLEMENT_1 || state == State.COMPLEMENT_2) num.bits.add(new Bit(true));
                    else num.bits.add(new Bit(false));
                }
            }
        }
        List<Bit> bits = new ArrayList<>();
        for (int i = 0; i < this.bits.size(); i++) {
            Bit b1 = this.bits.get(i);
            Bit b2 = num.bits.get(i);
            Bit bOutput = new Bit(false);
            new OR(bOutput, b1, b2).fire();
            bits.add(bOutput);
        }
        this.bits = bits;
        return this;
    }

    public Bits xor(Bits num) {
        // Fill missing bits of smaller list
        if (this.bits.size() != num.bits.size()) {
            int missingBits = 0;
            if (bits.size() < num.bits.size()) {
                missingBits = num.bits.size() - bits.size();
                for (int i = 0; i < missingBits; i++) {
                    if(state == State.COMPLEMENT_1 || state == State.COMPLEMENT_2) bits.add(new Bit(true));
                    else bits.add(new Bit(false));
                }
            } else {
                missingBits = bits.size() - num.bits.size();
                for (int i = 0; i < missingBits; i++) {
                    if(state == State.COMPLEMENT_1 || state == State.COMPLEMENT_2) num.bits.add(new Bit(true));
                    else num.bits.add(new Bit(false));
                }
            }
        }
        List<Bit> bits = new ArrayList<>();
        for (int i = 0; i < this.bits.size(); i++) {
            Bit b1 = this.bits.get(i);
            Bit b2 = num.bits.get(i);
            Bit bOutput = new Bit(false);
            new XOR(bOutput, b1, b2).fire();
            bits.add(bOutput);
        }
        this.bits = bits;
        return this;
    }

    public enum State {
        /**
         * The bytes have not been modified/complemented.
         */
        UNMODIFIED,
        /**
         * The bytes have been modified to 1s complement,
         * which means that each bit was inverted. <br>
         * 1s complement is a binary format to represent negative numbers.
         */
        COMPLEMENT_1,
        /**
         * The bytes have been modified to 2s complement,
         * which means that each bit was inverted (1s complement) and
         * 1 was added. <br>
         * 2s complement is a binary format to represent negative numbers.
         */
        COMPLEMENT_2
    }
}
