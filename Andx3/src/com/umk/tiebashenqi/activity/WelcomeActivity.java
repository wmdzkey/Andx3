package com.umk.tiebashenqi.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import com.googlecode.androidannotations.annotations.*;
import com.umk.andx3.base.BaseActivity;
import com.umk.tiebashenqi.R;
import com.umk.tiebashenqi.activity.register.RegisterActivity_;
import com.umk.tiebashenqi.config.SystemAdapter;

@NoTitle
@EActivity(R.layout.activity_welcome)
public class WelcomeActivity extends BaseActivity {

    public static Context instance = null;
    Handler mHandler;

    @ViewById(R.id.welcome_btn_register)
    Button welcome_btn_register;
    @ViewById(R.id.welcome_btn_login)
    Button welcome_btn_login;
    @ViewById(R.id.welcome_btn_experience)
    Button welcome_btn_exp;

    @AfterViews
    void init() {
        instance = this;
        mHandler = new Handler();
        autoLogin();
    }

    private void autoLogin() {
        //TODO :当应用可以使用其它途径更改密码此种方法就不可用了
        if(SystemAdapter.autoLogin()) {
            welcome_btn_register.setVisibility(View.INVISIBLE);
            welcome_btn_login.setVisibility(View.INVISIBLE);
            welcome_btn_exp.setVisibility(View.INVISIBLE);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(instance, MainActivity_.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }
    }

    @Click
    void welcome_btn_register() {
        startActivity(RegisterActivity_.class);
    }

    @Click
    void welcome_btn_login() {
        startActivity(LoginActivity_.class);
    }

    @Click
    void welcome_btn_experience() {
        startActivity(MainActivity_.class);
        //startActivity(ExpandableListViewActivity.class);
        //startActivity(SwipeListViewActivity.class);
        //finish();
    }

    /**关闭欢迎界面*/
    public static void close() {
        if(instance != null) {
            ((Activity)instance).finish();
        }
    }
}
