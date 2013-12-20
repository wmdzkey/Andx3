package com.umk.tiebashenqi.activity;

import android.widget.TextView;
import com.googlecode.androidannotations.annotations.*;
import com.umk.andx3.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.demo.activity.ExpandableListViewActivity;
import com.umk.demo.activity.SwipeListViewActivity;

@NoTitle
@EActivity(R.layout.activity_welcome)
public class WelcomeActivity extends BaseActivity {

    @ViewById(R.id.tv_welcome_title)
    TextView tv_welcome_title;

    @AfterViews
    void init() {
    }

    @Click
    void tv_welcome_title() {
        showCustomToast("Andx3");
        startActivity(MainActivity_.class);

        //startActivity(ExpandableListViewActivity.class);
        //startActivity(SwipeListViewActivity.class);
        finish();
    }

}
