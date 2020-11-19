package pl.dzokv.auth.utils;

import java.util.regex.*;

public class Util
{
    public static boolean isInteger(final String string) {
        return Pattern.matches("-?[0-9]+", string.subSequence(0, string.length()));
    }
    
    public static double round(final double value, final int decimals) {
        final double p = Math.pow(10.0, decimals);
        return Math.round(value * p) / p;
    }
    
    public static boolean isAlphaNumeric(final String s) {
        return s.matches("^[a-zA-Z0-9_]*$");
    }
}
