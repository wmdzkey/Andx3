package com.umk.tiebashenqi.api;

import com.smartybean.android.http.annotation.Name;
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
@Service(SystemConfig.WEB_SERVER_URL +"sys/")
public interface SystemApi {

    /**
     * 用户注册
     * */
    @Request(value="register", method = HttpMethod.POST)
    public void register(User user, AbstractCallBack<Integer> callBack);

    /**
     * 用户登录
     * */
    @Request(value="login", method = HttpMethod.POST)
    public void login(@Name("username") String username,@Name("password") String password, AbstractCallBack<User> callBack);

    /**
     * 检查唯一
     * */
    @Request(value="checkUsername",method =HttpMethod.POST)
    public void checkUsername(@Name("username") String username, AbstractCallBack<Boolean> callBack);

    /**
     * 修改用户信息
     * */
    @Request(value="update",method =HttpMethod.POST)
    public void update(User user, AbstractCallBack<User> callBack);

}
