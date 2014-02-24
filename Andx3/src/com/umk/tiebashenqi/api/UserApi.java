package com.umk.tiebashenqi.api;

import com.smartybean.android.http.annotation.Request;
import com.smartybean.android.http.common.HttpMethod;
import com.smartybean.core.AbstractCallBack;
import com.smartybean.core.anntions.Service;
import com.umk.tiebashenqi.config.SystemConfig;
import com.umk.tiebashenqi.entity.User;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：14-01-10
 */
@Service(SystemConfig.WEB_SERVER_URL_API +"user/")
public interface UserApi {

    /**
     * 更新用户信息
     * */
    @Request(value="update",method =HttpMethod.POST)
    public User update(User user, AbstractCallBack<String> callBack);

}
