package com.palm.epalm.base.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Date: 13-6-19
 * Time: 下午6:53
 *
 * @author desire
 * @version 1.0
 */
public interface AccessFilter {
    public boolean doFilter(HttpServletRequest request, HttpServletResponse response);
}
