package com.osiris.maths.binary.gates;

import com.osiris.maths.binary.Bit;
import com.osiris.maths.binary.Bits;

public class AND extends Gate {
    public AND(Bits inputs, Bit output) {
        super(inputs, output);
    }

    @Override
    public void fire() {
        for (Bit b : inputs.bits) {
            if (!b.value) {
                this.output.value = false;
                return;
            }
        }
        this.output.value = true;
    }
}