package com.umk.tiebashenqi.api;

import com.smartybean.android.http.annotation.Request;
import com.smartybean.android.http.common.HttpMethod;
import com.smartybean.core.AbstractCallBack;
import com.smartybean.core.anntions.Service;
import com.umk.tiebashenqi.config.SystemConfig;
import com.umk.tiebashenqi.entity.Tieba;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：14-1-3
 */
@Service(SystemConfig.WEB_SERVER_URL_API +"tieba/")
public interface TiebaApi {

    /**
     * 快速测试
     * */
    @Request(value="test", method = HttpMethod.POST)
    public void test(AbstractCallBack<String> callBack);


    /**
     * 存储贴吧
     * */
    @Request(value="add", method = HttpMethod.POST)
    public void add(Tieba tieba, AbstractCallBack<Tieba> callBack);

}
