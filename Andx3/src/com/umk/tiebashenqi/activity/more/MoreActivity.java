package com.umk.tiebashenqi.activity.more;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.waps.extend.AppWall;
import com.googlecode.androidannotations.annotations.*;
import com.umk.andx3.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.util.BlueToothUtil;

@NoTitle
@EActivity(R.layout.activity_more)
public class MoreActivity extends BaseActivity {

    @ViewById(R.id.header_layout_right_imagebuttonlayout)
    LinearLayout header_layout_right_imagebuttonlayout;
    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.header_tv_subtitle)
    TextView header_tv_subtitle;

    @AfterViews
    void init() {
        header_stv_title.setText("更多");
        header_tv_subtitle.setText("");
        header_layout_right_imagebuttonlayout.setVisibility(View.GONE);
    }

    @Click
    void header_stv_title() {
        showCustomToast("更多");
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
