package com.umk.tiebashenqi.activity.register;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.umk.tiebashenqi.R;
import com.umk.andx3.lib.config.Code;
import com.umk.tiebashenqi.util.TempUtil;

public class StepBaseInfo extends RegisterStep implements TextWatcher, OnCheckedChangeListener {

	private EditText mEtName;
	private RadioGroup mRgGender;
	private RadioButton mRbMale;
	private RadioButton mRbFemale;

    private String mTheName;
    private Integer mSex;

	private boolean mIsChange = true;

	public StepBaseInfo(RegisterActivity activity, View contentRootView) {
		super(activity, contentRootView);
	}

    public String getTheName() {
        return mTheName;
    }

    public Integer getSex() {
        return mSex;
    }

    @Override
	public void initViews() {
		mEtName = (EditText) findViewById(R.id.reg_baseinfo_et_name);
		mRgGender = (RadioGroup) findViewById(R.id.reg_baseinfo_rg_gender);
		mRbMale = (RadioButton) findViewById(R.id.reg_baseinfo_rb_male);
		mRbFemale = (RadioButton) findViewById(R.id.reg_baseinfo_rb_female);
        mEtName.requestFocus();
	}

	@Override
	public void initEvents() {
		mEtName.addTextChangedListener(this);
		mRgGender.setOnCheckedChangeListener(this);
	}

	@Override
	public boolean validate() {
		if (TempUtil.isNull(mEtName)) {
			showCustomToast("请输入用户名");
			mEtName.requestFocus();
			return false;
		}
		if (mRgGender.getCheckedRadioButtonId() < 0) {
			showCustomToast("请选择性别");
			return false;
		}


        if(mRgGender.getCheckedRadioButtonId() == R.id.reg_baseinfo_rb_male) {
            mSex = Code.sex.Boy;
        } else if(mRgGender.getCheckedRadioButtonId() == R.id.reg_baseinfo_rb_female){
            mSex = Code.sex.Girl;
        }
        mTheName = mEtName.getText().toString();

		return true;
	}

	@Override
	public boolean isChange() {
		return mIsChange;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.reg_baseinfo_rb_male:
			mRbMale.setChecked(true);
			break;

		case R.id.reg_baseinfo_rb_female:
			mRbFemale.setChecked(true);
			break;
		}
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		mIsChange = true;
	}

}
