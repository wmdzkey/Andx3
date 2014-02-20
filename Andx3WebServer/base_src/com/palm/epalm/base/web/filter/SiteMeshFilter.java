package com.palm.epalm.base.web.filter;

import com.palm.epalm.base.context.ApplicationHelper;
import com.palm.epalm.base.util.RequestUtil;

import javax.servlet.FilterChain
        ;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * -sitemesh所用的filter，添加ajax及pjax的过滤
 *
 * @author desire
 * @since 2013-04-26 16:25
 */
public class SiteMeshFilter extends org.sitemesh.config.ConfigurableSiteMeshFilter {
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getContextPath();
        String root = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        request.setAttribute("_uri", request.getRequestURI().replaceFirst(path, ""));
        request.setAttribute("base", root);
        request.setAttribute("ctx", path);
        //  Object user = ApplicationHelper.getCurrentUser();
        //  if(user!=null){
        //     request.setAttribute("user",user);
        //  }

        if (RequestUtil.isAjaxRequest((HttpServletRequest) servletRequest) || servletRequest.getParameter("ajax") != null || "true".equals(((HttpServletRequest) servletRequest).getHeader("X-PJAX"))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            super.doFilter(servletRequest, servletResponse, filterChain);
        }
    }
}
