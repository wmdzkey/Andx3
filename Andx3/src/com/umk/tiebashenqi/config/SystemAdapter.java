package com.umk.tiebashenqi.config;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.util.LogUtils;
import com.smartybean.android.http.HttpInterface;
import com.smartybean.core.AbstractCallBack;
import com.umk.andx3.base.BaseToast;
import com.umk.andx3.lib.config.Code;
import com.umk.andx3.lib.util.Base64CoderUtil;
import com.umk.andx3.util.SharePreferenceUtil;
import com.umk.tiebashenqi.api.FavoriteTieziApi;
import com.umk.tiebashenqi.entity.FavoriteTiezi;
import com.umk.tiebashenqi.entity.Tiezi;
import com.umk.tiebashenqi.entity.User;
import com.umk.tiebashenqi.lpi.FavoriteTieziLpi;
import com.umk.tiebashenqi.lpi.TieziLpi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Winnid
 * @title 存储系统运行时变量信息
 * 已经加密：
 * 加密过程：对象->Json->64格式化->加密->SP
 * 解密过程：SP->解密->64反格式化->Json->对象
 * @version:1.0
 * @since：13-12-18
 */
public class SystemAdapter {

    public static Context ctx;
    public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").create();

    private static User currentUser;
    private static final String USER = "storeCurrentUser";
    private static final String KEY_USER = "storeCurrentUserJson";

    private static boolean noticeableTieziFavorite = true;
    private static final String NOTICEABLE = "storeNoticeable";
    private static final String NOTICEABLE_TIEZI_FAVORITE = "storeNoticeableTieziFavorite";

    public final static int REGISTER_SUCCESS = 1;//注册成功
    public final static int REGISTER_FAULT_DEFAULT = -1;//注册失败默认
    public final static int REGISTER_FAULT_USERNAME_EXIST = -2;//注册失败:用户名已被注册
    public final static int LOGIN_STATE_FAULT_DEFAULT = -1;//登陆失败默认:用户名密码错误
    public final static int LOGIN_STATE_FAULT_LOCK = -2;//登陆失败默认:用户被锁定


    /**
     * 读取帖子收藏提醒参数（以后把提醒的类型改为枚举，做成一个通用方法）
     */
    public static boolean getNoticeableTieziFavorite() {
        SharePreferenceUtil spUtil = SharePreferenceUtil.getInstance(ctx, NOTICEABLE);
        noticeableTieziFavorite = spUtil.getBoolean(NOTICEABLE_TIEZI_FAVORITE, true);
        return noticeableTieziFavorite;
    }

    /**
     * 设置帖子收藏提醒参数
     */
    public static void setNoticeableTieziFavorite(boolean noticeable) {
        noticeableTieziFavorite = noticeable;
        SharePreferenceUtil spUtil = SharePreferenceUtil.getInstance(ctx, NOTICEABLE);
        spUtil.setBoolean(NOTICEABLE_TIEZI_FAVORITE, noticeable);
    }

    /**
     * 读取当前登录用户
     */
    public static User getCurrentUser(){

        if(currentUser == null) {
            SharePreferenceUtil spUtil = SharePreferenceUtil.getInstance(ctx, USER);
            String currentUserStr = spUtil.getString(KEY_USER,"");
            if(currentUserStr.equals("")){
                return null;
            }
            //TODO 此处若是加密则应给解密
            currentUserStr = new String(Base64CoderUtil.decode(currentUserStr));
            currentUser = gson.fromJson(currentUserStr, User.class);
        }
        return currentUser;

    }

    public static boolean autoLogin(){

        HttpInterface.clearDefaultAuth();

        if (getCurrentUser() == null){
            return false;
        } else {
            String userInfo = currentUser.getUsername() + ";" + currentUser.getPassword();
            String password = currentUser.getPassword();
            userInfo = new String(Base64CoderUtil.encodeString(userInfo));//防止中文乱码
            //TODO 此处给加密，且考虑密码不用写在第一个参数中
            HttpInterface.setDefaultAuth(ctx, userInfo, password);
            return true;
        }
    }

    public static void reLogin(){
        //TODO
        LogUtils.e("请重新登录");
        BaseToast.showToast(ctx, "请重新登陆");
    }

    public static void login(User currentUser){
        if(currentUser != null) {
            HttpInterface.clearDefaultAuth();
            SharePreferenceUtil spUtil = SharePreferenceUtil.getInstance(ctx, USER);
            String currentUserStr = gson.toJson(currentUser);
            currentUserStr = new String(Base64CoderUtil.encodeString(currentUserStr));
            //TODO 应给加密
            spUtil.setString(KEY_USER, currentUserStr);

            String userInfo = currentUser.getUsername() + ";" + currentUser.getPassword();
            String password = currentUser.getPassword();
            userInfo = new String(Base64CoderUtil.encodeString(userInfo));//防止中文乱码
            //TODO 此处给加密，且考虑密码不用写在第一个参数中
            HttpInterface.setDefaultAuth(ctx, userInfo, password);

            doAfterLoginSuccess();
        }
    }

    public static void logout(){
        SharePreferenceUtil spUtil = SharePreferenceUtil.getInstance(ctx, USER);
        spUtil.clear();
        currentUser = null;
        HttpInterface.clearDefaultAuth();
    }

    public static void doAfterLoginSuccess() {
        //初始化用户收藏信息
        initUserFavoriteInfo();
    }

    private static void initUserFavoriteInfo() {
        FavoriteTieziApi favoriteTieziApi = HttpInterface.proxy(ctx, FavoriteTieziApi.class);
        favoriteTieziApi.findAllFavoriteTiezi(getCurrentUser().getId(), new AbstractCallBack<List<Tiezi>>() {
            @Override
            public void call(List<Tiezi> tieziInDBList) {
                //TODO 初始化帖子倒来倒去
                List<FavoriteTiezi> favoriteTieziList = new ArrayList<FavoriteTiezi>();
                for(Tiezi tiezi : tieziInDBList){
                    FavoriteTiezi favoriteTiezi = new FavoriteTiezi();
                    favoriteTiezi.setState(Code.State.Normal);
                    favoriteTiezi.setTiebaId(tiezi.getTiebaId());
                    favoriteTiezi.setTieziUrl(tiezi.getUrl());
                    favoriteTiezi.setUserId(SystemAdapter.getCurrentUser().getId());
                    favoriteTieziList.add(favoriteTiezi);

                    tiezi.setId(null);
                }

                //保存帖子到数据库
                TieziLpi tieziLpi = new TieziLpi();
                tieziLpi.saveOrUpdate(ctx, tieziInDBList);
                FavoriteTieziLpi favoriteTieziLpi = new FavoriteTieziLpi();
                favoriteTieziLpi.saveOrUpdate(ctx, favoriteTieziList);
            }
        });
    }
}
