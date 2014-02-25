package com.palm.epalm.modules.tiebashenqi.api;

import com.google.gson.Gson;
import com.palm.epalm.base.context.ApplicationHelper;
import com.palm.epalm.modules.tiebashenqi.entity.FavoriteTiezi;
import com.palm.epalm.modules.tiebashenqi.entity.Tieba;
import com.palm.epalm.modules.tiebashenqi.entity.Tiezi;
import com.palm.epalm.modules.tiebashenqi.entity.TieziPicture;
import com.palm.epalm.modules.tiebashenqi.service.*;
import com.sun.deploy.util.SystemUtils;
import com.umk.andx3.lib.config.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/favorite_tiezi")
@Secured("ROLE_USER")
public class FavoriteTieziApi {
    @Autowired
    private FavoriteTieziService favoriteTieziService;
    @Autowired
    private TiebaService tiebaService;
    @Autowired
    private TieziService tieziService;
    @Autowired
    private TieziPictureService tieziPictureService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "add")
    @ResponseBody
    public FavoriteTiezi add(Tiezi tiezi, FavoriteTiezi favoriteTiezi, String tieziPictureUrlList) {

        tiezi = tieziService.addWithNoExist(tiezi, "url", tiezi.getUrl());

        List<String> urlList = new ArrayList<String>();
        if (tieziPictureUrlList != null && !tieziPictureUrlList.equals("")) {
            urlList = ApplicationHelper.gson.fromJson(tieziPictureUrlList, List.class);
        }

        for (String tieziPictureUrl : urlList) {
            TieziPicture tieziPicture = new TieziPicture();
            tieziPicture.setImageUrl(tieziPictureUrl);
            tieziPicture.setTiebaId(tiezi.getTiebaId());
            tieziPicture.setTieziId(tiezi.getId());
            tieziPicture.setState(Code.state.Normal);
            tieziPictureService.addWithNoExist(tieziPicture, "imageUrl", tieziPicture.getImageUrl());
        }

        favoriteTiezi.setTieziUrl(tiezi.getUrl());
        favoriteTiezi.setTiebaId(tiezi.getTiebaId());
        favoriteTiezi.setTieziId(tiezi.getId());
        favoriteTiezi.setState(Code.state.Normal);
        favoriteTiezi = favoriteTieziService.add(favoriteTiezi);

        return favoriteTiezi;
    }


    @RequestMapping(value = "cancel")
    @ResponseBody
    public Integer cancel(FavoriteTiezi favoriteTiezi) {
        favoriteTieziService.cancel(favoriteTiezi);
        return Code.returnStateCode.FavoriteCancelSuccess;
    }


    /**
     * 查找我收藏的贴吧
     * */
    @RequestMapping(value = "findFavoriteTieba")
    @ResponseBody
    public List<Tieba> findFavoriteTieba(Long userId) {
        return favoriteTieziService.findFavoriteTieba(userId);
    }

    /**
     * 查找我收藏的所有的帖子
     * */
    @RequestMapping(value = "findAllFavoriteTiezi")
    @ResponseBody
    public List<Tiezi> findAllFavoriteTiezi(Long userId) {
        return favoriteTieziService.findAllFavoriteTiezi(userId);
    }

    /**
     * 查找我收藏的贴吧的帖子
     * */
    @RequestMapping(value = "findFavoriteTiezi")
    @ResponseBody
    public List<Tiezi> findFavoriteTiezi(Long userId, Long tiebaId) {
        return favoriteTieziService.findFavoriteTiezi(userId, tiebaId);
    }


    /**
     * 查找我收藏的贴吧的帖子的图片
     * */
    @RequestMapping(value = "findFavoriteTieziPicture")
    @ResponseBody
    public List<TieziPicture> findFavoriteTieziPicture(Long userId, String tieziUrl) {
        Tiezi tiezi = tieziService.findByUrl(tieziUrl);
        if (tiezi != null) {
            return tieziPictureService.findTieziPicture(tiezi.getId());
        }
        return null;
    }

}
