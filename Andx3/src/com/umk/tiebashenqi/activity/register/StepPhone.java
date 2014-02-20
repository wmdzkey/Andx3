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
			//mWebDialog = new WebDialog(mContext, "用户协议");
			//TODO:网址要加密
			//mWebDialog.show("http://www.baidu.com");
            showAlertDialog("用户协议", "欢迎使用贴吧看图神器，请您仔细阅读以下条款，如果您对本协议的任何条款表示异议，您可以选择不使用本应用；进入本应用则意味着您将同意遵守本协议下全部规定，并完全服从于贴吧看图神器的统一管理。\n" +
                    "凡是注册用户和浏览用户均为贴吧看图神器用户（以下统称\"用户\"）。\n" +
                    "第1条 用户的个人信息受到保护，不接受任何个人或单位的查询。国家机关依法查询除外，用户的个人设置公开除外。\n" +
                    "第2条 用户的言行不得违反《计算机信息网络国际联网安全保护管理办法》、《互联网信息服务管理办法》、《互联网电子公告服务管理规定》、《维护互联网安全的决定》、《互联网新闻信息服务管理规定》等相关法律规定，不得在本应用发布、传播或以其它方式传送含有下列内容之一的信息：\n" +
                    "1.反对宪法所确定的基本原则的；" +
                    "2.危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；" +
                    "3.损害国家荣誉和利益的；" +
                    "4.煽动民族仇恨、民族歧视、破坏民族团结的；" +
                    "5.破坏国家宗教政策，宣扬邪教和封建迷信的；" +
                    "6.散布谣言，扰乱社会秩序，破坏社会稳定的；" +
                    "7.散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；" +
                    "8.侮辱或者诽谤他人，侵害他人合法权利的；" +
                    "9.煽动非法集会、结社、游行、示威、聚众扰乱社会秩序的；" +
                    "10.以非法民间组织名义活动的；" +
                    "11.含有虚假、有害、胁迫、侵害他人隐私、骚扰、侵害、中伤、粗俗、猥亵、或其它道德上令人反感的内容；\n" +
                    "12.含有中国法律、法规、规章、条例以及任何具有法律效力之规范所限制或禁止的其它内容的。\n" +
                    "第3条 贴吧由用户搜索获取，是用户对该吧名称所指事物、现象介绍、评论的平台。贴吧文章仅代表作者观点，与本应用无关。对于用户言论的真实性引发的全部责任，由用户自行承担，本应用不承担任何责任。\n" +
                    "第4条 用户之间因使用而产生或可能产生的任何纠纷和/或损失，由用户自行解决并承担相应的责任，与本应用无关。\n" +
                    "第5条 如因系统维护或升级等而需暂停服务时，将事先公告。若因硬件故障或其它不可抗力而导致暂停服务，于暂停服务期间造成的一切不便与损失，本应用不负任何责任。 由于调整导致信息丢失和/或其他结果的，本应用不承担任何责任。");
			break;
		}
	}

}
