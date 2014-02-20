package com.palm.epalm.base.util.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * -
 *
 * @author desire
 * @since 2013-05-15 16:48
 */
public class StringDateConverter implements Converter<String, Date> {
    private static final String FORMAT_0 = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT_1 = "yyyy-MM-dd";
    private static final String FORMAT_2 = "HH:mm:ss";
    private static final String FORMAT_3 = "yyyy-MM-dd HH:mm";
    private static final String FORMAT_4 = "HH:mm";

    @Override
    public Date convert(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        SimpleDateFormat simple = null;
        switch (date.trim().length()) {
            case 19:// 日期+时间
                simple = new SimpleDateFormat(FORMAT_0);
                break;
            case 10:// 仅日期
                simple = new SimpleDateFormat(FORMAT_1);
                break;
            case 8:// 仅时间
                simple = new SimpleDateFormat(FORMAT_2);
                break;
            case 16:
                simple = new SimpleDateFormat(FORMAT_3);
                break;
            case 5:
                simple = new SimpleDateFormat(FORMAT_4);
                break;
            default:
                if (StringUtils.isNumeric(date)) {  //秒表示法
                    return new Date(Long.valueOf(date));
                }
                return null;
        }
        try {
            return simple.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
