package com.osiris.maths.binary;

import java.util.Arrays;

public class Byte {
    public Bit[] bits;
    public Byte() {
        this(null);
    }
    public Byte(Bit[] bits) {
        if(bits !=null && bits.length!=0){
            if(bits.length>8) throw new RuntimeException("Provided values array cannot be bigger than 8!");
            this.bits = bits;
        } else{
            this.bits = new Bit[8];
            Arrays.fill(this.bits, new Bit(false));
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Bit b : bits) {
            if (b.value) s.append(1);
            else s.append(0);
        }
        return s.toString();
    }
}
