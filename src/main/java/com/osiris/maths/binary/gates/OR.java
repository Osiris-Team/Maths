package com.osiris.maths.binary.gates;

import com.osiris.maths.binary.Bit;
import com.osiris.maths.binary.Bits;

public class OR extends Gate{
    public OR(Bits inputs, Bit output) {
        super(inputs, output);
    }

    @Override
    public void fire() {
        for (Bit b : inputs.bits) {
            if(b.value){
                this.output.value = true;
                return;
            }
        }
        this.output.value = false;
    }
}
