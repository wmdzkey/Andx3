package com.umk.tiebashenqi.api;

import com.smartybean.android.http.annotation.Name;
import com.smartybean.android.http.annotation.Request;
import com.smartybean.android.http.common.HttpMethod;
import com.smartybean.core.AbstractCallBack;
import com.smartybean.core.anntions.Service;
import com.umk.tiebashenqi.config.SystemConfig;
import com.umk.tiebashenqi.entity.Tiezi;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：14-1-3
 */
@Service(SystemConfig.WEB_SERVER_URL_API +"tiezi/")
public interface TieziApi {

    /**
     *更新追加帖子
     * */
    @Request(value="update", method = HttpMethod.POST)
    public void update(Tiezi tiezi, @Name("tieziPictureList")String tieziPictureList, AbstractCallBack<String> callBack);

}
