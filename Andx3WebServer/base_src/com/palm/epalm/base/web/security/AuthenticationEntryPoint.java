package com.palm.epalm.base.web.security;

import com.palm.epalm.base.context.ApplicationHelper;
import com.palm.epalm.base.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date: 13-5-28
 * Time: 下午5:21
 *
 * @author desire
 * @version 1.0
 */
public class AuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public AuthenticationEntryPoint(String loginUrl) {
        super(loginUrl);
    }

    @Autowired(required = false)
    BasicAuthenticationEntryPoint basicAuthenticationEntryPoint;
    @Autowired(required = false)
    LoginService loginService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ApplicationHelper.setCurrentHttpRequest(request);
        if (RequestUtil.isAjaxRequest(request) && basicAuthenticationEntryPoint != null) {
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
            basicAuthenticationEntryPoint.commence(request, response, authException);
            return;
        } else if (loginService != null && request.getParameter("_authorization") != null) {
            String _authorization = request.getParameter("_authorization");
            String[] authorizations = new String(new BASE64Decoder().decodeBuffer(_authorization)).split(":");
            if (authorizations.length == 2) {
                try {
                    loginService.login(authorizations[0], authorizations[1]);
                    request.getRequestDispatcher(request.getRequestURI()).forward(request, response);
                    return;
                } catch (AuthenticationException e) {
                    super.commence(request, response, e);
                    return;
                }
            }
        }
        super.commence(request, response, authException);
    }
}
