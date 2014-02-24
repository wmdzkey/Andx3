package com.umk.tiebashenqi.api;

import com.smartybean.android.http.annotation.Name;
import com.smartybean.android.http.annotation.Request;
import com.smartybean.android.http.common.HttpMethod;
import com.smartybean.core.AbstractCallBack;
import com.smartybean.core.anntions.Service;
import com.umk.andx3.api.ProgressCallBack;
import com.umk.tiebashenqi.config.SystemConfig;
import com.umk.tiebashenqi.entity.FavoriteTiezi;
import com.umk.tiebashenqi.entity.Tieba;
import com.umk.tiebashenqi.entity.Tiezi;
import com.umk.tiebashenqi.entity.TieziPicture;

import java.util.List;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：14-1-3
 */
@Service(SystemConfig.WEB_SERVER_URL_API +"favorite_tiezi/")
public interface FavoriteTieziApi {

    /**
     * 收藏帖子
     * */
    @Request(value="add", method = HttpMethod.POST)
    public void add(Tiezi tiezi, FavoriteTiezi favoriteTiezi, @Name("tieziPictureUrlList") String tieziPictureUrlList, ProgressCallBack<FavoriteTiezi> callBack);

    /**
     * 取消收藏帖子
     * */
    @Request(value="cancel", method = HttpMethod.POST)
    public void cancel(FavoriteTiezi favoriteTiezi, ProgressCallBack<String> callBack);


    /**
     * 查找我收藏的贴吧
     * */
    @Request(value="findFavoriteTieba", method = HttpMethod.POST)
    public void findFavoriteTieba(@Name("userId") Long userId, ProgressCallBack<List<Tieba>> callBack);

    /**
     * 查找我收藏的所有帖子
     * */
    @Request(value="findAllFavoriteTiezi", method = HttpMethod.POST)
    public void findAllFavoriteTiezi(@Name("userId") Long userId, AbstractCallBack<List<Tiezi>> callBack);


    /**
     * 查找我收藏的贴吧的帖子
     * */
    @Request(value="findFavoriteTiezi", method = HttpMethod.POST)
    public void findFavoriteTiezi(@Name("userId") Long userId, @Name("tiebaId") Long tiebaId, ProgressCallBack<List<Tiezi>> callBack);


    /**
     * 查找我收藏的贴吧的帖子的图片
     * */
    @Request(value="findFavoriteTieziPicture", method = HttpMethod.POST)
    public void findFavoriteTieziPicture(@Name("userId") Long userId, @Name("tieziUrl") String tieziUrl, ProgressCallBack<List<TieziPicture>> callBack);

}
