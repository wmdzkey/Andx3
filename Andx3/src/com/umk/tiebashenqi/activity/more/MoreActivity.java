package com.umk.tiebashenqi.activity.more;

import android.widget.TextView;
import com.googlecode.androidannotations.annotations.*;
import com.umk.andx3.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.tiebashenqi.activity.MainActivity_;

@NoTitle
@EActivity(R.layout.activity_more)
public class MoreActivity extends BaseActivity {

    @ViewById(R.id.tv_more_title)
    TextView tv_more_title;

    @AfterViews
    void init() {
    }

    @Click
    void tv_more_title() {
        showCustomToast("更多");
    }

}
