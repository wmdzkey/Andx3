package com.palm.epalm.base.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author desire
 * @since 2013-08-08 19:49
 */
public class ListArrayUtil {
    public static <T> List<T> newList(T... args) {
        List<T> list = new ArrayList<T>();
        for (T t : args) {
            list.add(t);
        }
        return list;
    }

    public static <T> List<T> add(List<T> list, T... args) {
        for (T t : args) {
            list.add(t);
        }
        return list;
    }
}
