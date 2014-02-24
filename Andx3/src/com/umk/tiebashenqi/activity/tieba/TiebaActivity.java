package com.umk.tiebashenqi.activity.tieba;

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
import com.smartybean.core.AbstractCallBack;
import com.umk.andx3.api.Api;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.lib.config.Code;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.andx3.view.dialog.SimpleProgressDialog;
import com.umk.tiebashenqi.R;
import com.umk.tiebashenqi.activity.MainActivity;
import com.umk.tiebashenqi.adapter.TiebaAdapter;
import com.umk.tiebashenqi.api.TiebaApi;
import com.umk.tiebashenqi.config.SystemConfig;
import com.umk.tiebashenqi.entity.Tieba;
import com.umk.tiebashenqi.lpi.TiebaLpi;
import com.umk.andx3.lib.util.ListMap;
import com.umk.tiebashenqi.util.TempUtil;
import com.umk.tiebashenqi.util.TiebaUtil;

import java.util.List;
import java.util.Map;

@NoTitle
@EActivity(R.layout.activity_tieba)
public class TiebaActivity extends BaseActivity {

    public static Context instance = null;

    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.tieba_slv)
    SwipeListView tieba_slv;

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
    @ViewById(R.id.header_layout_right_imagebuttonlayout)
    LinearLayout header_layout_right_imagebuttonlayout;

    TiebaAdapter tiebaAdapter;
    ListMap<Long, Tieba> tiebaListMap = new ListMap<Long, Tieba>();
    TiebaLpi tiebaLpi = new TiebaLpi();

    @Api
    TiebaApi tiebaApi;

    @AfterViews
    void init() {
        instance = this;

        initView();
        initData();
        initSlv();

    }

    private void initView() {
        header_stv_title.setText("贴吧");
        header_layout_title.setVisibility(View.GONE);
        header_layout_search.setVisibility(View.VISIBLE);
        header_layout_rightview_container.setVisibility(View.VISIBLE);
        header_ib_right_imagebutton.setImageResource(R.drawable.ic_btn_search);
        header_ib_right_imagebutton.setClickable(false);
    }


    private void initData() {
        List<Tieba> tiebaList = tiebaLpi.findAllByState(instance, Code.state.Normal);
        for (Tieba t : tiebaList) {
            tiebaListMap.put(t.getId(), t);
        }

        tiebaAdapter = new TiebaAdapter(this, tiebaListMap, tieba_slv);
        tieba_slv.setAdapter(tiebaAdapter);
        tieba_slv.setSwipeListViewListener(new SwipeListViewListener());
    }

    void initSlv() {
        tieba_slv.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
        tieba_slv.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
        tieba_slv.setOffsetLeft((mScreenWidth - 50) / 3 * 2 + 50);
        tieba_slv.setAnimationTime(0);
        tieba_slv.setSwipeOpenOnLongPress(false);
    }


    @Click
    void header_layout_right_imagebuttonlayout() {

        if(header_et_search.getText() == null || header_et_search.getText().toString().trim().equals("")) {
            showCustomToast("请输入你想查找的贴吧名");
            return;
        }
        //查找贴吧并加入数据库
        final String searchTiebaName = header_et_search.getText().toString();
        //转换为网络通用url
        final String searchTiebaNameUrl = TempUtil.convertChineseUrl(searchTiebaName);
        //解析主页看是否有贴子，如果有则加入数据库，如果无则提示贴吧不存在
        final String homePage = SystemConfig.TIEBA_TOUCH_BASE_URL + searchTiebaNameUrl;

        new SimpleProgressDialog<Map<String, String>>(instance, "正在加载中...") {

            @Override
            public Map<String, String> doInBackground() {
                return TiebaUtil.getHomePageName(homePage);
            }

            @Override
            public void doInUiThread(Map<String, String> result) {
                if(result != null && result.size() != 0) {
                    Tieba tieba = new Tieba();
                    tieba.setTheName(result.get(TiebaUtil.TIEBA_NAME) + "吧");
                    tieba.setTheNameUrl(TempUtil.convertChineseUrl(result.get(TiebaUtil.TIEBA_NAME)));
                    tieba.setLogoUrl(result.get(TiebaUtil.TIEBA_LOGO));
                    tieba.setState(Code.state.Normal);
                    tiebaApi.add(tieba, new AbstractCallBack<Tieba>() {
                        @Override
                        public void call(Tieba t) {
                            TiebaLpi tiebaLpi = new TiebaLpi();
                            tiebaLpi.saveOrUpdate(instance, t);

                            tiebaListMap.put(t.getId(), t);
                            tiebaAdapter.notifyDataSetChanged();
                            showCustomToast("已为您找到 : " + t.getTheName());
                            dismiss();
                        }
                    });
                } else {
                    showLongToast("没有听说过这个贴吧");
                }
                header_et_search.getText().clear();
            }
        }.setAutoDismiss(false).show();

    }


    class SwipeListViewListener extends BaseSwipeListViewListener {
        @Override
        public void onClickFrontView(int position) {
            super.onClickFrontView(position);
            //进入贴吧贴子列表
            Tieba tieba = tiebaListMap.get(position);
            Intent intent = new Intent(instance, TiebaTieziActivity_.class);
            intent.putExtra(TiebaTieziActivity.intentTiebaId, tieba.getId());
            startActivity(intent);
        }

        @Override
        public void onDismiss(int[] reverseSortedPositions) {
            //TODO:删除贴吧
            for (int position : reverseSortedPositions) {
                //TODO:更新数据库
                Tieba tieba = tiebaListMap.get(position);
                tieba.setState(Code.state.Delete);
                tiebaLpi.saveOrUpdate(instance, tieba);
                tiebaListMap.remove(position);
            }
            tiebaAdapter.notifyDataSetChanged();
        }

        @Override
        public void onStartOpen(int position, int action, boolean right) {
            tieba_slv.closeOpenedItems();
            super.onStartOpen(position, action, right);
        }
    }

    @Override
    public void onBackPressed() {
        MainActivity.backPressed();
    }
}