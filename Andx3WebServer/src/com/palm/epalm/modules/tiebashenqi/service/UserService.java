package com.palm.epalm.modules.tiebashenqi.service;

import com.palm.epalm.base.service.BaseService;
import com.palm.epalm.modules.tiebashenqi.repository.UserDao;
import com.palm.epalm.modules.tiebashenqi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User> {

    @Autowired
    private UserDao userDao;
    public UserDao repository(){
        return userDao;
    }

}
