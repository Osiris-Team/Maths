package com.osiris.maths.binary.gates;

import com.osiris.maths.binary.Bit;
import com.osiris.maths.binary.Bits;

import java.util.Arrays;
import java.util.List;

public class AND extends Gate {
    public AND(Bit output, Bit... inputs) {
        super(Arrays.asList(inputs), output);
    }
    public AND(Bits inputs, Bit output) {
        super(inputs.bits, output);
    }

    @Override
    public void fire() {
        if(inputs.size() == 0) {
            this.output.value = false;
            return;
        }
        for (Bit b : inputs) {
            if (!b.value) {
                this.output.value = false;
                return;
            }
        }
        this.output.value = true;
    }
}
