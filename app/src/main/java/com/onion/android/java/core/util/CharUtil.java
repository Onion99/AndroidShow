package com.onion.android.java.core.util;

public class CharUtil {

    /**
     * 是否为空白符
     */
    public static boolean isBlankChar(char c){
        return isBlankChar((int) c);
    }

    private static boolean isBlankChar(int c){
        return Character.isWhitespace(c)
                || Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a'
                || c == '\u0000';
    }
}
