package com.osiris.maths.binary.gates;

import com.osiris.maths.binary.Bit;
import com.osiris.maths.binary.Bits;

import java.util.Arrays;

public class OR extends Gate {
    public OR(Bit output, Bit... inputs) {
        super(Arrays.asList(inputs), output);
    }
    public OR(Bits inputs, Bit output) {
        super(inputs.bits, output);
    }

    @Override
    public void fire() {
        for (Bit b : inputs) {
            if (b.value) {
                this.output.value = true;
                return;
            }
        }
        this.output.value = false;
    }
}
