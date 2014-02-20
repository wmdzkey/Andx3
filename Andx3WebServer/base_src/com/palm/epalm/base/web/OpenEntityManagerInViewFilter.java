package com.palm.epalm.base.web;

import javax.servlet.annotation.WebFilter;

/**
 * Date: 13-3-19
 * Time: 下午2:50
 *
 * @author desire
 * @version 1.0
 * @WebFilter(filterName = "openEntityManagerInViewFilter",urlPatterns = "/*")
 */
public class OpenEntityManagerInViewFilter extends org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter {
}
