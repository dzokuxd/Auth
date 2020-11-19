package pl.dzokv.auth.utils;

import java.util.*;

public class AuthUtil
{
    private static Random random;
    private static char[] CAPTCHA_CHARS;
    
    static {
        AuthUtil.random = new Random(new Random().nextLong());
        AuthUtil.CAPTCHA_CHARS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'o', 'p', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
    }
    
    public static String generateCaptcha() {
        return randomChars(5);
    }
    
    public static String randomChars(final int length) {
        final int size = AuthUtil.CAPTCHA_CHARS.length;
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            builder.append(AuthUtil.CAPTCHA_CHARS[AuthUtil.random.nextInt(size)]);
        }
        return builder.toString();
    }
}
