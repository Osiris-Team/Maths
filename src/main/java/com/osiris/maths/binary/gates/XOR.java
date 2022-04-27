package com.osiris.maths.binary.gates;

import com.osiris.maths.binary.Bit;
import com.osiris.maths.binary.Bits;

import java.util.Arrays;

public class XOR extends Gate {
    public XOR(Bit output, Bit... inputs) {
        super(Arrays.asList(inputs), output);
    }
    public XOR(Bits inputs, Bit output) {
        super(inputs.bits, output);
    }

    @Override
    public void fire() {
        if(inputs.size() == 0){
            this.output.value = false;
            return;
        }
        // 0 0 = 0
        // 1 0 = 1
        // 0 1 = 1
        // 1 1 = 0
        Bit firstBit = inputs.get(0);
        for (Bit b : inputs) {
            if (b.value != firstBit.value) {
                this.output.value = true;
                return;
            }
        }
        this.output.value = false;
    }
}
