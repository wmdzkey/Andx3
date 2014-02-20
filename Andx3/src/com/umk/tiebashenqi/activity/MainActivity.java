package com.umk.tiebashenqi.activity;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import cn.waps.extend.QuitPopAd;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.ViewById;
import com.umk.tiebashenqi.R;
import com.umk.tiebashenqi.activity.more.MoreActivity_;
import com.umk.tiebashenqi.activity.tieba.TiebaActivity_;
import com.umk.tiebashenqi.config.SystemAdapter;


@NoTitle
@EActivity(R.layout.activity_main)
public class MainActivity extends ActivityGroup {

    public static Context instance = null;
    public static LayoutInflater inflater;

    @ViewById(R.id.tabhost)
    TabHost mTabHost;

    //记录返回键点击次数
    private static int backPressNum = 0;

    @AfterViews
    void init() {
        instance = this;
        inflater = LayoutInflater.from(instance);
        initTabs();
    }

    private void initTabs() {
        // 如果没有继承TabActivity时，通过该种方法加载启动tabHost
        mTabHost.setup(this.getLocalActivityManager());

        View tiebaView = inflater.inflate( R.layout.common_bottombar_tab_tieba, null);
        TabHost.TabSpec tiebaTabSpec = mTabHost.newTabSpec( TiebaActivity_.class.getName()).setIndicator(tiebaView);
        tiebaTabSpec.setContent(new Intent(MainActivity.this, TiebaActivity_.class));
        mTabHost.addTab(tiebaTabSpec);


        View meView = inflater.inflate( R.layout.common_bottombar_tab_me, null);
        TabHost.TabSpec meTabSpec = mTabHost.newTabSpec( MeActivity_.class.getName()).setIndicator(meView);
        meTabSpec.setContent(new Intent(MainActivity.this, MeActivity_.class));
        mTabHost.addTab(meTabSpec);

        View moreView = inflater.inflate( R.layout.common_bottombar_tab_more, null);
        TabHost.TabSpec moreTabSpec = mTabHost.newTabSpec( MoreActivity_.class.getName()).setIndicator(moreView);
        moreTabSpec.setContent(new Intent(MainActivity.this, MoreActivity_.class));
        mTabHost.addTab(moreTabSpec);
    }


    public static void backPressed() {

//        if(instance != null) {
//            backPressNum ++;
//            if(backPressNum % 2 != 0) {
//                BaseToast.showToast(instance, "再按一次，我就退出了");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        backPressNum = 0;
//                    }
//                }, 2000);
//            } else {
//                AppConnect.getInstance(instance).close();//关闭广告服务
//                System.exit(0);
//            }
//        }

        if(instance != null && SystemAdapter.getCurrentUser() != null) {
            // 调用退屏广告
            QuitPopAd.getInstance().show(instance);
        } else {
            close();
        }

    }

    /**关闭主界面*/
    public static void close() {
        if(instance != null) {
            ((Activity)instance).finish();
        }
    }

}
