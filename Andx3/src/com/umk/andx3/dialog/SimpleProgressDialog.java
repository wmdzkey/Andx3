package com.umk.andx3.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import com.umk.andx3.R;

/**
 * @author Winnid
 * @Title:带有回调函数的进度条
 * @Description: doInBackground表示方法处理过程<br>doInUiThread表示对于返回结果的处理调用
 * @date 14-1-8
 * @Version V1.0
 */
public abstract class SimpleProgressDialog<T>  extends ProgressDialog {

    private Context mContext;
    private ImageView mFivIcon;
    private TextView mText;
    private String message;
    private T result;

    private CountDownTimer cdt;
    private Handler mHandler;

    private boolean mTouchCancelable;
    private boolean mBackClickable;


    public SimpleProgressDialog(Context context, String text) {
        super(context);
        initDialog(context, text, false, false);
    }

    //点返回取消/点击隐藏到后台
    public SimpleProgressDialog(Context context, String text, boolean touchCancelable, boolean backClickable) {
        super(context);
        initDialog(context, text, touchCancelable, backClickable);
    }

    private void initDialog(Context context, String text, boolean touchCancelable, boolean backClickable) {
        mContext = context;
        message = text;
        mHandler = new Handler();
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

        //添加异步操作
        new Thread(new Runnable(){
            @Override
            public void run() {
                //加载数据
                result = doInBackground();
                //更新界面
                mHandler.post(new Runnable() {
                    public void run() {
                        doInUiThread(result);
                        dismiss();
                    }
                });
            }
        }).start();

        cdt.start();
    }

    public abstract T doInBackground();
    public abstract void doInUiThread(T result);

}
