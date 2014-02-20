package com.umk.andx3.lib.config;

/**
 * @author Winnid
 * @title 设置类的状态,分类等属性
 * @version:1.0
 * @since：13-12-19
 */
public class Code {

    public static String DESKEY = "Andx3DES4TIEBASHENQI";

    public static class State extends AbstractCode {
        public static int Normal = 0;
        public static int Delete = 1;

        static {
            keys = new int[]{0,1};
            values = new String[]{"正常","删除"};
        }
    }

    public static class Sex extends AbstractCode{
        public static int Boy = 0;
        public static int Girl = 1;
        public static int Other = 2;

        static {
            keys = new int[]{0, 1, 2};
            values = new String[]{"男", "女", "其它"};
        }
    }

}
