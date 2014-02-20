package com.umk.tiebashenqi.activity.more;

import android.content.Context;
import android.widget.LinearLayout;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.ViewById;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.R;

@NoTitle
@EActivity(R.layout.activity_about_version)
public class AboutVersionActivity extends BaseActivity {

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
        initView();
    }

    private void initView() {
        header_stv_title.setText("版本介绍");
    }

}
