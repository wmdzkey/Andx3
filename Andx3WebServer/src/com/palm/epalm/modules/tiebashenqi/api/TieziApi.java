package com.palm.epalm.modules.tiebashenqi.api;

import com.palm.epalm.modules.tiebashenqi.entity.Tiezi;
import com.palm.epalm.modules.tiebashenqi.entity.TieziPicture;
import com.palm.epalm.modules.tiebashenqi.service.TieziPictureService;
import com.palm.epalm.modules.tiebashenqi.service.TieziService;
import com.umk.andx3.lib.config.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/tiezi")
@Secured("ROLE_USER")
public class TieziApi {

    @Autowired
    private TieziService tieziService;
    @Autowired
    private TieziPictureService tieziPictureService;

    @RequestMapping(value = "update")
    @ResponseBody
    public String update(Tiezi tiezi, List<TieziPicture> tieziPictureList) {

        tiezi = tieziService.findByUrl(tiezi.getUrl());
        for (TieziPicture tieziPicture : tieziPictureList) {
            tieziPicture.setTiebaId(tiezi.getTiebaId());
            tieziPicture.setTieziId(tiezi.getId());
            tieziPicture.setState(Code.State.Normal);
            tieziPictureService.addWithNoExist(tieziPicture, "imageUrl", tieziPicture.getImageUrl());
        }

        return "更新成功";
    }

}
