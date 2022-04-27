package com.osiris.maths;

import com.osiris.maths.binary.Bits;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BitsTest {

    @Test
    void from() {
        System.out.println(Bits.from(1));
    }

    @Test
    void testToString() {
        Bits.from(-125);
    }

    @Test
    void to() {
    }

    @Test
    void add() {
        Bits result = Bits.from(1).add(Bits.from(5));

    }

    @Test
    void testToString1() {
    }

    @Test
    void toStringLSBtoMSB() {
    }

    @Test
    void append() {
    }

    @Test
    void testAppend() {
    }

    @Test
    void toDecimal() {
        assertEquals(5, Bits.from(5).toDecimal());
        Bits b = Bits.from(-5);
        System.out.println(b);
        assertEquals(-5, b.toDecimal());
    }
}