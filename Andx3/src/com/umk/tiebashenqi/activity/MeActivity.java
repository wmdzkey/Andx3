package com.umk.tiebashenqi.activity;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;
import com.googlecode.androidannotations.annotations.*;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.R;
import com.umk.tiebashenqi.activity.favorite.FavoriteActivity_;
import com.umk.tiebashenqi.config.SystemAdapter;
import com.umk.tiebashenqi.entity.User;

@NoTitle
@EActivity(R.layout.activity_me)
public class MeActivity extends BaseActivity implements UpdatePointsNotifier {

    public static Context instance = null;

    @ViewById(R.id.header_layout_right_imagebuttonlayout)
    LinearLayout header_layout_right_imagebuttonlayout;
    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.header_stv_subtitle)
    ScrollingTextView header_stv_subtitle;

    @ViewById(R.id.me_tv_option_username)
    TextView me_tv_option_username;
    @ViewById(R.id.me_tv_option_thename)
    TextView me_tv_option_thename;
    @ViewById(R.id.me_tv_option_money)
    TextView me_tv_option_money;

    User user;

    @AfterViews
    void init() {
        instance = this;

        header_stv_title.setText("个人中心");
        header_layout_right_imagebuttonlayout.setVisibility(View.GONE);

        initData();

        initMoney();
    }

    private void initMoney() {
        if(SystemAdapter.getCurrentUser() != null) {
            AppConnect.getInstance(instance).getPoints(this);
        } else {
            showCustomToast("想要使用更多功能就先注册吧");
            me_tv_option_money.setText("游客无法使用贴币功能");
        }
    }


    private void initData() {

        user = SystemAdapter.getCurrentUser();
        if(user != null) {
            me_tv_option_username.setText(user.getUsername());
            me_tv_option_thename.setText(user.getTheName());
        } else {
            //按游客处理
            me_tv_option_username.setText("游客");
            me_tv_option_thename.setText("游客");
        }

    }

    @Click
    void me_rl_option_money() {
        //TODO:刷新贴币
        showCustomToast("正在刷新贴币");
        initMoney();
    }


    @Click
    void more_new_rl_option_favorite() {
        if(user != null) {
            startActivity(FavoriteActivity_.class);
        } else {
            showCustomToast("想要使用更多功能就先注册吧");
        }
    }

    @Click
    void me_btn_ad() {
        //显示推荐列表（综合）
        AppConnect.getInstance(this).showOffers(this);
    }

    @Click
    void me_btn_ad_add() {
        //奖励虚拟货币
        AppConnect.getInstance(this).awardPoints(50, this);
    }

    @Click
    void me_btn_ad_sub() {
        //消费虚拟货币.
        AppConnect.getInstance(this).spendPoints(50, this);
    }

    @Override
    public void onBackPressed() {
        MainActivity.backPressed();
    }

    @Override
    public void getUpdatePoints(final String currencyName, final int pointTotal) {//获取成功
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showCustomToast("刷新完成");
                updateMoney(currencyName, pointTotal);
            }
        });
    }

    @Override
    public void getUpdatePointsFailed(String error) {//获取失败
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                me_tv_option_money.setText("贴币获取失败");
            }
        });
    }

    public static void updateMoney(String currencyName, int pointTotal) {
        if(instance != null) {
            ((MeActivity)instance).me_tv_option_money.setText(pointTotal + " " + currencyName);
        }
    }
}