package com.osiris.maths;

import com.osiris.maths.binary.Bits;
import org.junit.jupiter.api.Test;

class BitsTest {

    @Test
    void from() {
        System.out.println(new Bits(1));
    }

    @Test
    void testToString() {
    }

    @Test
    void to() {
    }

    @Test
    void add() {
        Bits before = new Bits(1);
        System.out.println(before);
        Bits after = before.add(new Bits(5));
        System.out.println(after);
    }
}