package com.osiris.maths.binary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Infinitely big whole number, made out of bits.
 */
public class BinNum {
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
    public List<MyByte> bytes;

    public BinNum(List<MyByte> bytes) {
        this.bytes = bytes;
    }

    public BinNum(int number) {
        this.bytes = from(number);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (MyByte b8 : bytes) {
            s.append(b8+" ");
        }
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode())+" "+s.toString();
    }

    /**
     * Converts the provided int to {@link #bytes} and returns it.
     */
    public List<MyByte> from(int num){
        return from(num, false);
    }

    /**
     * Converts the provided int to {@link #bytes} and returns it.
     */
    public List<MyByte> from(int num, boolean printSteps){
        List<MyByte> bytes = new ArrayList<>();
        int quotient = num;
        int remainder = 0;
        do {
            MyByte b8 = new MyByte();
            int i = 7;
            for (; i >= 0; i--) {
                if(printSteps) System.out.print(quotient + "/2=");
                remainder = quotient % 2;
                if (remainder == 0) b8.bits[i] = new MyBit(false);
                else b8.bits[i] = new MyBit(true);
                quotient = quotient / 2;
                if(printSteps) System.out.println(quotient + "  r:" + remainder);
                if (quotient == 0) break;
            }
            if (bytes.isEmpty()) bytes.add(b8);
            else bytes.add(0, b8);
        } while (quotient != 0);
        return bytes;
    }

    /**
     * Converts the current number/{@link #bytes}
     * to the new state and returns the new number.
     */
    public BinNum to(State newState){
        List<MyByte> copy = new ArrayList<>();
        Collections.copy(copy, bytes);
        BinNum newNum = new BinNum(copy);
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
    private void toComplement1(BinNum num){
        for (MyByte b8 : num.bytes) {
            for (MyBit b : b8.bits) {
                b.value = !b.value;
            }
        }
    }
    private void toComplement2(BinNum num){
        toComplement1(num);
        add(new BinNum(1));
    }

    /**
     * Adds the provided number to the current one.
     */
    public BinNum add(BinNum num){
        MyBit bOverflow = new MyBit(false);
        List<MyByte> smallerList;
        List<MyByte> biggerList;
        if(bytes.size() < num.bytes.size()){
            smallerList = bytes;
            biggerList = num.bytes;
        } else{
            smallerList = num.bytes;
            biggerList = bytes;
        }
        System.out.println("Addition:");
        System.out.println(smallerList);
        System.out.println("+");
        System.out.println(biggerList);
        System.out.println("=");
        for (int i = smallerList.size()-1; i >= 0 ; i--) {
            MyByte b8_0 = smallerList.get(i);
            MyByte b8_1 = biggerList.get(i);
            MyByte b8Result = new MyByte();
            boolean lastOverflow = false;
            for (int j = 7; j >= 0; j--) {
                boolean b0 = b8_0.bits[j].value;
                boolean b1 = b8_1.bits[j].value;
                boolean nextOverflow = false;
                if(!b0 && !b1){ // 0 0
                    System.out.println("["+j+"] 0+0=0");
                    b8Result.bits[j]=new MyBit(false);
                }
                else if(b0 && b1){ // 1 1
                    System.out.println("["+j+"] 1+1=0 overflow:1");
                    b8Result.bits[j]=new MyBit(false);
                    nextOverflow = true;
                }
                else{ // 0 1 or 1 0
                    System.out.println("["+j+"] 1+0 or 0+1 =1");
                    b8Result.bits[j]=new MyBit(true);
                }
                // Add last overflow to result if existing
                if(lastOverflow){
                    if(b8Result.bits[j].value){ // 1 1
                        System.out.println("["+j+"/overflow] 1+1=0 overflow:1");
                        b8Result.bits[j]=new MyBit(false);
                        nextOverflow = true;
                    }
                    else{ // 0 1 or 1 0
                        System.out.println("["+j+"/overflow] 1+0 or 0+1 =1");
                        b8Result.bits[j]=new MyBit(true);
                    }
                }
                lastOverflow = nextOverflow;
                bOverflow.value = lastOverflow;
            }
            // TODO make it work
            // TODO overflow to next byte
            System.out.println(b8Result);
            smallerList.set(i, b8Result);
        }
        return this;
    }
}
