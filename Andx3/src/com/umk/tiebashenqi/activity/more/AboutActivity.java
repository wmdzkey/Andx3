package com.umk.tiebashenqi.activity.more;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import cn.waps.extend.AppWall;
import com.googlecode.androidannotations.annotations.*;
import com.umk.tiebashenqi.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.activity.MainActivity;
import com.umk.tiebashenqi.activity.WelcomeActivity_;
import com.umk.tiebashenqi.config.SystemAdapter;

@NoTitle
@EActivity(R.layout.activity_about)
public class AboutActivity extends BaseActivity {

    public static Context instance = null;

    @ViewById(R.id.header_layout_right_imagebuttonlayout)
    LinearLayout header_layout_right_imagebuttonlayout;
    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.header_stv_subtitle)
    ScrollingTextView header_stv_subtitle;


    @AfterViews
    void init() {
        instance = this;
        header_stv_title.setText("关于");
        header_layout_right_imagebuttonlayout.setVisibility(View.GONE);
    }

    @Click
    void about_rl_option_ad() {
        //获取全部自定义广告数据
        Intent appWallIntent = new Intent(this, AppWall.class);
        startActivity(appWallIntent);
    }

    @Click
    void about_rl_option_version() {
        startActivity(AboutVersionActivity_.class);
    }

    @Click
    void about_rl_option_disclaimer() {
        startActivity(AboutDisclaimerActivity_.class);
    }

    @Click
    void about_btn_logout() {
        SystemAdapter.logout();
        startActivity(WelcomeActivity_.class);
        MainActivity.close();
        finish();
    }
}
