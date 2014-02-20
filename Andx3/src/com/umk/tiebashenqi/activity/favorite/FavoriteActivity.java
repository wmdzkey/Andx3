package com.umk.tiebashenqi.activity.favorite;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.fortysevendeg.android.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.android.swipelistview.SwipeListView;
import com.googlecode.androidannotations.annotations.*;
import com.umk.tiebashenqi.R;
import com.umk.andx3.api.Api;
import com.umk.andx3.api.ProgressCallBack;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.adapter.TiebaAdapter;
import com.umk.tiebashenqi.api.FavoriteTieziApi;
import com.umk.tiebashenqi.api.TiebaApi;
import com.umk.tiebashenqi.config.SystemAdapter;
import com.umk.tiebashenqi.entity.Tieba;
import com.umk.andx3.lib.util.ListMap;

import java.util.List;

@NoTitle
@EActivity(R.layout.activity_favorite)
public class FavoriteActivity extends BaseActivity {

    public static Context instance = null;

    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.favorite_slv)
    SwipeListView favorite_slv;

    @ViewById(R.id.header_layout_title)
    LinearLayout header_layout_title;
    @ViewById(R.id.header_layout_search)
    RelativeLayout header_layout_search;

    @ViewById(R.id.header_et_search)
    EditText header_et_search;
    @ViewById(R.id.header_layout_rightview_container)
    LinearLayout header_layout_rightview_container;
    @ViewById(R.id.header_ib_right_imagebutton)
    ImageButton header_ib_right_imagebutton;

    TiebaAdapter tiebaAdapter;
    ListMap<Long, Tieba> tiebaListMap = new ListMap<Long, Tieba>();

    @Api
    TiebaApi tiebaApi;
    @Api
    FavoriteTieziApi favoriteTieziApi;

    @AfterViews
    void init() {
        instance = this;

        initView();
        initData();
        initSlv();
    }

    private void initView() {
        header_stv_title.setText("收藏的贴吧");
        header_layout_title.setVisibility(View.VISIBLE);
    }


    private void initData() {

        tiebaAdapter = new TiebaAdapter(this, tiebaListMap, favorite_slv);
        favorite_slv.setAdapter(tiebaAdapter);
        favorite_slv.setSwipeListViewListener(new SwipeListViewListener());

        if(SystemAdapter.getCurrentUser() != null) {
            //读取网络
            favoriteTieziApi.findFavoriteTieba(SystemAdapter.getCurrentUser().getId(), new ProgressCallBack<List<Tieba>>() {
                @Override
                protected Context getContext() {
                    return instance;
                }

                @Override
                public void call(List<Tieba> tiebas) {
                    for (Tieba t : tiebas) {
                        tiebaListMap.put(t.getId(), t);
                        tiebaAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            showCustomToast("加载出错，请注销后重新登录");
        }
    }

    void initSlv() {
        favorite_slv.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
        favorite_slv.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
        favorite_slv.setOffsetLeft((mScreenWidth - 50) / 3 * 2 + 50);
        favorite_slv.setAnimationTime(0);
        favorite_slv.setSwipeOpenOnLongPress(false);
    }


    class SwipeListViewListener extends BaseSwipeListViewListener {
        @Override
        public void onClickFrontView(int position) {
            super.onClickFrontView(position);
            //进入贴吧收藏的贴子列表
            Tieba tieba = tiebaListMap.get(position);
            Intent intent = new Intent(instance, FavoriteTieziActivity_.class);
            intent.putExtra(FavoriteTieziActivity.intentTieba, tieba);
            startActivity(intent);
        }

        @Override
        public void onDismiss(int[] reverseSortedPositions) {
        }

        @Override
        public void onStartOpen(int position, int action, boolean right) {
            favorite_slv.closeOpenedItems();
            super.onStartOpen(position, action, right);
        }
    }

    /**返回*/
    @Click
    void header_layout_left_imagebuttonlayout() {
        finish();
    }

}