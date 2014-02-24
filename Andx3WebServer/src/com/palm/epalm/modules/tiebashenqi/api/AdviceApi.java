package com.palm.epalm.modules.tiebashenqi.api;

import com.palm.epalm.modules.tiebashenqi.entity.Advice;
import com.palm.epalm.modules.tiebashenqi.entity.Tieba;
import com.palm.epalm.modules.tiebashenqi.service.AdviceService;
import com.palm.epalm.modules.tiebashenqi.service.TiebaService;
import com.umk.andx3.lib.config.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/advice")
public class AdviceApi {

    @Autowired
    private AdviceService adviceService;

    /** 列表 */
    @RequestMapping(value = "add")
    @ResponseBody
    public Integer add(Advice advice) {
        if (adviceService.save(advice) != null) {
            return Code.returnStateCode.AdviceSendSuccess;
        }
        return Code.returnStateCode.AdviceSendFaild;
    }
}
