package com.osiris.maths.binary;

public class Bit {
    public boolean value;

    public Bit(boolean value) {
        this.value = value;
    }

    public Bit(int bit) {
        this.value = bit == 1;
    }

    @Override
    public String toString() {
        return value ? "1" : "0";
    }
}
