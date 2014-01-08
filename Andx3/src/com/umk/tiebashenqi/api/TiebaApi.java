package com.umk.tiebashenqi.api;

import com.smartybean.android.http.annotation.Request;
import com.smartybean.android.http.common.HttpMethod;
import com.smartybean.core.AbstractCallBack;
import com.smartybean.core.anntions.Service;
import com.umk.tiebashenqi.config.SystemConfig;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：14-1-3
 */
@Service(SystemConfig.WEB_SERVER_URL +"tieba/")
public interface TiebaApi {
    /**
     * 存储相册成员
     * */
    @Request(value="test", method = HttpMethod.POST)
    public void test(AbstractCallBack<String> callBack);

}
