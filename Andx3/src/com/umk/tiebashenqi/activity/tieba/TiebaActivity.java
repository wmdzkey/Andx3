package com.umk.tiebashenqi.activity.tieba;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import com.fortysevendeg.android.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.android.swipelistview.SwipeListView;
import com.google.gson.internal.LinkedTreeMap;
import com.googlecode.androidannotations.annotations.*;
import com.umk.andx3.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.adapter.TiebaAdapter;
import com.umk.tiebashenqi.config.Code;
import com.umk.tiebashenqi.entity.Tieba;
import com.umk.tiebashenqi.lpi.TiebaLpi;
import com.umk.tiebashenqi.util.TempUtil;
import com.umk.tiebashenqi.util.TiebaUtil;

import java.util.ArrayList;
import java.util.List;

@NoTitle
@EActivity(R.layout.activity_tieba)
public class TiebaActivity extends BaseActivity {

    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.tieba_slv)
    SwipeListView tieba_slv;
    @ViewById(R.id.tieba_tv_test)
    TextView tieba_tv_test;

    @ViewById(R.id.header_layout_title)
    LinearLayout header_layout_title;
    @ViewById(R.id.header_layout_search)
    RelativeLayout header_layout_search;

    @ViewById(R.id.header_et_search)
    EditText header_et_search;
    @ViewById(R.id.header_ib_right_imagebutton)
    ImageButton header_ib_right_imagebutton;
    @ViewById(R.id.header_layout_right_imagebuttonlayout)
    LinearLayout header_layout_right_imagebuttonlayout;

    TiebaAdapter tiebaAdapter;
    List<Tieba> tiebaList = new ArrayList<Tieba>();
    TiebaLpi tiebaLpi = new TiebaLpi();
    LinkedTreeMap<String, String> newMap = new LinkedTreeMap<String, String>();

    @AfterViews
    void init() {
        initView();
        initData();
        initSlv();
        initDebug(false);

    }

    private void initView() {
        header_stv_title.setText("贴吧");
        header_layout_title.setVisibility(View.GONE);
        header_layout_search.setVisibility(View.VISIBLE);
        header_ib_right_imagebutton.setImageResource(R.drawable.ic_btn_search);
        header_ib_right_imagebutton.setClickable(false);
    }


    private void initData() {
        tiebaList = tiebaLpi.findAllByState(instance, Code.State.Normal);
        tiebaAdapter = new TiebaAdapter(this, R.layout.list_item_tieba, tiebaList, tieba_slv);
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


    private void initDebug(boolean b) {
        if(b) {
            tieba_tv_test.setVisibility(View.VISIBLE);
            tieba_tv_test.setText("http://tieba.baidu.com/f?ie=utf-8&kw=^21313asdasd");
        }
    }

    @Click
    void header_layout_right_imagebuttonlayout() {
        if(header_et_search.getText() == null || header_et_search.getText().toString().trim().equals("")) {
            return;
        }
        //查找贴吧并加入数据库
        String searchTiebaName = header_et_search.getText().toString();
        //转换为网络通用url
        String searchTiebaNameUrl = TempUtil.convertChineseUrl(searchTiebaName);
        //解析主页看是否有贴子，如果有则加入数据库，如果无则提示贴吧不存在
        String homePage = "http://tieba.baidu.com/f?ie=utf-8&kw=" + searchTiebaNameUrl;
        newMap = TiebaUtil.getHomePageHashMap(homePage);
        if(newMap != null & newMap.size() != 0) {
            Tieba tieba = new Tieba();
            tieba.setTheName(searchTiebaName);
            tieba.setTheNameUrl(searchTiebaNameUrl);
            tieba.setState(Code.State.Normal);
            TiebaLpi tiebaLpi = new TiebaLpi();
            tiebaLpi.saveOrUpdate(instance, tieba);
            tiebaList.add(tieba);
            tiebaAdapter.notifyDataSetChanged();
        } else {
            showLongToast("搜索的贴吧不存在");
        }
        header_et_search.getText().clear();
    }


    class SwipeListViewListener extends BaseSwipeListViewListener {
        @Override
        public void onClickFrontView(int position) {
            super.onClickFrontView(position);
            //showCustomToast("点击事件");
            //进入贴吧贴子列表
            Tieba tieba = tiebaList.get(position);
            Intent intent = new Intent(instance, TiebaTieziActivity_.class);
            intent.putExtra(TiebaTieziActivity.intentTiebaId, tieba.getId());
            startActivity(intent);
        }

        @Override
        public void onDismiss(int[] reverseSortedPositions) {
            //showCustomToast("移除事件");
            //TODO:删除贴吧
            for (int position : reverseSortedPositions) {
                //TODO:更新数据库
                Tieba tieba = tiebaList.get(position);
                tieba.setState(Code.State.Delete);
                tiebaLpi.saveOrUpdate(instance, tieba);
                tiebaList.remove(position);
            }
            tiebaAdapter.notifyDataSetChanged();
        }

        @Override
        public void onStartOpen(int position, int action, boolean right) {
            tieba_slv.closeOpenedItems();
            super.onStartOpen(position, action, right);
        }
    }

}