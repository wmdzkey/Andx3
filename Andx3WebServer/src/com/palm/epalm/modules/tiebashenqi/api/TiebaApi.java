package com.palm.epalm.modules.tiebashenqi.api;

import com.palm.epalm.base.repository.MatchType;
import com.palm.epalm.base.repository.QueryFilter;
import com.palm.epalm.modules.tiebashenqi.entity.Tieba;
import com.palm.epalm.modules.tiebashenqi.service.TiebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @RequestMapping(value = "test2")
    @ResponseBody
    public String test2() {
        return new Date().toString();
    }

    /** 列表 */
    @RequestMapping(value = "test3")
    @ResponseBody
    public List<Tieba> test3() {
        List<QueryFilter> xx = new ArrayList<QueryFilter>();
        xx.add(new QueryFilter("id", "-1", MatchType.GT));
        List<Tieba> tiebaList = tiebaService.findAll(xx);
        return tiebaList;
    }

    /** 列表 */
    @RequestMapping(value = "add")
    @ResponseBody
    public Tieba add(Tieba tieba) {
       return tiebaService.addWithNoExist(tieba, "theNameUrl", tieba.getTheNameUrl());
    }

}
