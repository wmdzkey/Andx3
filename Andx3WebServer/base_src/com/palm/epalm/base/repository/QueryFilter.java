package com.palm.epalm.base.repository;

import com.palm.epalm.base.util.converter.DateStringConverter;
import com.palm.epalm.base.util.converter.StringConverter;
import com.palm.epalm.base.util.converter.StringDateConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * -动态查询条件，可以通过该类型动态拼凑查询条件
 *
 * @author desire
 * @since 2013-04-25 18:50
 */
public class QueryFilter {

    private MatchType matchType = MatchType.EQ;
    private String propertyName = null;
    private Object value;
    private PropertyType propertyType;
    private StringDateConverter stringDateConverter = new StringDateConverter();

    /**
     * @param filter 类型匹配类型_属性名[|其他属性名]
     *               如:SLIKE_name|nicName
     *               匹配类型: EQ,NE,LIKE,LT,GT,IN,GE,LE
     *               属性类型: S(String.class),I(Integer.class),L(Long.class),N(Double.class),D(Date.class),B(Boolean.class);
     * @param value
     */
    public QueryFilter(String filter, String value) {
        String[] fs = filter.split("_");
        if (fs.length <= 1) {
            // return;
            throw new RuntimeException("filter error");
        }
        this.matchType = MatchType.valueOf(fs[0].replaceFirst("\\w", ""));
        this.propertyType = PropertyType.valueOf(String.valueOf(fs[0].charAt(0)));
        if (matchType == MatchType.IN || matchType == MatchType.NOTIN) {
            String[] values = value.split(",");
            ArrayList<Object> vl = new ArrayList<Object>();
            for (String v : values) {
                vl.add(convert(v, propertyType));
            }
            this.value = vl;
        } else {
            this.value = convert(value, propertyType);
        }

        propertyName = fs[1];
    }

    private Object convert(String value, PropertyType propertyType) {
        switch (propertyType) {
            case S:
                return value;
            case L:
                return Long.valueOf(value);
            case I:
                return Integer.valueOf(value);
            case N:
                return Double.valueOf(value);
            case D:
                return stringDateConverter.convert(value);
            case B:
                return Boolean.valueOf(value);
        }
        return value;
    }

    /**
     * @param propertyName -字段名称，可以通过'|'符号分隔，表示多个字段 name|nickName
     * @param value
     * @param matchType    LIKE
     */
    public QueryFilter(String propertyName, Object value, MatchType matchType) {
        this.matchType = matchType;
        this.value = value;
        this.propertyName = propertyName;
        this.propertyType = PropertyType.valueOf(value.getClass());
    }

    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @param propertyName -字段名称，可以通过'|'符号分隔，表示多个字段 name|nickName
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    /**
     * -字段类型，为数据库可以接受的基本类型
     *
     * @param propertyType
     */
    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    /**
     * -字段类型
     */
    public static enum PropertyType {
        S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);
        private Class<?> clazz;

        private PropertyType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getValue() {
            return clazz;
        }

        public static PropertyType valueOf(Class<?> type) {
            for (PropertyType p : PropertyType.values()) {
                if (p.getValue() == type) {
                    return p;
                }
            }
            return null;
        }
    }


}
