package com.palm.epalm.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * -默认提供数据库操作实现的Factory
 *
 * @param <R>
 * @param <T>
 * @param <I>
 * @author desirelong
 * @version 1.0
 */
public class DefaultRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
        extends JpaRepositoryFactoryBean<R, T, I> {

    protected RepositoryFactorySupport createRepositoryFactory(
            EntityManager entityManager) {
        return new MyRepositoryFactory<T, I>(entityManager);
    }

    private static class MyRepositoryFactory<T, I extends Serializable> extends
            JpaRepositoryFactory {
        private EntityManager entityManager;

        public MyRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }

        @SuppressWarnings("unchecked")
        protected JpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata) {
            return new DefaultRepositoryImpl(metadata.getDomainType(), entityManager);
        }

        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return DefaultRepository.class;
        }
    }

}
