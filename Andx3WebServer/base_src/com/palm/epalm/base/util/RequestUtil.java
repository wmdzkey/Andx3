package com.palm.epalm.base.util;

import com.palm.epalm.base.context.ApplicationHelper;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: 13-5-28
 * Time: 下午4:54
 * -request辅助类
 *
 * @author desire
 * @version 1.0
 */
public class RequestUtil {
    /**
     * -获取当前请求
     *
     * @return
     */
    public static HttpServletRequest currentRequest() {
        return ApplicationHelper.getCurrentHttpRequest();
    }

    /**
     * -根据请求头判断请求是否为ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("x-requested-with"))
                || MediaType.APPLICATION_JSON.toString().equals(request.getHeader("Content-Type"))
                || MediaType.APPLICATION_JSON.toString().equals(request.getHeader("accept"))
                || MediaType.APPLICATION_XML.toString().equals(request.getHeader("accept"));
    }
}
