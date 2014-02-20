package com.palm.epalm.base.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palm.epalm.base.util.RequestUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date: 13-5-28
 * Time: 下午5:25
 *
 * @author desire
 * @version 1.0
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (RequestUtil.isAjaxRequest(request)) {
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            objectMapper.writeValue(response.getWriter(), authentication.getPrincipal());
        } else {
            response.addCookie(new Cookie("name", authentication.getName()));
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
