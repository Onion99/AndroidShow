package com.onion.android.java.core.util;

import com.onion.android.java.core.text.StringFormatter;

public class CharSequenceUtil {
    /**
     * 注意：{@code "null" != null}
     */
    public static final String NULL = "null";

    /**
     * 格式化文本, 占位符替换
     */
    public static String format(CharSequence template, Object ... params){
        if(null == template){
            return NULL;
        }
        if(ArrayUtil.isEmpty(params) || isBlank(template)){
            return template.toString();
        }
        return StringFormatter.format(template.toString(), params);
    }

    /**
     * 字符串是否为空白，包含对空白符的判断
     */
    public static boolean isBlank(CharSequence sequence){
        int length;
        if( (sequence == null) || ((length = sequence.length())) == 0 ){
            return true;
        }
        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if(!CharUtil.isBlankChar(sequence.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串是否为空
     */
    public static boolean isEmpty(CharSequence sequence){
        return sequence == null || sequence.length() == 0;
    }
}
