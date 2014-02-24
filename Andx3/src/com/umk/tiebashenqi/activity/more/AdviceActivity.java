package com.umk.tiebashenqi.activity.more;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import cn.waps.demo201.DemoApp;
import com.googlecode.androidannotations.annotations.*;
import com.smartybean.core.AbstractCallBack;
import com.umk.andx3.api.Api;
import com.umk.andx3.api.ProgressCallBack;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.lib.config.Code;
import com.umk.andx3.util.AndroidInfoUtil;
import com.umk.andx3.util.AppInfoUtil;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.R;
import com.umk.tiebashenqi.api.AdviceApi;
import com.umk.tiebashenqi.api.FavoriteTieziApi;
import com.umk.tiebashenqi.api.SystemApi;
import com.umk.tiebashenqi.api.TiebaApi;
import com.umk.tiebashenqi.config.SystemAdapter;
import com.umk.tiebashenqi.config.SystemConfig;
import com.umk.tiebashenqi.entity.Advice;
import com.umk.tiebashenqi.entity.User;
import com.umk.tiebashenqi.util.BlueToothUtil;

@NoTitle
@EActivity(R.layout.activity_more_advice)
public class AdviceActivity extends BaseActivity {

    public static Context instance = null;

    @ViewById(R.id.header_layout_right_imagebuttonlayout)
    LinearLayout header_layout_right_imagebuttonlayout;
    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.header_stv_subtitle)
    ScrollingTextView header_stv_subtitle;

    @ViewById(R.id.more_advice_et_advice)
    EditText et_advise_the_name;
    @ViewById(R.id.more_advice_et_user_name)
    EditText et_advise_user_name;
    @ViewById(R.id.more_advice_et_user_contact)
    EditText et_advise_user_contact;

    @ViewById(R.id.btn_single)
    Button btn_single;

    @Api
    AdviceApi adviceApi;

    @AfterViews
    void init() {
        instance = this;
        header_stv_title.setText("意见反馈");
        header_layout_right_imagebuttonlayout.setVisibility(View.GONE);
        btn_single.setText("发送");
    }

    @Click
    void header_layout_left_imagebuttonlayout() {
        finish();
    }

    @Click
    void btn_single() {

        if (et_advise_the_name.getText() == null || et_advise_the_name.getText().toString().length() == 0) {
            showCustomToast("写点什么吧");
            return;
        }

        Advice advice = new Advice();
        advice.setTheName(et_advise_the_name.getText().toString());
        User user = SystemAdapter.getCurrentUser();
        if (user != null) advice.setUserId(user.getId());
        advice.setUserName(et_advise_user_name.getText().toString());
        advice.setUserContact(et_advise_user_contact.getText().toString());
        advice.setAppVersion(AppInfoUtil.getVersionName(instance));
        advice.setAndroidVersion(AndroidInfoUtil.getSysVersion());
        advice.setDeviceName(AndroidInfoUtil.getDevice());
        advice.setDeviceId(AndroidInfoUtil.getDeviceId(instance));
        adviceApi.add(advice, new ProgressCallBack<Integer>() {
            @Override
            protected Context getContext() {
                return instance;
            }

            @Override
            public void call(Integer code) {
                if (code != null) {
                    showCustomToast(Code.returnStateCode.getValue(code.intValue()));
                } else {
                    showCustomToast(Code.returnStateCode.getValue(Code.returnStateCode.ServerUnknown));
                }
            }
        });
    }

}