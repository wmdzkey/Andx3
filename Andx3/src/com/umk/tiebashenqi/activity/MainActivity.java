package com.umk.tiebashenqi.activity;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.ViewById;
import com.umk.andx3.R;
import com.umk.tiebashenqi.activity.more.AboutActivity_;
import com.umk.tiebashenqi.activity.tieba.TiebaActivity_;

@NoTitle
@EActivity(R.layout.activity_main)
public class MainActivity extends ActivityGroup {

    public static Context instance = null;
    public static LayoutInflater inflater;

    @ViewById(R.id.tabhost)
    TabHost mTabHost;


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
        TabHost.TabSpec albumTabSpec = mTabHost.newTabSpec( TiebaActivity_.class.getName()).setIndicator(tiebaView);
        albumTabSpec.setContent(new Intent(MainActivity.this, TiebaActivity_.class));
        mTabHost.addTab(albumTabSpec);

        View moreView = inflater.inflate( R.layout.common_bottombar_tab_more, null);
        TabHost.TabSpec moreTabSpec = mTabHost.newTabSpec( AboutActivity_.class.getName()).setIndicator(moreView);
        moreTabSpec.setContent(new Intent(MainActivity.this, AboutActivity_.class));
        mTabHost.addTab(moreTabSpec);
    }


    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }
}
