package com.palm.epalm.base.repository;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * -系统默认实现数据库操作接口，在Spring  JpaRepository基础上添加部分功能
 *
 * @param <T>
 * @author desirelong
 * @version 1.0
 */
public interface DefaultRepository<T> extends JpaRepository<T, Long> {
    Session getSession();

    public Specification<T> specification(final List<QueryFilter> filters);

    @SuppressWarnings("hiding")
    /**
     * 根据动态hql查询，在无需拼接查询时，不推荐使用该方法
     */
    <T> List<T> findByHql(String hql, Object... values);

    @SuppressWarnings("hiding")
    /**
     * @see findByHql
     */
    <T> T findOneByHql(String hql, Object... values);

    @Modifying
    /**
     * @see findByHql
     */
    int executeHql(String sql, Object... values);

    @Modifying
    /**
     * 执行动态slq,在语句确定（无需动态拼接语句）情况下不推荐使用该方法
     */
    public int executeSql(String sql, Object... values);

    /**
     * 执行动态slq,在语句确定（无需动态拼接语句）情况下不推荐使用该方法
     */
    public <T> List<T> findBySql(String sql, Object... values);

    /**
     * 执行动态slq,在语句确定（无需动态拼接语句）情况下不推荐使用该方法
     */
    public <T> T findOneBySql(String sql, Object... values);

    /**
     * -根据过滤添加查找出唯一符合条件的实体
     *
     * @param filters 过滤条件
     * @return
     */
    public T findOne(List<QueryFilter> filters);

    public List<T> findAll(List<QueryFilter> filters);

    public Page<T> findAll(List<QueryFilter> filters, Pageable page);

    public List<T> findAll(List<QueryFilter> filters, Sort sort);

    public List<T> findAll(Specification<T> spec, Limit limit);

    public List<T> findAll(Specification<T> spec, Sort sort, Limit limit);

    public List<T> findAll(List<QueryFilter> filters, Sort sort, Limit limit);

    public List<T> findAll(List<QueryFilter> filters, Limit limit);
}
