package com.umk.tiebashenqi.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-18
 */
public class TempUtil {

    public static String convertChineseUrl(String url) {
        char[] tp = url.toCharArray();
        String now = "";
        for (char ch : tp) {
            if (ch >= 0x4E00 && ch <= 0x9FA5) {
                try {
                    now += URLEncoder.encode(ch + "", "gbk");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                now += ch;
            }
        }
        return now;
    }
}
