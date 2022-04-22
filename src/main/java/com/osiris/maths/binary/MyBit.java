package com.osiris.maths.binary;

public class MyBit {
    public boolean value;
    public MyBit(boolean value) {
        this.value = value;
    }
    public MyBit(int bit) {
        this.value = bit == 1;
    }

    @Override
    public String toString() {
        return value ? "1" : "0";
    }
}
