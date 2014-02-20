package com.umk.andx3.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.*;
import android.widget.Button;

import com.umk.tiebashenqi.R;
import com.umk.andx3.base.BaseToast;
import com.umk.andx3.util.NetWorkUtil;
import com.umk.andx3.view.ScrollingTextView;

/**
 * @author Winnid
 * @Title: 显示网页信息对话框
 * @date 14-1-10
 * @Version V1.0
 */
public class WebDialog extends ProgressDialog {

    private Context mContext;
    private NetWorkUtil mNetWorkUtils;

    private WebView mWebView;
    private View mLoadingView;
    private ScrollingTextView mTextView;
    private Button mButton;

    private String title;

    private boolean mTouchCancelable;
    private boolean mBackClickable;


    public WebDialog(Context context, String text) {
        super(context);
        initDialog(context, text, false, true);
    }

    public WebDialog(Context context, String text, boolean touchCancelable, boolean backClickable) {
        super(context);
        initDialog(context, text, touchCancelable, backClickable);
    }

    private void initDialog(Context context, String text, boolean touchCancelable, boolean backClickable) {
        mContext = context;
        title = text;
        mTouchCancelable = touchCancelable;
        mBackClickable = backClickable;
        setCanceledOnTouchOutside(mTouchCancelable);
        setCancelable(mBackClickable);
        mNetWorkUtils = new NetWorkUtil(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_dialog_web);
        mButton = (Button) findViewById(R.id.web_dialog_btn);
        mTextView = (ScrollingTextView) findViewById(R.id.header_stv_title);
        mLoadingView = findViewById(R.id.web_dialog_loading_indicator);
        mWebView = (WebView) findViewById(R.id.web_dialog_webview);

        mTextView.setText(title);
        mButton.setText("确定");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

        });
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) {
                handler.proceed();
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showProgress();
            }

            private void showProgress() {
                mLoadingView.setVisibility(View.VISIBLE);
            }

            private void dismissProgress() {
                mLoadingView.setVisibility(View.GONE);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissProgress();
            }
        });
    }

    public void show(String url) {
        super.show();
        loadUrl(url);
    }


    private void loadUrl(String url) {
        if (url == null) {
            urlError();
            return;
        }
        if (mNetWorkUtils.getConnectState() == NetWorkUtil.NetWorkState.NONE) {
            networkError();
            return;
        }
        mWebView.loadUrl(url);
        mWebView.getSettings().setLayoutAlgorithm(
                WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    }



    public static Dialog show(Context context, String title, String url) {
        WebDialog dialog = new WebDialog(context, title, false, true);
        dialog.show(url);
        return dialog;
    }


    private void urlError() {
        BaseToast.showToast(mContext, "网页地址错误,请检查");
    }

    private void networkError() {
        BaseToast.showToast(mContext, "当前网络不可用,请检查");
    }
}
