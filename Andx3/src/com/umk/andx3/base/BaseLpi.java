package com.umk.andx3.base;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-16
 */
public abstract class BaseLpi<T> {
    protected DbUtils db;
    protected Class<T> entityClass;

}
