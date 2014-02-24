package com.umk.tiebashenqi.activity.register;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.smartybean.android.http.HttpInterface;
import com.smartybean.core.AbstractCallBack;
import com.umk.andx3.dialog.WebDialog;
import com.umk.tiebashenqi.R;
import com.umk.tiebashenqi.api.SystemApi;
import com.umk.tiebashenqi.config.SystemConfig;
import com.umk.tiebashenqi.util.TempUtil;

public class StepPhone extends RegisterStep implements OnClickListener, TextWatcher {

	private EditText mEtPhone;
	private TextView mHtvNotice;
	private TextView mHtvNote;

	private String mPhone;
	private boolean mIsChange = true;

	private WebDialog mWebDialog;

	public StepPhone(RegisterActivity activity, View contentRootView) {
		super(activity, contentRootView);
	}

	public String getPhoneNumber() {
		return mPhone;
	}

	@Override
	public void initViews() {
		mEtPhone = (EditText) findViewById(R.id.reg_phone_et_phone);
		mHtvNotice = (TextView) findViewById(R.id.reg_phone_htv_notice);
		mHtvNote = (TextView) findViewById(R.id.reg_phone_htv_note);
        TempUtil.addHyperlinks(mHtvNote, 8, 16, this);
        mEtPhone.requestFocus();
	}

	@Override
	public void initEvents() {
		mEtPhone.addTextChangedListener(this);
	}


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mIsChange = true;
        if (s.toString().length() > 0) {
            mHtvNotice.setVisibility(View.VISIBLE);
            char[] chars = s.toString().toCharArray();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < chars.length; i++) {
                if (i % 4 == 2) {
                    buffer.append(chars[i] + "  ");
                } else {
                    buffer.append(chars[i]);
                }
            }
            mHtvNotice.setText(buffer.toString());
        } else {
            mHtvNotice.setVisibility(View.GONE);
        }
    }


	@Override
	public boolean validate() {
		mPhone = null;
		if (TempUtil.isNull(mEtPhone)) {
			showCustomToast("请填写手机号码");
			mEtPhone.requestFocus();
			return false;
		}
		String phone = mEtPhone.getText().toString().trim();
		if (TempUtil.matchPhone(phone)) {
			mPhone = phone;
			return true;
		}
		showCustomToast("手机号码格式不正确");
		mEtPhone.requestFocus();
		return false;
	}

	@Override
	public boolean isChange() {
		return mIsChange;
	}


    @Override
    public void doNext() {

        showLoadingDialog("正在验证手机号,请稍后...");
        SystemApi systemApi = HttpInterface.proxy(mContext, SystemApi.class);
        systemApi.checkUsername(mPhone, new AbstractCallBack<Boolean>() {
            @Override
            public void call(Boolean result) {
                dismissLoadingDialog();
                if (result) {
                    mIsChange = false;
                    mOnNextActionListener.next();
                } else {
                    showCustomToast("手机号码不可用或已被注册");
                }
            }
        });

    }


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			mWebDialog = new WebDialog(mContext, "用户协议");
			//TODO:网址要加密
			mWebDialog.show(SystemConfig.WEB_SERVER_URL_USER_EULA);
			break;
		}
	}

}
