package com.osiris.maths.human;

public class Utils {
    /**
     * Digits 0-9 as utf8 encoded chars.
     */
    public static int[] utf8Digits = new int[]{
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39};

    /**
     * Returns true if the provided utf8 encoded char is a digit.
     */
    public static boolean isDigit(int utf8Char){
        for (int i = 0; i < 10; i++) {
            if (utf8Digits[i] == utf8Char)
                return true;
        }
        return false;
    }

}
