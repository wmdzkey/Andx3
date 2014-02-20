package com.umk.tiebashenqi.activity.register;

import android.content.Context;
import android.view.View;

public abstract class RegisterStep {

	protected RegisterActivity mActivity;
	protected Context mContext;
	private View mContentRootView;
	protected onNextActionListener mOnNextActionListener;

	public RegisterStep(RegisterActivity activity, View contentRootView) {
		mActivity = activity;
		mContext = activity;
		mContentRootView = contentRootView;
		initViews();
		initEvents();
	}

    public View findViewById(int id) {
        return mContentRootView.findViewById(id);
    }


	public abstract void initViews();

	public abstract void initEvents();



	public abstract boolean validate();

	public abstract boolean isChange();


	public void doPrevious() {}

	public void doNext() {}

	public void nextAnimation() {}

	public void preAnimation() {}


	protected void showCustomToast(String text) {
		mActivity.showCustomToast(text);
	}

    protected void showAlertDialog(String title, String message) {
        mActivity.showAlertDialog(title, message);
    }

	protected void showLoadingDialog(String text) {
		mActivity.showLoadingDialog(text);
	}

	protected void dismissLoadingDialog() {
		mActivity.dismissLoadingDialog();
	}

	public void setOnNextActionListener(onNextActionListener listener) {
		mOnNextActionListener = listener;
	}

	public interface onNextActionListener {
		void next();
	}
}
