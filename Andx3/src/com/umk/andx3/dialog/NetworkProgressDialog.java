package com.umk.andx3.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;
import com.umk.andx3.R;

/**
 * @author Winnid
 * @Title: 为网络准备的进度条
 * @date 14-1-8
 * @Version V1.0
 */
public class NetworkProgressDialog  extends ProgressDialog {

    private Context mContext;
    private ImageView mFivIcon;
    private TextView mText;
    private String message;

    private CountDownTimer cdt;

    private boolean mTouchCancelable;
    private boolean mBackClickable;


    public NetworkProgressDialog(Context context, String text) {
        super(context);
        initDialog(context, text, false, false);
    }

    public NetworkProgressDialog(Context context, String text, boolean touchCancelable, boolean backClickable) {
        super(context);
        initDialog(context, text, touchCancelable, backClickable);
    }

    private void initDialog(Context context, String text, boolean touchCancelable, boolean backClickable) {
        mContext = context;
        message = text;
        mTouchCancelable = touchCancelable;
        mBackClickable = backClickable;
        setCanceledOnTouchOutside(mTouchCancelable);
        setCancelable(mBackClickable);

        initCountDownTimer();
    }

    private void initCountDownTimer() {
        cdt = new CountDownTimer(23000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                //替换图标,显示超时，请稍后再试，并且解开cancelable
                if(isShowing()) {

                    mText.setText("请求超时, 请稍后重试");

                    mTouchCancelable = true;
                    mBackClickable = true;
                    setCanceledOnTouchOutside(mTouchCancelable);
                    setCancelable(mBackClickable);
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_simple_progress_dialog);
        mFivIcon = (ImageView) findViewById(R.id.progress_iv_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) mFivIcon.getBackground();
        animationDrawable.start();
        mText = (TextView) findViewById(R.id.progress_tv_loading);
        mText.setText(message);
    }

    @Override
    public void show() {
        super.show();
        cdt.start();
    }


    public static Dialog show(Context context, String msg) {
        NetworkProgressDialog dialog = new NetworkProgressDialog(context, msg);
        dialog.show();
        return dialog;
    }
}
