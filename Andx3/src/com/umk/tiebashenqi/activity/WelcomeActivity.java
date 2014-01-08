package com.umk.tiebashenqi.activity;

import android.os.Looper;
import android.widget.TextView;
import cn.waps.AppConnect;
import com.googlecode.androidannotations.annotations.*;
import com.umk.andx3.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.demo.activity.ExpandableListViewActivity;
import com.umk.demo.activity.SwipeListViewActivity;

import android.os.Handler;

@NoTitle
@EActivity(R.layout.activity_welcome)
public class WelcomeActivity extends BaseActivity {

    Handler mHandler;

    @AfterViews
    void init() {
        initInBackground();
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity_.class);
                finish();
            }
        }, 2000);
    }

    @Background
    void initInBackground() {
        // 初始化统计器，需要在AndroidManifest中注册APP_ID和APP_PID值
        AppConnect.getInstance(instance);
        // 预加载自定义广告内容（仅在使用了自定义广告、抽屉广告或迷你广告的情况，才需要添加）
        AppConnect.getInstance(instance).initAdInfo();
    }

    void welcome_tv_title() {
        //startActivity(MainActivity_.class);
        //startActivity(ExpandableListViewActivity.class);
        //startActivity(SwipeListViewActivity.class);
        //finish();
    }

}
