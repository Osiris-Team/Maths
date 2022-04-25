package com.osiris.maths.binary.gates;

import com.osiris.maths.binary.Bit;
import com.osiris.maths.binary.Bits;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ANDTest {

    @Test
    void fire() {
        Bit output = new Bit(0);
        AND a = new AND(Bits.fromBinaryString("011"), output);
        a.fire();
        System.out.println(a.inputs);
        // AND gate requires all inputs to be true/1
        assertEquals(false, output.value);
        a.inputs = Bits.fromBinaryString("11");
        System.out.println(a.inputs);
        a.fire();
        assertEquals(true, output.value);
    }
}