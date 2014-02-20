package com.palm.epalm.base.web;


import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Date: 13-3-19
 * Time: 下午2:33
 *
 * @author desire
 * @version 1.0
 *
 * @WebServlet(name = "application",urlPatterns = "/",loadOnStartup = 1,initParams = {
 * @WebInitParam(name = "contextConfigLocation",value = "classpath*:spring-mvc.xml")
 * })
 */
public class ApplicationServlet extends DispatcherServlet {

    public static final long serialVersionUID = -2440216393145762479L;

    private static final UrlPathHelper urlPathHelper = new UrlPathHelper();

    //  private static final JspServlet servlet = new JspServlet();
    //  private  static VelocityDecoratorServlet velocityDecoratorServlet = new VelocityDecoratorServlet();
    //  private FreemarkerDecoratorServlet servlet = new FreemarkerDecoratorServlet();
    @Override
    protected void initFrameworkServlet() throws ServletException {
        super.initFrameworkServlet();
        // servlet.init(getServletConfig());
        // velocityDecoratorServlet.init(getServletConfig());
        // servlet.init(getServletConfig());
    }

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // String path = urlPathHelper.getRequestUri(request);
        super.doService(request, response);
        // System.out.println(path);
        // request.getServletContext().getServlet("jsp");

        /**
        request.setAttribute("user", ShiroDbRealm.currentUser());

        if (path.matches("./layouts/[\\w/]+\\.jsp")) {
        //if (path.endsWith(".jsp")){
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            servlet.service(request,response);
        //  velocityDecoratorServlet.doGet(request,response);
        //  servlet.doGet(request, response);
        } else {
            super.doService(request, response);
        }
        **/
    }
}
