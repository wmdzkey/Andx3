package com.umk.andx3.lib.config;

/**
 * @author Winnid
 * @title 参数状态类
 * @version:1.0
 * @since：14-1-13
 */
public abstract class AbstractCode {

    protected static int[] keys;
    protected static String[] values;

    public static int getKey(String value) {
        for (int i = 0; i < values.length; i++) {
            values[i].equals(value);
            return i;
        }
        return -1;
    }

    public static String getValue(int key) {
        return values[key];
    }
}
