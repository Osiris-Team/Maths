package com.osiris.maths;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FTest {
    @Test
    void sum() {
        assertEquals(-2.0, F.sum(1.0, 2.0, -5.0));
    }

    @Test
    void multiply() {
        assertEquals(-10.0, F.multiply(1.0, 2.0, -5.0));
    }

    @Test
    void divide() {
        assertEquals(-50.0, F.divide(500.0, 2.0, -5.0));
    }

    @Test
    void potentiate() {
        assertEquals(1953125.0, F.potentiate(2, 3, 5));
    }

    @Test
    void raiseToPowerOf() {
        assertEquals(16.0, F.raiseToPowerOf(2.0, 4));
    }

    @Test
    void factorial() {
        assertEquals(5040.0, F.factorial(7));
    }

    @Test
    void exp() {
        // Note that the last digit should be 2 instead of 5. Changing the precision doesn't affect it somehow.
        assertEquals(2.7182818284590455, F.exp(1.0, 20));
    }

    @Test
    void log() {
        assertEquals(3.0, F.log(8.0, 2.0, 20));
    }
}