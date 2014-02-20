package com.palm.epalm.base.util.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * -把其他类型转换为String
 *
 * @author desire
 * @since 2013-05-03 10:01
 */
public interface StringConverter<T> extends Converter<T, String> {
    String convert(T target);
}
