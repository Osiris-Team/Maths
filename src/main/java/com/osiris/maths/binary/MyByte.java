package com.osiris.maths.binary;

import java.util.Arrays;

public class MyByte {
    public MyBit[] bits;
    public MyByte() {
        this(null);
    }
    public MyByte(MyBit[] bits) {
        if(bits !=null && bits.length!=0){
            if(bits.length>8) throw new RuntimeException("Provided values array cannot be bigger than 8!");
            this.bits = bits;
        } else{
            this.bits = new MyBit[8];
            Arrays.fill(this.bits, new MyBit(false));
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (MyBit b : bits) {
            if (b.value) s.append(1);
            else s.append(0);
        }
        return s.toString();
    }
}
