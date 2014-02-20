package com.palm.epalm.base.repository;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

@NoRepositoryBean
public class DefaultRepositoryImpl<T> extends SimpleJpaRepository<T, Long> implements
        DefaultRepository<T> {

    private HibernateEntityManager entityManager;
    private Class<T> domainType;

    public DefaultRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        entityManager = ((HibernateEntityManager) em);
        domainType = domainClass;
        //this.sessionFactory = ((HibernateEntityManager)em).ge
    }

    public DefaultRepositoryImpl(final JpaEntityInformation<T, ?> entityInformation, final EntityManager em) {
        super(entityInformation, em);
        entityManager = ((HibernateEntityManager) em);
        domainType = entityInformation.getJavaType();
    }

    public Session getSession() {
        return entityManager.getSession();
    }

    public Query createQuery(final String queryString, final Object... values) {
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    @Override
    public int executeHql(String hql, Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    @Override
    public <T> List<T> findByHql(String hql, Object... values) {
        return createQuery(hql, values).list();
    }

    @Override
    public <T> T findOneByHql(String hql, Object... values) {
        return (T) createQuery(hql, values).uniqueResult();
    }

    protected SQLQuery createSQLQuery(String sql, Object... values) {
        SQLQuery query = getSession().createSQLQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    public int executeSql(String sql, Object... values) {
        return createSQLQuery(sql, values).executeUpdate();
    }

    @Override
    public <T> List<T> findBySql(String sql, Object... values) {
        return createSQLQuery(sql, values).list();
    }

    @Override
    public <T> T findOneBySql(String sql, Object... values) {
        return (T) createSQLQuery(sql, values).uniqueResult();
    }

    public T findOne(List<QueryFilter> filters) {
        return super.findOne(buildSpecification(filters));
    }

    ;

    public List<T> findAll(List<QueryFilter> filters) {
        return super.findAll(buildSpecification(filters));
    }

    public Page<T> findAll(List<QueryFilter> filters, Pageable page) {
        return super.findAll(buildSpecification(filters), page);
    }

    private Predicate addFilter(Expression ep, Object value, MatchType matchType, QueryFilter.PropertyType propertyType, CriteriaBuilder cb) {
        switch (matchType) {
            case EQ:
                return cb.equal(ep, value);
            case NE:
                return cb.notEqual(ep, value);
            case LIKE:
                return cb.like(ep, "%" + value + "%");
            case LT:
                switch (propertyType) {
                    case I:
                        return cb.lt((Expression<Integer>) ep, (Integer) value);
                    case L:
                        return cb.lt((Expression<Long>) ep, (Long) value);
                    case N:
                        return cb.lt((Expression<Double>) ep, (Double) value);
                    case D:
                        return cb.lessThan((Expression<Date>) ep, (Date) value);
                    case B:
                        return cb.lessThan((Expression<Boolean>) ep, (Boolean) value);
                    case S:
                        return cb.lessThan((Expression<String>) ep, (String) value);
                }
            case GT:
                switch (propertyType) {
                    case I:
                        return cb.gt((Expression<Integer>) ep, (Integer) value);
                    case L:
                        return cb.gt((Expression<Long>) ep, (Long) value);
                    case N:
                        return cb.gt((Expression<Double>) ep, (Double) value);
                    case D:
                        return cb.greaterThan((Expression<Date>) ep, (Date) value);
                    case B:
                        return cb.greaterThan((Expression<Boolean>) ep, (Boolean) value);
                    case S:
                        return cb.greaterThan((Expression<String>) ep, (String) value);
                }
            case LE:
                switch (propertyType) {
                    case I:
                        return cb.le((Expression<Integer>) ep, (Integer) value);
                    case L:
                        return cb.le((Expression<Long>) ep, (Long) value);
                    case N:
                        return cb.le((Expression<Double>) ep, (Double) value);
                    case D:
                        return cb.lessThanOrEqualTo((Expression<Date>) ep, (Date) value);
                    case B:
                        return cb.lessThanOrEqualTo((Expression<Boolean>) ep, (Boolean) value);
                    case S:
                        return cb.lessThanOrEqualTo((Expression<String>) ep, (String) value);
                }
            case GE:
                switch (propertyType) {
                    case I:
                        return cb.ge((Expression<Integer>) ep, (Integer) value);
                    case L:
                        return cb.ge((Expression<Long>) ep, (Long) value);
                    case N:
                        return cb.ge((Expression<Double>) ep, (Double) value);
                    case D:
                        return cb.greaterThanOrEqualTo((Expression<Date>) ep, (Date) value);
                    case B:
                        return cb.greaterThanOrEqualTo((Expression<Boolean>) ep, (Boolean) value);
                    case S:

                        return cb.greaterThanOrEqualTo((Expression<String>) ep, (String) value);
                }
            case IN:
                return ep.in((Collection) value);
            case NOTIN:
                return cb.not(ep.in((Collection) value));
        }
        return cb.equal(ep, value);
    }

    private Predicate addFilter(QueryFilter filter, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String propertyName = filter.getPropertyName();
        String[] ps = propertyName.split("\\|");
        if (ps.length > 1) {
            Predicate[] predicates = new Predicate[ps.length];
            for (int i = ps.length - 1; i >= 0; i--) {
                Expression ep = getExpression(root, ps[i]);
                predicates[i] = addFilter(ep, filter.getValue(), filter.getMatchType(), filter.getPropertyType(), cb);
            }
            return cb.or(predicates);
        } else {
            Expression ep = getExpression(root, propertyName);
            return addFilter(ep, filter.getValue(), filter.getMatchType(), filter.getPropertyType(), cb);
        }

    }

    private Expression getExpression(Root<?> root, String property) {
        if (property.indexOf(".") == -1) {
            return root.get(property);
        } else {
            Path a = root;
            String[] ps = property.split("\\.");
            for (String p : ps) {
                a = a.get(p);
            }
            return a;
        }
    }

    private Predicate addFilters(List<QueryFilter> filters, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        for (QueryFilter pf : filters) {
            if (pf != null)
                predicates.add(addFilter(pf, root, query, cb));
        }
        return query.where(predicates.toArray(new Predicate[predicates.size()])).getGroupRestriction();
    }

    public Specification<T> buildSpecification(final List<QueryFilter> filters) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> tRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return addFilters(filters, tRoot, criteriaQuery, criteriaBuilder);
            }
        };
    }

    // @Override
    public Specification<T> specification(final List<QueryFilter> filters) {
        return buildSpecification(filters);
    }

    public List<T> findAll(List<QueryFilter> filters, Sort sort) {

        return findAll(buildSpecification(filters), sort);
    }

    private <S> Root<T> applySpecificationToCriteria(Specification<T> spec, CriteriaQuery<S> query) {

        Assert.notNull(query);
        Root<T> root = query.from(domainType);

        if (spec == null) {
            return root;
        }

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

    private TypedQuery<Long> getCountQuery(Specification<T> spec) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<T> root = applySpecificationToCriteria(spec, query);

        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }

        return entityManager.createQuery(query);
    }

    private TypedQuery<T> getQuery(Specification<T> spec, Sort sort) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(domainType);

        Root<T> root = applySpecificationToCriteria(spec, query);
        query.select(root);

        if (sort != null) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return entityManager.createQuery(query);
    }

    private TypedQuery<T> getQuery(Specification<T> spec) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(domainType);

        Root<T> root = applySpecificationToCriteria(spec, query);
        query.select(root);
        return entityManager.createQuery(query);
    }

    private TypedQuery<T> getQuery(Specification<T> spec, Pageable pageable) {

        Sort sort = pageable == null ? null : pageable.getSort();
        return getQuery(spec, sort);
    }

    public List<T> findAll(Specification<T> spec, Limit limit) {
        TypedQuery<T> query = getQuery(spec);
        query.setFirstResult(limit.getOffset());
        query.setMaxResults(limit.getSize());
        return query.getResultList();
    }

    public List<T> findAll(Specification<T> spec, Sort sort, Limit limit) {
        TypedQuery<T> query = getQuery(spec, sort);
        query.setFirstResult(limit.getOffset());
        query.setMaxResults(limit.getSize());
        return query.getResultList();
    }

    public List<T> findAll(List<QueryFilter> filters, Sort sort, Limit limit) {
        return findAll(buildSpecification(filters), sort, limit);
    }

    public List<T> findAll(List<QueryFilter> filters, Limit limit) {
        return findAll(buildSpecification(filters), limit);
    }
}