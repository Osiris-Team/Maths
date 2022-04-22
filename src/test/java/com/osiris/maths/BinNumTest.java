package com.osiris.maths;

import com.osiris.maths.binary.BinNum;
import org.junit.jupiter.api.Test;

class BinNumTest {

    @Test
    void from() {
        System.out.println(new BinNum(1));
    }

    @Test
    void testToString() {
    }

    @Test
    void to() {
    }

    @Test
    void add() {
        System.out.println(new BinNum(1).add(new BinNum(5)));
    }
}