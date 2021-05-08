package com.onion.android.java.core.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class StringUtil extends CharSequenceUtil implements StringPool {

    /**
     * 对象转字符串
     */
    public static String object2String(Object object, Charset charset){
        if(null == object) return null;

        if(object instanceof String){
            return (String) object;
        } else if(object instanceof byte[]){
            return object2String((byte[]) object, charset);
        } else if(object instanceof Byte[]){
            return object2String((Byte[]) object, charset);
        } else if(object instanceof ByteBuffer){
            return object2String( (ByteBuffer) object, charset);
        } else if(ArrayUtil.isArray(object)){
            return ArrayUtil.toString(object);
        }
        return object.toString();
    }

    /**
     * 解码字节码
     */
    private static String object2String(Byte[] data, Charset charset) {
        if (data == null) return null;

        byte[] bytes = new byte[data.length];
        Byte dataByte;
        for (int i = 0; i < data.length; i++) {
            dataByte = data[i];
            bytes[i] = (null == dataByte) ? -1 : dataByte;
        }
        return object2String(bytes,charset);
    }

    /**
     * 解码字节码
     */
    private static String object2String(byte[] data, Charset charset) {
        if (data == null) return null;
        if (null == charset) {
            return new String(data);
        }
        return new String(data, charset);
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     */
    private static String object2String(ByteBuffer data, Charset charset) {
        if (null == charset) {
            charset = Charset.defaultCharset();
        }
        return charset.decode(data).toString();
    }

}
