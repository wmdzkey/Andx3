package com.umk.tiebashenqi.activity.favorite;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.google.gson.internal.LinkedTreeMap;
import com.googlecode.androidannotations.annotations.*;
import com.umk.tiebashenqi.R;
import com.umk.andx3.api.Api;
import com.umk.andx3.api.ProgressCallBack;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.lib.config.Code;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.andx3.view.dialog.SimpleProgressDialog;
import com.umk.tiebashenqi.adapter.FavoriteTieziAdapter;
import com.umk.tiebashenqi.adapter.TiebaTieziAdapter;
import com.umk.tiebashenqi.api.FavoriteTieziApi;
import com.umk.tiebashenqi.config.SystemAdapter;
import com.umk.tiebashenqi.config.SystemConfig;
import com.umk.tiebashenqi.entity.FavoriteTiezi;
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
@EActivity(R.layout.activity_favorite_tiezi)
public class FavoriteTieziActivity extends BaseActivity {

    public static Context instance = null;
    public static String intentTieba = "tieba";


    @ViewById(R.id.favorite_tiezi_elv)
    ExpandableListView tiezi_elv;
    @ViewById(R.id.header_layout_rightview_container)
    LinearLayout header_layout_rightview_container;
    @ViewById(R.id.header_ib_right_imagebutton)
    ImageButton header_ib_right_imagebutton;
    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.header_stv_subtitle)
    ScrollingTextView header_stv_subtitle;


    FavoriteTieziAdapter mAdapter = null;
    List<Tiezi> mGroup = new ArrayList<Tiezi>();
    List<List<TieziPicture>> mData = new ArrayList<List<TieziPicture>>();

    Tieba tieba;

    @Api
    FavoriteTieziApi favoriteTieziApi;

    @AfterViews
    void init() {
        instance = this;
        initParam();
        initView();
        initList();
        initData();
    }

    private void initParam() {
        tieba = (Tieba) getIntent().getSerializableExtra(intentTieba);
    }

    private void initView() {
        header_stv_title.setText(tieba.getTheName());
    }

    private void initList() {
        mAdapter = new FavoriteTieziAdapter(this, tiezi_elv, mGroup, mData);
        tiezi_elv.setGroupIndicator(getResources().getDrawable(R.drawable.ic_expander));
        tiezi_elv.setAdapter(mAdapter);
    }

    private void initData() {
        if(tieba != null) {

            favoriteTieziApi.findFavoriteTiezi(SystemAdapter.getCurrentUser().getId(), tieba.getId(), new ProgressCallBack<List<Tiezi>>() {
                @Override
                protected Context getContext() {
                    return instance;
                }

                @Override
                public void call(List<Tiezi> tieziList) {
                    List<FavoriteTiezi> favoriteTieziList = new ArrayList<FavoriteTiezi>();
                    for(Tiezi tiezi : tieziList){
                        FavoriteTiezi favoriteTiezi = new FavoriteTiezi();
                        favoriteTiezi.setState(Code.state.Normal);
                        favoriteTiezi.setTiebaId(tieba.getId());
                        favoriteTiezi.setTieziUrl(tiezi.getUrl());
                        favoriteTiezi.setUserId(SystemAdapter.getCurrentUser().getId());
                        favoriteTieziList.add(favoriteTiezi);
                    }

                    //保存帖子到数据库
                    TieziLpi tieziLpi = new TieziLpi();
                    tieziLpi.saveOrUpdate(instance, tieziList);
                    FavoriteTieziLpi favoriteTieziLpi = new FavoriteTieziLpi();
                    favoriteTieziLpi.saveOrUpdate(instance, favoriteTieziList);

                    List<Tiezi> tieziInDBList =favoriteTieziLpi.findTiezi(instance, tieba.getId());

                    for(Tiezi tiezi : tieziInDBList){
                        mGroup.add(tiezi);
                        mData.add(new ArrayList<TieziPicture>());
                    }

                    mAdapter.notifyDataSetChanged();

                }
            });

        } else {
            showLongToast("贴吧加载出错");
        }
    }


    /**返回*/
    @Click
    void header_layout_left_imagebuttonlayout() {
        finish();
    }

}