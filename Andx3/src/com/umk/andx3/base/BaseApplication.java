package com.umk.andx3.base;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import cn.waps.AppConnect;
import com.lidroid.xutils.util.LogUtils;
import com.smartybean.android.http.HttpInterface;
import com.smartybean.android.http.common.DefaultRequestHandler;
import com.smartybean.android.http.common.ResponseException;
import com.smartybean.util.NetWorkUtil;
import com.umk.tiebashenqi.config.SystemAdapter;
import org.apache.http.NameValuePair;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：14-1-9
 * @Description
 * 1.广告初始化
 * 2.全局静态Context
 * 3.处理后台消息
 */
public class BaseApplication extends Application {


    private Context aContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initBase();
        initServer();//处理后台消息
        initAd();
    }


    private void initBase() {
        aContext = SystemAdapter.ctx = getApplicationContext();
        //LogUtils.allowE = false;
    }


    private void initServer() {
        HttpInterface.setDefaultRequestHandler(new DefaultRequestHandler(aContext) {

            @Override
            public void beforeRequest(List<NameValuePair> params) {
                super.beforeRequest(params);
            }

            public void onException(final Exception e) throws java.lang.Exception {
                if (e instanceof UnknownHostException) {
                    if (!NetWorkUtil.isConnected(context)) {
                        doInUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BaseToast.showToast(context, "网络连接不可用");
                            }
                        });
                    } else {
                        doInUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BaseToast.showToast(context, "服务器正在维护，请稍后再试");
                            }
                        });
                    }
                } else if (e instanceof HttpHostConnectException) {
                    if (!NetWorkUtil.isConnected(context)) {
                        doInUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BaseToast.showToast(context, "网络连接不可用");
                                //NetWorkUtil.showNetSettingView(context);
                            }
                        });
                    }else{
                        doInUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BaseToast.showToast(context, "服务器正在维护，请稍后再试");
                            }
                        });
                        return;
                    }
                } else if (e instanceof ConnectTimeoutException) {
                    doInUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseToast.showToast(context, "服务器正在忙碌中，请稍后再试");
                        }
                    });

                } else if (e instanceof ResponseException) {
                    doInUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch (((ResponseException) e).httpStatus()) {
                                case UNAUTHORIZED://401, 需要权限
                                    SystemAdapter.reLogin();
                                    break;
                                case NON_AUTHORITATIVE_INFORMATION://没有相关权限
                                case NOT_FOUND://404
                                case INTERNAL_SERVER_ERROR://500 服务器内部错误
                                    BaseToast.showToast(context, "服务器异常 :" + e.toString());
                                    LogUtils.e(e.toString());
                            }

                        }
                    });
                }

            }

        });
    }

    private void initAd() {
        // 初始化统计器，需要在AndroidManifest中注册APP_ID和APP_PID值
        AppConnect.getInstance(aContext);
        // 预加载插屏广告内容（仅在使用到插屏广告的情况，才需要添加）
        AppConnect.getInstance(this).initPopAd(this);
    }

}
