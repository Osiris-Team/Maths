package com.osiris.maths;

/**
 * Collection of "useful" functions.
 */
public class F {

    public static Double sum(Double... numbers){
        double result = 0.0;
        for (Double num : numbers) {
            result = result + num;
        }
        return result;
    }

    public static Obj3 sum(Obj3... objects){
        double resultX = 0.0;
        double resultY = 0.0;
        double resultZ = 0.0;
        for (Obj3 obj : objects) {
            resultX = resultX + obj.x;
            resultY = resultY + obj.y;
            resultZ = resultZ + obj.z;
        }
        return new Obj3(resultX, resultY, resultZ);
    }

    public static Double subtract(Double... numbers){
        double result = 0.0;
        for (Double num : numbers) {
            result = result - num;
        }
        return result;
    }

    public static Obj3 subtract(Obj3... objects){
        double resultX = 0.0;
        double resultY = 0.0;
        double resultZ = 0.0;
        for (Obj3 obj : objects) {
            resultX = resultX - obj.x;
            resultY = resultY - obj.y;
            resultZ = resultZ - obj.z;
        }
        return new Obj3(resultX, resultY, resultZ);
    }

    public static Double multiply(Double... numbers){
        double result = 1.0;
        for (Double num : numbers) {
            result = result * num;
        }
        return result;
    }

    public static Obj3 multiply(Obj3... objects){
        double resultX = 1.0;
        double resultY = 1.0;
        double resultZ = 1.0;
        for (Obj3 obj : objects) {
            resultX = resultX * obj.x;
            resultY = resultY * obj.y;
            resultZ = resultZ * obj.z;
        }
        return new Obj3(resultX, resultY, resultZ);
    }

    public static Double divide(Double... numbers){
        double result = numbers[0] * numbers[0];
        for (Double num : numbers) {
            result = result / num;
        }
        return result;
    }

    public static Obj3 divide(Obj3... objects){
        double resultX = objects[0].x * objects[0].x;
        double resultY = objects[0].y * objects[0].y;
        double resultZ = objects[0].z * objects[0].z;
        for (Obj3 obj : objects) {
            resultX = resultX / obj.x;
            resultY = resultY / obj.y;
            resultZ = resultZ / obj.z;
        }
        return new Obj3(resultX, resultY, resultZ);
    }

    public static Double raiseToPowerOf(Double number, int power){
        Double[] numbers = new Double[power];
        for (int i = 0; i < power; i++) {
            numbers[i] = number;
        }
        return multiply(numbers);
    }

    /**
     * Allows to raise something to the power of 2.89 for example. <br>
     * Pass over 2 for power and 0.89 for powerDecimal, to achieve that.
     */
    public static Double raiseToPowerOf(Double number, int power, Double powerDecimal){
        Double[] numbers = new Double[power];
        for (int i = 0; i < power; i++) {
            numbers[i] = number;
        }
        return multiply(numbers) + (number * powerDecimal);
    }

    /**
     * 2^3 = 8; radix(8,3) returns 2 (the base of the exponent). <br>
     */
    public static Double root(Double number, int power){
        // With Newtons' method
        double xPre = Math.random() % 10; // initially guessing a random number between 0-9
        double eps = 0.001; // smaller eps, denotes more accuracy
        double delX = Integer.MAX_VALUE; // initializing difference between two roots by INT_MAX

        // xK denotes current value of x
        double xK = 0.0;

        // loop until we reach desired accuracy
        while (delX > eps)
        {
            // calculating current value from previous
            // value by newton's method
            xK = ((power - 1.0) * xPre +
                    number / Math.pow(xPre, power - 1)) / (double)power;
            delX = Math.abs(xK - xPre);
            xPre = xK;
        }

        return xK;
    }

    /**
     * Example with input: [2, 3, 5] <br>
     * 2^1 = 2 <br>
     * 3^2 = 9 <br>
     * 5^9 = 1953125 <br>
     */
    public static Double potentiate(int... numbers){ // TODO numbers as double, like 2.8 or 15.298
        Double result = 1.0;
        for (int num : numbers) {
            result = raiseToPowerOf((double) num, result.intValue());
        }
        return result;
    }

    public static Double factorial(int number){ // TODO number as double, like 2.8 or 15.298
        Double[] numbers = new Double[number];
        double num = 0.0;
        for (int i = 0; i < number; i++) {
            numbers[i] = (num = num + 1.0);
        }
        return multiply(numbers);
    }

    /**
     * Function: Exponential <br>
     *
     * Returns the y value for the provided x value. <br>
     * The underlying function is the natural exponential function. <br>
     * @param precision The amount of loops. The higher this number, the more precise the returned value will be.
     */
    public static Double exp(Double x, int precision){ // TODO number as double, like 2.8 or 15.298
        // TODO We could retrieve the value by doing e^x. Idk if that's more precise and performant though.
        Double[] numbers = new Double[precision];
        numbers[0] = 1.0;
        for (int i = 1; i < precision; i++) {
            numbers[i] = divide(raiseToPowerOf(x, i), factorial(i));
        }
        return sum(numbers);
    }

    /**
     * Function: Logarithm <br>
     * Useful if the unknown number is an exponent. <br>
     * Example: 2^?=8 <br>
     * To find ? simply enter log(8, 2, 20); <br>
     * @param precision The amount of loops. The higher this number, the more precise the returned value will be.
     * @return The y value.
     */
    public static Double log(Double x, Double base, int precision){
        double result = 1.0;
        double lastResult = 0.0;
        boolean isLastBigger = false;
        boolean isBigger = true;
        for (int i = 0; i < precision; i++) {
            System.out.println(result);
            isBigger = raiseToPowerOf(base, (int) result) > x;
            if(isBigger){
                if (!isLastBigger) result = result + ((result - lastResult) / 2);
                else {
                    result = result / 2;
                }
            } else{
                if (isLastBigger) result = result + ((lastResult - result) / 2);
                else {
                    result = result * 2;
                }

            }
            lastResult = result;
            isLastBigger = isBigger;
        }
        return result;
    }

    /**
     * Function: Natural logarithm <br>
     * Is the same as {@link #log(Double, Double, int)} with {@link Constant#e} as base.
     * @return The y value.
     */
    public static Double ln(Double x){
        return log(x, Constant.e, 20);
    }



}
