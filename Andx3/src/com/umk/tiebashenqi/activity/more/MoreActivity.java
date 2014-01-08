package com.umk.tiebashenqi.activity.more;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.waps.extend.AppWall;
import com.googlecode.androidannotations.annotations.*;
import com.smartybean.core.AbstractCallBack;
import com.umk.andx3.R;
import com.umk.andx3.api.Api;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.api.TiebaApi;

@NoTitle
@EActivity(R.layout.activity_more)
public class MoreActivity extends BaseActivity {

    @ViewById(R.id.header_layout_right_imagebuttonlayout)
    LinearLayout header_layout_right_imagebuttonlayout;
    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.header_tv_subtitle)
    TextView header_tv_subtitle;

    @Api
    TiebaApi tiebaApi;

    @AfterViews
    void init() {
        header_stv_title.setText("更多");
        header_tv_subtitle.setText("");
        header_layout_right_imagebuttonlayout.setVisibility(View.GONE);
    }

    @Click
    void more_rl_option_test() {
        tiebaApi.test(new AbstractCallBack<String>() {
            @Override
            public void call(String str) {
                showCustomToast(str);
            }
        });
    }

    @Click
    void more_rl_option_picture() {
        startActivity(GalleryActivity_.class);
    }

    @Click
    void more_rl_option_ad() {
        //获取全部自定义广告数据
        Intent appWallIntent = new Intent(this, AppWall.class);
        this.startActivity(appWallIntent);
    }
}
