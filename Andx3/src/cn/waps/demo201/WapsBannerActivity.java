package cn.waps.demo201;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import cn.waps.AppConnect;
import com.googlecode.androidannotations.annotations.*;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.R;

@NoTitle
@EActivity(R.layout.activity_waps_banner)
public class WapsBannerActivity extends BaseActivity {

    public static Context instance;
    public Handler mHandler;

    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;

    @ViewById(R.id.waps_ll_banner)
	LinearLayout waps_ll_banner;


	@AfterViews
    void init() {
        instance = this;
        mHandler = new Handler();
        header_stv_title.setText("友情链接");

        initAd();
    }

    @Background
    void initAd() {
        int adNum = 3;
        for (int i = 0; i < adNum; i++) {
            final int finalCopyI = i;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 互动广告调用方式
                    LinearLayout adLayout = new LinearLayout(instance);
                    adLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    AppConnect.getInstance(instance).showBannerAd(instance, adLayout);

                    waps_ll_banner.addView(adLayout, finalCopyI, param);
                }
            }, 1000);

        }

    }

    @Click
    void header_layout_left_imagebuttonlayout() {
        finish();
    }

}