package com.palm.epalm.base.util.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * -日期转换为字符串，如果是用于Json请在字段上标注Jackson专用日期格式转换标注JsonFormat用于转换
 * 如：@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
 *
 * @author desire
 * @see com.fasterxml.jackson.annotation.JsonFormat
 * @since 2013-05-15 16:46
 */
public class DateStringConverter implements StringConverter<Date> {
    private SimpleDateFormat simpleDateFormat;

    public void setDefaultFormat(String format) {
        simpleDateFormat = new SimpleDateFormat(format);
    }

    @Override
    public String convert(Date date) {
        return simpleDateFormat.format(date);
    }
}
