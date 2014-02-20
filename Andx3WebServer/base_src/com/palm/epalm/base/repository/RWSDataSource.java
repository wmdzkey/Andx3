package com.palm.epalm.base.repository;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author desire
 * @version 1.0
 * @since 13-9-28 18:24
 */
public class RWSDataSource extends AbstractDataSource implements InitializingBean {
    protected static final ThreadLocal<Boolean> isReadOnly = new ThreadLocal<Boolean>();
    protected DataSource readDateSource;
    protected DataSource writeDateSource;

    protected DataSource determineTargetDataSource() {
        return isReadOnly.get() ? readDateSource : writeDateSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        assert readDateSource != null;
        assert writeDateSource != null;
    }

    public void setReadDateSource(DataSource dateSource) {
        readDateSource = dateSource;
    }

    public void setWriteDateSource(DataSource dateSource) {
        writeDateSource = dateSource;
    }
}
