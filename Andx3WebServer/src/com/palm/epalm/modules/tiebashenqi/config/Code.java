package com.palm.epalm.modules.tiebashenqi.config;

/**
 * @author Winnid
 * @title 设置类的状态,分类等属性
 * @version:1.0
 * @since：13-12-19
 */
public abstract class Code {

    private static int[] keys;
    private static String[] values;

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

    public static class State extends Code{
        public static int Normal = 0;
        public static int Delete = 1;

        static {
            keys = new int[]{0,1};
            values = new String[]{"正常","删除"};
        }
    }
}
