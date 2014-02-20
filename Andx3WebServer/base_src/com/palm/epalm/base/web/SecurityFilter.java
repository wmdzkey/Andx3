package com.palm.epalm.base.web;

import com.palm.epalm.base.context.ApplicationHelper;
import com.palm.epalm.base.web.security.AccessFilter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * Date: 13-3-19
 * Time: 下午2:51
 *
 * @author desire
 * @version 1.0
 * @WebFilter(filterName = "securityFilter",urlPatterns = "/*",asyncSupported = true)
 */
public class SecurityFilter implements Filter {
    private Collection<AccessFilter> accessFilters;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void doFilter(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response, javax.servlet.FilterChain filterChain) throws javax.servlet.ServletException, java.io.IOException {
        if (accessFilters == null) {
            Map<String, AccessFilter> accessFilterMap = ApplicationHelper.getApplicationContext((HttpServletRequest) request).getBeansOfType(AccessFilter.class);
            accessFilters = accessFilterMap.values();
        }
        for (AccessFilter filter : accessFilters) {
            if (!filter.doFilter((HttpServletRequest) request, (HttpServletResponse) response)) {
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        accessFilters = null;
    }
}
