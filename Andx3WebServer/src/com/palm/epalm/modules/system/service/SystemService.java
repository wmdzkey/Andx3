package com.palm.epalm.modules.system.service;

import com.palm.epalm.base.context.ApplicationHelper;
import com.palm.epalm.base.repository.MatchType;
import com.palm.epalm.base.repository.QueryFilter;
import com.palm.epalm.base.web.security.LoginService;
import com.palm.epalm.modules.system.api.SystemApi;
import com.palm.epalm.modules.system.repository.SystemDao;
import com.umk.andx3.lib.util.Base64CoderUtil;
import com.palm.epalm.modules.tiebashenqi.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemService implements UserDetailsService, LoginService {

    private static Logger logger = LoggerFactory.getLogger(SystemService.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SystemDao systemDao;

    @Override
    public UserDetails loadUserByUsername(String userInfo) throws UsernameNotFoundException {
        //TODO:前台传输加密后台还需要解密
        try {
            userInfo = new String(Base64CoderUtil.decode(userInfo), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] token = userInfo.split(";");
        final String username = token[0];
        final String password = token[1];
        List<User> users = systemDao.findAll(new ArrayList<QueryFilter>() {{
            add(new QueryFilter("SEQ_username", username));
            add(new QueryFilter("SEQ_password", password));
        }});
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return users.get(0);
    }



    /**
     * 密码加密
     * */
    public static String encryptPassword(UserDetails user,String password) {
        return encryptPassword(user.getUsername(), password);
    }

    public static String encryptPassword(String username,String password) {
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        return md5PasswordEncoder.encodePassword(password, username);
    }

    /**
     * 使用用户名密码登录，一般情况从filter登录而不是使用该方法登录
     * @param username
     * @param password
     */
    public void login(String username, String password){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        token.setDetails(new WebAuthenticationDetails(ApplicationHelper.getCurrentHttpRequest()));
        Authentication authenticate = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

    /**
     * 使用用户名密码登录
     */
    public User login(List<QueryFilter> queryFilters) {
        List<User> users = systemDao.findAll(queryFilters);
        if(users.isEmpty()){
            User user = new User();
            user.setId((long)SystemApi.LOGIN_STATE_FAULT_DEFAULT);//用户登录失败
            return user;
        }
        return users.get(0);
    }

    public User register(User user) {
        return systemDao.save(user);
    }

    public boolean checkUsername(String username) {
        List<QueryFilter> condition = new ArrayList<QueryFilter>();
        condition.add(new QueryFilter("username", username, MatchType.EQ));
        List<User> users = systemDao.findAll(condition);
        if(users != null && users.size() != 0) {
            return false;
        }
        return true;
    }

    public void update(User user) {
        systemDao.save(user);
    }
}
