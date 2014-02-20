package com.umk.tiebashenqi.activity;


import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.*;
import com.smartybean.core.AbstractCallBack;
import com.umk.tiebashenqi.R;
import com.umk.andx3.api.Api;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.api.SystemApi;
import com.umk.tiebashenqi.config.SystemAdapter;
import com.umk.tiebashenqi.entity.User;
import com.umk.tiebashenqi.util.TempUtil;

import java.util.regex.Pattern;

@NoTitle
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {


    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;

    @ViewById(R.id.login_btn_back)
    Button mBtnBack;
    @ViewById(R.id.login_btn_login)
    Button mBtnLogin;

    @ViewById(R.id.login_et_account)
    EditText mEtAccount;
    @ViewById(R.id.login_et_pwd)
    EditText mEtPwd;

    @ViewById(R.id.login_tv_forgotpassword)
    TextView mTvForgotPassword;

    private String mAccount;
    private String mPassword;

    @Api
    SystemApi systemApi;


    @AfterViews
    void init() {
        header_stv_title.setText("登陆");
        TempUtil.addUnderlineText(this, mTvForgotPassword, 0,
                mTvForgotPassword.getText().length());
    }

    @Click
    void login_btn_back() {
        finish();
    }

    @Click
    void login_btn_login() {
        login();
    }

    @Click
    void login_tv_forgotpassword() {
        //TODO : startActivity(FindPwdTabsActivity.class);
        showCustomToast("不是告诉你了，没办法找回");
    }



    private boolean validateAccount() {
        mAccount = null;
        if (TempUtil.isNull(mEtAccount)) {
            showCustomToast("请输入手机号码");
            mEtAccount.requestFocus();
            return false;
        }
        String account = mEtAccount.getText().toString().trim();
        if (TempUtil.matchPhone(account)) {
            if (account.length() < 3) {
                showCustomToast("账号格式不正确");
                mEtAccount.requestFocus();
                return false;
            }
            if (Pattern.compile("(\\d{3,})|(\\+\\d{3,})").matcher(account)
                    .matches()) {
                mAccount = account;
                return true;
            }
        }
        showCustomToast("账号格式不正确");
        mEtAccount.requestFocus();
        return false;
    }

    private boolean validatePwd() {
        mPassword = null;
        String pwd = mEtPwd.getText().toString().trim();
        if (pwd.length() < 4) {
            showCustomToast("密码不能小于4位");
            mEtPwd.requestFocus();
            return false;
        }
        if (pwd.length() > 16) {
            showCustomToast("密码不能大于16位");
            mEtPwd.requestFocus();
            return false;
        }
        mPassword = pwd;
        return true;
    }


    private void login() {
        if ((!validateAccount()) || (!validatePwd())) {
            return;
        }
        showLoadingDialog("正在登录,请稍后...");
        systemApi.login(mAccount, mPassword, new AbstractCallBack<User>() {
            @Override
            public void call(User user) {
                if(user != null) {
                    if(user.getId().intValue() == SystemAdapter.LOGIN_STATE_FAULT_DEFAULT) {
                        showCustomToast("账号或密码错误,请检查是否输入正确");
                    } else if(user.getId().intValue() == SystemAdapter.LOGIN_STATE_FAULT_LOCK) {
                        showCustomToast("该用户已被服务器冻结");
                    } else {
                        showCustomToast("恭喜你，登陆成功");
                        //记住用户信息并登陆
                        SystemAdapter.login(user);
                        WelcomeActivity.close();
                        startActivity(MainActivity_.class);
                        finish();
                    }
                } else {
                    showCustomToast("账号或密码错误,请检查是否输入正确");
                }
                dismissLoadingDialog();
            }
        });
    }


}
