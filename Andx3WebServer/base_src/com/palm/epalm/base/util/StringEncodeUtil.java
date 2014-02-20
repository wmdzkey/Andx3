package com.palm.epalm.base.util;

import java.io.UnsupportedEncodingException;

/**
 * -String编码转换类
 *
 * @author desire
 * @since 2013-05-02 18:15
 */
public class StringEncodeUtil {
    public static String code_8859_1_to_UTF_8(String source) {
        try {
            return new String(source.getBytes("8859_1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return source;
        }
    }
}
