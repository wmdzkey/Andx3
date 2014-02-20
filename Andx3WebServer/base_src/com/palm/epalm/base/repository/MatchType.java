package com.palm.epalm.base.repository;

/**
 * -查询匹配类型，配合QueryFilter使用
 *
 * @author desire
 * @see QueryFilter
 * EQ("="), NE("<>"), LIKE("like"), GT(">"), LT("<"), GE(">="), LE("<="), IN("in"), NOTIN("not in");
 * @since 2013-04-25 17:24
 */
public enum MatchType {
    EQ, NE, LIKE, LT, GT, IN, GE, LE, NOTIN
}
