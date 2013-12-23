package com.umk.tiebashenqi.activity.more;

import android.content.DialogInterface;
import com.googlecode.androidannotations.annotations.*;
import com.umk.andx3.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.util.BlueToothUtil;

@NoTitle
@EActivity(R.layout.activity_more)
public class MoreActivity extends BaseActivity {

    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;

    @AfterViews
    void init() {
        header_stv_title.setText("更多");
    }

    @Click
    void header_stv_title() {
        showCustomToast("更多");
    }

    /**
     *发送蓝牙安装包
     * */
    @Click
    void more_rl_option_bluetooth() {

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
                "发送安装包前请告之您的朋友打开蓝牙，并设置可以被周围手机搜索到",
                "搜索并发送",leftClickListener,
                "我再想想",rightClickListener);
    }

}
