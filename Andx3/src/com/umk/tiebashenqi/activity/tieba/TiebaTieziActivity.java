package com.umk.tiebashenqi.activity.tieba;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.google.gson.internal.LinkedTreeMap;
import com.googlecode.androidannotations.annotations.*;
import com.lidroid.xutils.util.LogUtils;
import com.umk.andx3.api.Api;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.andx3.view.dialog.SimpleProgressDialog;
import com.umk.tiebashenqi.R;
import com.umk.tiebashenqi.adapter.TiebaTieziAdapter;
import com.umk.tiebashenqi.api.FavoriteTieziApi;
import com.umk.tiebashenqi.config.SystemConfig;
import com.umk.tiebashenqi.entity.Tieba;
import com.umk.tiebashenqi.entity.Tiezi;
import com.umk.tiebashenqi.entity.TieziPicture;
import com.umk.tiebashenqi.lpi.FavoriteTieziLpi;
import com.umk.tiebashenqi.lpi.TiebaLpi;
import com.umk.tiebashenqi.lpi.TieziLpi;
import com.umk.tiebashenqi.util.TiebaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@NoTitle
@EActivity(R.layout.activity_tieba_tiezi)
public class TiebaTieziActivity extends BaseActivity {

    public static Context instance = null;
    public static String intentTiebaId = "tiebaId";


    @ViewById(R.id.tiezi_elv)
    ExpandableListView tiezi_elv;
    @ViewById(R.id.header_layout_rightview_container)
    LinearLayout header_layout_rightview_container;
    @ViewById(R.id.header_ib_right_imagebutton)
    ImageButton header_ib_right_imagebutton;
    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.header_stv_subtitle)
    ScrollingTextView header_stv_subtitle;


    TiebaTieziAdapter mAdapter = null;
    List<Tiezi> mGroup = new ArrayList<Tiezi>();
    List<List<TieziPicture>> mData = new ArrayList<List<TieziPicture>>();

    List<Tiezi> mFavoriteTiezi = new ArrayList<Tiezi>();

    Long tiebaId;
    Tieba tieba;

    @Api
    FavoriteTieziApi favoriteTieziApi;


    @AfterViews
    void init() {
        instance = this;
        initParam();
        initData();
        initView();
    }

    private void initParam() {
        //TODO:初始化参数
        tiebaId = getIntent().getLongExtra(intentTiebaId, -1L);
    }



    private void initData() {
        if(tiebaId != -1L) {

            TiebaLpi tiebaLpi = new TiebaLpi();
            tieba = tiebaLpi.find(instance, tiebaId);

            FavoriteTieziLpi favoriteTieziLpi = new FavoriteTieziLpi();
            mFavoriteTiezi = favoriteTieziLpi.findTiezi(instance, tieba.getId());

            new SimpleProgressDialog<Map<String, String>>(instance, "正在加载...") {
                @Override
                public Map<String, String> doInBackground() {
                    //设置主页
                    String homePage = SystemConfig.TIEBA_WAP_BASE_URL + tieba.getTheNameUrl();

                    //初始化帖子列表<标题,网址>
                    LinkedTreeMap<String, String> map = TiebaUtil.getHomePageHashMap(homePage);
                    return map;
                }

                @Override
                public void doInUiThread(final Map<String, String> map) {

                    if (map != null && map.get(TiebaUtil.TIEBA_EXCEPTION) != null) {
                        showCustomToast("网络正忙...请点击右上角刷新按钮");
                        LogUtils.e(map.get(TiebaUtil.TIEBA_EXCEPTION));
                    } else if (map != null) {
                        //TODO(OK):设置帖子
                        List<Tiezi> tieziList = new ArrayList<Tiezi>();
                        for(String url : map.keySet()){
                            Tiezi tiezi = new Tiezi();
                            tiezi.setUrl(url);
                            tiezi.setTiebaId(tieba.getId());
                            tiezi.setTheName(map.get(url));
                            mGroup.add(tiezi);
                            mData.add(new ArrayList<TieziPicture>());
                            tieziList.add(tiezi);
                        }
                        //TODO(OK):保存帖子到数据库
                        new TieziLpi().saveOrUpdate(instance, tieziList);

                        initList();

                    } else {
                        showCustomToast("这个贴吧是新吧哦，还没有帖子呢");
                    }

                }
            }.show();

        } else {
            showLongToast("贴吧加载出错");
        }
    }

    private void initView() {
        header_ib_right_imagebutton.setImageDrawable(getResources().getDrawable(R.drawable.btn_fresh));
        header_stv_title.setText(tieba.getTheName());
        header_stv_subtitle.setText("消耗的贴币可以通过点点点广告获得");
        header_stv_subtitle.setVisibility(View.VISIBLE);
        header_layout_rightview_container.setVisibility(View.VISIBLE);
    }

    private void initList() {
        mAdapter = new TiebaTieziAdapter(this, tiezi_elv, mGroup, mData);
        mAdapter.setFavoriteTiezi(mFavoriteTiezi);
        mAdapter.notifyDataSetChanged();
        tiezi_elv.setGroupIndicator(getResources().getDrawable(R.drawable.ic_expander));
        tiezi_elv.setAdapter(mAdapter);
    }

    /**
     * 返回
     * */
    @Click
    void header_layout_left_imagebuttonlayout() {
        finish();
    }

    /**
     * 刷新组
     * */
    @Click
    void header_layout_right_imagebuttonlayout() {
        mGroup.clear();
        mData.clear();
        initData();
        mAdapter.notifyDataSetChanged();
        showCustomToast("已刷新");
    }

}