package com.palm.epalm.base.repository;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * use age:
 * <bean id="transactionManager" class="com.palm.epalm.base.repository.RWSTransactionManager">
 * <property name="entityManagerFactory" ref="entityManagerFactory"/>
 * </bean>
 * <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
 * <property name="dataSource" ref="dataSource"/>
 * ....
 * </bean>
 * <bean id="dataSource" class="com.palm.epalm.base.repository.RWSDataSource">
 * <property name="targetDataSources">
 * <map key-type="java.lang.String">
 * <entry key="read" value-ref="dataSource_read" />
 * <entry key="write" value-ref="dataSource_write" />
 * </map>
 * </property>
 * <property name="defaultTargetDataSource" ref="dataSource_read"/>
 * </bean>
 *
 * @author desire
 * @version 1.0
 * @since 13-9-28 17:49
 */
public class RWSTransactionManager extends JpaTransactionManager {
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        RWSDataSource.isReadOnly.set(definition.isReadOnly());
        super.doBegin(transaction, definition);
    }
}
