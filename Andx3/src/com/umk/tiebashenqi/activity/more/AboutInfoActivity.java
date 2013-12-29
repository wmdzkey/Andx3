package com.umk.tiebashenqi.activity.more;

import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.*;
import com.umk.andx3.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.util.BlueToothUtil;

@NoTitle
@EActivity(R.layout.activity_about_info)
public class AboutInfoActivity extends BaseActivity {

    @ViewById(R.id.header_layout_right_imagebuttonlayout)
    LinearLayout header_layout_right_imagebuttonlayout;
    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.header_tv_subtitle)
    TextView header_tv_subtitle;

    @AfterViews
    void init() {
        initView();
    }

    private void initView() {
        header_stv_title.setText("关于");
        header_tv_subtitle.setText("");
        header_layout_right_imagebuttonlayout.setVisibility(View.GONE);
    }

    @Click
    void header_stv_title() {
        showCustomToast("关于");
    }

    /**
     *发送蓝牙安装包
     * */
    @Click
    void about_btn_bluetooth() {

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
