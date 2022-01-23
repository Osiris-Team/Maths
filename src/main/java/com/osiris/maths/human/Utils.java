package com.osiris.maths.human;

public class Utils {
    /**
     * Digits 0-9 as utf8 encoded chars.
     */
    public static char[] utf8Digits = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * Returns true if the provided utf8 encoded char is a digit.
     */
    public static boolean isDigit(char utf8Char){
        for (int i = 0; i < 10; i++) {
            if (utf8Digits[i] == utf8Char)
                return true;
        }
        return false;
    }

}
