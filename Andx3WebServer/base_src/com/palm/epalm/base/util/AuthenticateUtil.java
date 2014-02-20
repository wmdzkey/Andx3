package com.palm.epalm.base.util;

import com.palm.epalm.base.context.ApplicationHelper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

/**
 * 简单的权限判断
 *
 * @author desire
 * @since 2013-08-01 12:47
 */
public class AuthenticateUtil {
    /**
     * 当前登录用户和指定用户是同一个用户
     *
     * @param user
     */
    public static void assertUserEqual(UserDetails user) {
        UserDetails userDetails = ApplicationHelper.getCurrentUser();
        if (userDetails == null) {
            throw new PreAuthenticatedCredentialsNotFoundException("用户未登录");
        }
        assertEqual(userDetails.getUsername(), user.getUsername());
    }

    public static void assertTrue(boolean flag) {
        if (!flag) {
            throw new AccessDeniedException("当前用户没有这个权限");
        }
    }

    public static void assertEqual(Object target, Object source) {
        if (target == source) return;
        assertTrue(target.equals(source));
    }

    public static void assertNotNull(Object object) {
        assertTrue(object != null);
    }

    public static void assertNotEmpty(String target) {
        assertNotNull(target);
        assertTrue(!target.trim().equals(""));
    }
}
