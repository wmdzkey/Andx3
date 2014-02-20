package com.palm.epalm.modules.system.api;

import com.palm.epalm.base.repository.QueryFilter;
import com.palm.epalm.modules.system.service.SystemService;
import com.palm.epalm.modules.tiebashenqi.entity.User;
import com.umk.andx3.lib.config.Code;
import com.umk.andx3.lib.util.DesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping("/api/sys")
public class SystemApi {

    @Autowired
    private SystemService systemService;

    public final static int REGISTER_SUCCESS = 1;//注册成功
    public final static int REGISTER_FAULT_DEFAULT = -1;//注册失败默认
    public final static int REGISTER_FAULT_USERNAME_EXIST = -2;//注册失败:用户名已被注册
    public final static int LOGIN_STATE_FAULT_DEFAULT = -1;//登陆失败默认:用户名密码错误
    public final static int LOGIN_STATE_FAULT_LOCK = -2;//登陆失败默认:用户被锁定

    /**
     * 注册用户
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public Integer register(User user) {
        if(!checkUsername(user.getUsername())) {
            return REGISTER_FAULT_USERNAME_EXIST;//用户名存在
        }
        if(systemService.register(user) != null) {
            return REGISTER_SUCCESS;//注册成功
        }
        return REGISTER_FAULT_DEFAULT;//注册失败
    }


    /**
     * 登陆
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public User login(final String username,final String password){
        User user = systemService.login(new ArrayList<QueryFilter>(){{
            add(new QueryFilter("SEQ_username", username));
            //add(new QueryFilter("SEQ_password", password));
            //TODO 加密
            //add(new QueryFilter("SEQ_password", DesUtils.getInstance(Code.DESKEY).decrypt(password)));
        }});
        return user;
    }


    /**
     * 检查用户是否注册
     * */
    @RequestMapping(value = "checkUsername", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkUsername(String username){
        if(username == null || username.equals("")){
            return false;
        }
        return systemService.checkUsername(username);
    }

    /**
     *修改用户信息
     * */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public User update(User user){
        if(!checkUsername(user.getUsername())) {
            systemService.update(user);
        }
        return user;
    }

 }
