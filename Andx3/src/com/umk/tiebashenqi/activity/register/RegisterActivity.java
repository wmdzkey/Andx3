package com.umk.tiebashenqi.activity.register;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import com.googlecode.androidannotations.annotations.*;
import com.smartybean.android.http.HttpInterface;
import com.smartybean.core.AbstractCallBack;

import com.umk.andx3.dialog.FlippingAlertDialog;
import com.umk.tiebashenqi.R;
import com.umk.andx3.api.Api;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.dialog.FlippingProgressDialog;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.activity.MainActivity_;
import com.umk.tiebashenqi.activity.WelcomeActivity;
import com.umk.tiebashenqi.api.SystemApi;
import com.umk.tiebashenqi.config.SystemAdapter;
import com.umk.tiebashenqi.entity.User;

@NoTitle
@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity implements RegisterStep.onNextActionListener {

    public static Context instance = null;

    @ViewById(R.id.reg_vf_viewflipper)
    ViewFlipper mVfFlipper;

    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.header_stv_subtitle)
    ScrollingTextView header_stv_subtitle;

    @ViewById(R.id.header_layout_rightview_container)
    LinearLayout header_layout_rightview_container;

    @ViewById(R.id.reg_btn_previous)
    Button mBtnPrevious;
    @ViewById(R.id.reg_btn_next)
    Button mBtnNext;

    @Api
    SystemApi systemApi;

    private RegisterStep mCurrentStep;


    private StepPhone mStepPhone;
    private StepPassword mStepPassword;
    private StepBaseInfo mStepBaseInfo;

    private int mCurrentStepIndex = 1;

    @AfterViews
    void init() {
        instance = this;
        mVfFlipper.setDisplayedChild(0);
        header_stv_subtitle.setVisibility(View.VISIBLE);
        header_layout_rightview_container.setVisibility(View.INVISIBLE);
        mCurrentStep = initStep();
        mCurrentStep.setOnNextActionListener(this);
    }

    private RegisterStep initStep() {
        switch (mCurrentStepIndex) {
            case 1:
                if (mStepPhone == null) {
                    mStepPhone = new StepPhone(this, mVfFlipper.getChildAt(0));
                }
                header_stv_title.setText("注册新账号");
                header_stv_subtitle.setText("1/3");
                mBtnPrevious.setText("返    回");
                mBtnNext.setText("下一步");
                return mStepPhone;

            case 2:
                if (mStepPassword == null) {
                    mStepPassword = new StepPassword(this, mVfFlipper.getChildAt(1));
                }
                header_stv_title.setText("设置密码");
                header_stv_subtitle.setText("2/3");
                mBtnPrevious.setText("上一步");
                mBtnNext.setText("下一步");
                return mStepPassword;

            case 3:
                if (mStepBaseInfo == null) {
                    mStepBaseInfo = new StepBaseInfo(this, mVfFlipper.getChildAt(2));
                }
                header_stv_title.setText("填写基本资料");
                header_stv_subtitle.setText("3/3");
                mBtnPrevious.setText("上一步");
                mBtnNext.setText("注    册");
                return mStepBaseInfo;
        }
        return null;
    }


    private void showBackDialog() {
        showAlertDialog("提示","确认要放弃注册么?",
                "确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                },
                "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    @Override
    public void next() {
        reg_btn_next();
    }

    @Click
    void reg_btn_previous() {
        if (mCurrentStepIndex <= 1) {
            showBackDialog();
        } else {
            mCurrentStepIndex--;
            mCurrentStep = initStep();
            mCurrentStep.setOnNextActionListener(this);
            mVfFlipper.setInAnimation(this, R.anim.push_right_in);
            mVfFlipper.setOutAnimation(this, R.anim.push_right_out);
            mVfFlipper.showPrevious();
        }
    }

    @Click
    void reg_btn_next() {
        if (mCurrentStepIndex < 3) {
            if (mCurrentStep.validate()) {
                if (mCurrentStep.isChange()) {
                    mCurrentStep.doNext();
                } else {
                    mCurrentStepIndex++;
                    mCurrentStep = initStep();
                    mCurrentStep.setOnNextActionListener(this);
                    mVfFlipper.setInAnimation(this, R.anim.push_left_in);
                    mVfFlipper.setOutAnimation(this, R.anim.push_left_out);
                    mVfFlipper.showNext();
                }
            }
        } else {
            if (mCurrentStep.validate()) {
                regist();
            }
        }
    }

    private void regist() {
        showLoadingDialog("正在注册, 请稍后 ...");
        final User user = new User();
        user.setUsername(mStepPhone.getPhoneNumber());
        user.setPassword(mStepPassword.getPassword());
        user.setTheName(mStepBaseInfo.getTheName());
        user.setSex(mStepBaseInfo.getSex());
        systemApi.register(user, new AbstractCallBack<Integer>() {
            @Override
            public void call(Integer result) {
                if (result == SystemAdapter.REGISTER_FAULT_DEFAULT) {
                    showCustomToast("注册失败, 请稍后再试");
                } else if (result == SystemAdapter.REGISTER_FAULT_USERNAME_EXIST) {
                    showCustomToast("注册失败, 手机号已被注册");
                } else {
                    user.setId(result.longValue());
                    //记住用户并登陆
                    SystemAdapter.login(user);

                    dismissLoadingDialog();
                    Intent intent = new Intent(instance, MainActivity_.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    WelcomeActivity.close();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        reg_btn_previous();
    }

    @Override
    protected void showCustomToast(String text) {
        super.showCustomToast(text);
    }

    @Override
    protected FlippingAlertDialog showAlertDialog(String title, String message) {
        return super.showAlertDialog(title, message);
    }

    @Override
    protected void showLoadingDialog(String text) {
        super.showLoadingDialog(text);
    }

    @Override
    protected void dismissLoadingDialog() {
        super.dismissLoadingDialog();
    }

}
