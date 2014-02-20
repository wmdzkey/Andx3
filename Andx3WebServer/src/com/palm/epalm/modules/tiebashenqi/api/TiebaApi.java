package com.palm.epalm.modules.tiebashenqi.api;

import com.palm.epalm.modules.tiebashenqi.entity.Tieba;
import com.palm.epalm.modules.tiebashenqi.service.TiebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/tieba")
public class TiebaApi {

    @Autowired
    private TiebaService tiebaService;

    /** 列表 */
    @RequestMapping(value = "test")
    @ResponseBody
    public String test() {
        return "我就是个测试";
    }

    /** 列表 */
    @RequestMapping(value = "add")
    @ResponseBody
    public Tieba add(Tieba tieba) {
       return tiebaService.addWithNoExist(tieba, "theNameUrl", tieba.getTheNameUrl());
    }

}
