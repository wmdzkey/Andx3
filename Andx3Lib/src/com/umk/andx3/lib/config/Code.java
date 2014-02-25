package com.umk.andx3.lib.config;

/**
 * @author Winnid
 * @title 设置类的状态,分类等属性
 * @version:1.0
 * @since：13-12-19
 */
public class Code {

    public static String DESKEY = "Andx3DES4TIEBASHENQI";

    public static State state = new State();
    public static Sex sex = new Sex();
    public static ReturnStateCode returnStateCode = new ReturnStateCode();


    public static class State extends AbstractCode {
        public int Normal = 0;
        public int Delete = 1;

        State() {
            keys = new int[]{0,1};
            values = new String[]{"正常","删除"};
        }
    }

    public static class Sex extends AbstractCode{
        public int Boy = 0;
        public int Girl = 1;
        public int Other = 2;

        Sex() {
            keys = new int[]{0, 1, 2};
            values = new String[]{"男", "女", "其它"};
        }
    }

    public static class ReturnStateCode extends AbstractCode {


        public int ServerUnknown = 999;

        /**意见反馈*/
        public int AdviceSendSuccess = 1000;
        public int AdviceSendFaild = 1001;

        public int FavoriteCancelSuccess = 1010;
        public int FavoriteCancelFaild = 1011;

        ReturnStateCode() {
            keys = new int[10000];
            for (int i = 0; i < 10000; i++) {
                keys[i] = i;
            }
            values = new String[10000];
            for (int i = 0; i < 10000; i++) {
                values[i] = "";
            }
            values[999] = "服务器繁忙";
            values[1000] = "发送意见反馈成功";
            values[1001] = "发送意见反馈失败";

            values[1010] = "取消收藏成功";
            values[1011] = "取消收藏失败";
        }
    }
}
