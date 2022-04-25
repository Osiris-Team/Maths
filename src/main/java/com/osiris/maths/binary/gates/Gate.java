package com.osiris.maths.binary.gates;

import com.osiris.maths.binary.Bit;
import com.osiris.maths.binary.Bits;

public class Gate {
    public Bits inputs;
    public Bit output;

    public Gate(Bits inputs, Bit output) {
        this.inputs = inputs;
        this.output = output;
    }

    /**
     * Changes {@link #output} according to
     * this gates logic and given {@link #inputs}.
     */
    public void fire() {
        // Implement logic by extending this class and overriding this method.
    }
}
