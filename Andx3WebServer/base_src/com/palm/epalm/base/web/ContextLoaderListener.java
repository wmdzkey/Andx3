package com.palm.epalm.base.web;

import javax.servlet.ServletContextEvent;

/**
 * Date: 13-3-19
 * Time: 下午2:45
 *
 * @author desire
 * @version 1.0
 * @WebListener
 */
public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
    }
}
