package com.umk.tiebashenqi.activity.more;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;
import cn.waps.demo201.DemoApp;
import cn.waps.extend.AppWall;
import com.googlecode.androidannotations.annotations.*;
import com.smartybean.core.AbstractCallBack;

import com.umk.tiebashenqi.R;
import com.umk.andx3.api.Api;
import com.umk.andx3.api.ProgressCallBack;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.demo.activity.ExpandableListViewActivity;
import com.umk.demo.activity.SwipeListViewActivity;
import com.umk.tiebashenqi.activity.MeActivity;
import com.umk.tiebashenqi.api.FavoriteTieziApi;
import com.umk.tiebashenqi.api.SystemApi;
import com.umk.tiebashenqi.api.TiebaApi;
import com.umk.tiebashenqi.config.SystemAdapter;
import com.umk.tiebashenqi.entity.Tieba;
import com.umk.tiebashenqi.entity.User;
import com.umk.tiebashenqi.util.BlueToothUtil;

import java.util.List;

@NoTitle
@EActivity(R.layout.activity_more_new)
public class NewActivity extends BaseActivity {

    public static Context instance = null;

    @ViewById(R.id.header_layout_right_imagebuttonlayout)
    LinearLayout header_layout_right_imagebuttonlayout;
    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.header_stv_subtitle)
    ScrollingTextView header_stv_subtitle;

    @Api
    TiebaApi tiebaApi;
    @Api
    SystemApi systemApi;
    @Api
    FavoriteTieziApi favoriteTieziApi;

    @AfterViews
    void init() {
        instance = this;

        header_stv_title.setText("更多");
        header_layout_right_imagebuttonlayout.setVisibility(View.GONE);
    }

    /**
     *发送蓝牙安装包
     * */
    @Click
    void more_new_rl_option_bluetooth() {

        DialogInterface.OnClickListener leftClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                BlueToothUtil.sendApkFile(instance);
            }
        };

        DialogInterface.OnClickListener rightClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        };

        showAlertDialog("温馨提示",
                "发送安装包前请告之您的朋友打开蓝牙\n并设置可以被周围手机搜索到",
                "搜索并发送",leftClickListener,
                "我再想想",rightClickListener);
    }


    @Click
    void more_new_rl_option_picture() {
        startActivity(GalleryActivity_.class);
    }


    @Click
    void more_new_rl_option_fasttest() {
        tiebaApi.test(new AbstractCallBack<String>() {
            @Override
            public void call(String str) {
                showCustomToast(str);
            }
        });
    }
    @Click
    void more_new_rl_option_test() {
        showCustomToast("测试一下");
        //startActivity(ExpandableListViewActivity.class);
    }

    @Click
    void more_new_rl_option_ad_model() {
        startActivity(DemoApp.class);
    }

}