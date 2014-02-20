package com.umk.tiebashenqi.activity.register;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.umk.tiebashenqi.R;
import com.umk.tiebashenqi.util.TempUtil;

public class StepPassword extends RegisterStep implements TextWatcher {

	private EditText mEtPwd;
	private EditText mEtRePwd;

    private String mPassword;

	private boolean mIsChange = true;

	public StepPassword(RegisterActivity activity, View contentRootView) {
		super(activity, contentRootView);
	}

    public String getPassword() {
        return mPassword;
    }

	@Override
	public void initViews() {
		mEtPwd = (EditText) findViewById(R.id.reg_pwd_et_pwd);
		mEtRePwd = (EditText) findViewById(R.id.reg_pwd_et_repwd);
        mEtPwd.requestFocus();
	}

	@Override
	public void initEvents() {
		mEtPwd.addTextChangedListener(this);
		mEtRePwd.addTextChangedListener(this);
	}


	@Override
	public boolean validate() {
		String pwd = null;
		String rePwd = null;
		if (TempUtil.isNull(mEtPwd)) {
			showCustomToast("请输入密码");
			mEtPwd.requestFocus();
			return false;
		} else {
			pwd = mEtPwd.getText().toString().trim();
			if (pwd.length() < 6) {
				showCustomToast("密码不能小于6位");
				mEtPwd.requestFocus();
				return false;
			}
		}
		if (TempUtil.isNull(mEtRePwd)) {
			showCustomToast("请重复输入一次密码");
			mEtRePwd.requestFocus();
			return false;
		} else {
			rePwd = mEtRePwd.getText().toString().trim();
			if (!pwd.equals(rePwd)) {
				showCustomToast("两次输入的密码不一致");
				mEtRePwd.requestFocus();
				return false;
			}
		}
        mPassword = pwd;
		return true;
	}

	@Override
	public boolean isChange() {
		return mIsChange;
	}

    @Override
    public void doNext() {
        mIsChange = false;
        mOnNextActionListener.next();
    }


	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		mIsChange = true;
	}

}
