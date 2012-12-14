
package com.java.web.core.lib;

public class StringHelper {

    public static boolean isNullOrEmpty(String s) {
        boolean result = s == null || s.length() == 0;
        return result;
    }

    public static String joinNewLine(String... s) {
        String result = join(
            String.format("%n"),
            s);
        return result;
    }
    public static String join(String delimiter, String... s) {
        StringBuilder buffer = new StringBuilder();
        for (int i=0; i<s.length; i++) {
            if (i>0)
                buffer.append(delimiter);
            buffer.append(s[i]);
        }
        return buffer.toString();
    }

}
