package com.osiris.maths.binary.gates;

import com.osiris.maths.binary.Bit;
import com.osiris.maths.binary.Bits;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ORTest {

    @Test
    void fire() {
        Bit output = new Bit(0);
        OR a = new OR(Bits.from("011"), output);
        a.fire();
        // OR gate requires one input to be true/1
        assertEquals(true, output.value);
        a.inputs = Bits.from("11").bits;
        a.fire();
        assertEquals(true, output.value);
    }
}