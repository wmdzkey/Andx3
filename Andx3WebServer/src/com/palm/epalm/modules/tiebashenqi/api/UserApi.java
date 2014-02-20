package com.palm.epalm.modules.tiebashenqi.api;

import com.palm.epalm.modules.tiebashenqi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
@Secured("ROLE_USER")
public class UserApi {
    @Autowired
    private UserService userService;

 }
