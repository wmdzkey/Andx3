package com.umk.tiebashenqi.activity.more;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.waps.demo201.WapsBannerActivity_;
import com.googlecode.androidannotations.annotations.*;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.util.AppInfoUtil;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.R;
import com.umk.tiebashenqi.activity.MainActivity;

@NoTitle
@EActivity(R.layout.activity_more)
public class MoreActivity extends BaseActivity {

    public static Context instance = null;

    @ViewById(R.id.header_layout_right_imagebuttonlayout)
    LinearLayout header_layout_right_imagebuttonlayout;
    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.header_stv_subtitle)
    ScrollingTextView header_stv_subtitle;
    @ViewById(R.id.more_tv_version)
    TextView about_tv_version;

    @AfterViews
    void init() {
        instance = this;
        initView();
    }

    private void initView() {
        header_stv_title.setText("更多");
        header_layout_right_imagebuttonlayout.setVisibility(View.GONE);
        about_tv_version.setText("版本 : " + AppInfoUtil.getVersionName(instance));
    }

    @Click
    void more_btn_more_new() {
        startActivity(NewActivity_.class);
    }


    @Click
    void more_btn_advice() {
        startActivity(AdviceActivity_.class);
    }


    @Click
    void more_btn_about() {
        startActivity(AboutActivity_.class);
    }

    @Override
    public void onBackPressed() {
        MainActivity.backPressed();
    }
}
