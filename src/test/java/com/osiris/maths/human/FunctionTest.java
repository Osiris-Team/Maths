package com.osiris.maths.human;

import org.junit.jupiter.api.Test;

class FunctionTest {

    @Test
    void easyFunctionsTest() throws Exception {
        Function f1 = new Function("f(x) = x");
        Function f2 = new Function("f(x) = x + 1");
        Function f3 = new Function("f(x) = 13x * 27y");
        f1.print();
        f2.print();
        f3.print();
    }
}