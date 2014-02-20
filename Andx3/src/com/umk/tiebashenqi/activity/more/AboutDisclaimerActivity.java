package com.umk.tiebashenqi.activity.more;

import android.content.Context;
import android.widget.LinearLayout;
import com.googlecode.androidannotations.annotations.*;
import com.umk.tiebashenqi.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;

@NoTitle
@EActivity(R.layout.activity_about_disclaimer)
public class AboutDisclaimerActivity extends BaseActivity {

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
        header_stv_title.setText("免责声明");
    }

}
