package com.palm.epalm.base.context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * -用于获取常用的Context和变量，包括:request,session,Spring的context,SecurityContext以及当前用户
 *
 * @author desireloong
 * @version 1.0
 */
public final class ApplicationHelper {


    public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").create();


    private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();

    private ApplicationHelper() {}

    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();//.currentRequestAttributes();
    }

    /**
     * -当前Http请求
     *
     * @return HttpServletRequest 当前的请求
     */
    public static void setCurrentHttpRequest(HttpServletRequest request) {
        ApplicationHelper.request.set(request);
    }

    /**
     * -当前Http请求
     *
     * @return HttpServletRequest 当前的请求
     */
    public static HttpServletRequest getCurrentHttpRequest() {
        HttpServletRequest req = request.get();
        return req == null ? getServletRequestAttributes().getRequest() : req;
    }

    public static String currentHost() {
        return getCurrentHttpRequest().getRequestURL().toString().replace(getCurrentHttpRequest().getRequestURI(), "");
    }

    /**
     * -session
     *
     * @return
     */
    public static HttpSession getSession() {
        return getCurrentHttpRequest().getSession();
    }

    public static WebApplicationContext getApplicationContext() {
        return getApplicationContext(getCurrentHttpRequest());
    }

    public static WebApplicationContext getApplicationContext(HttpServletRequest req) {
        return WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
    }

    /**
     * 获取登陆名
     *
     * @return
     */
    public static String currentUserName() {
        Authentication at = getSecurityContext().getAuthentication();
        if (at == null) return null;
        Object ud = at.getPrincipal();
        if (ud == null) return null;
        if ("anonymousUser".equals(ud)) return null;
        return ((UserDetails) ud).getUsername();
    }

    /**
     * 获取当前登录用户
     *
     * @param session
     * @param <T>
     * @return
     */
    public static <T extends UserDetails> T getCurrentUser(HttpSession session) {
        Object ud = getSecurityContext(session).getAuthentication().getPrincipal();
        if (ud == null) return null;
        if ("anonymousUser".equals(ud)) return null;
        return (T) ud;
    }

    /**
     * 获取当前登录用户
     *
     * @param <T> 系统定义用户类型
     * @return 登录用户
     */
    public static <T extends UserDetails> T getCurrentUser() {
        Authentication at = getSecurityContext().getAuthentication();
        if (at == null) return null;
        Object ud = at.getPrincipal();
        if (ud == null) return null;
        if ("anonymousUser".equals(ud)) return null;
        return (T) ud;
    }

    public static SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    public static SecurityContext getSecurityContext(HttpSession httpSession) {
        return ((SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT"));
    }

    //public void test(){
    //	UserDetails ud = (UserDetails)getSecurityContext().getAuthentication().getPrincipal();
    //}
}
