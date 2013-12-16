package com.umk.tiebashenqi.activity.tieba;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import com.google.gson.internal.LinkedTreeMap;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.ViewById;
import com.lidroid.xutils.util.LogUtils;
import com.umk.andx3.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.tiebashenqi.adapter.TiebaTieziAdapter;
import com.umk.tiebashenqi.entity.Tieba;
import com.umk.tiebashenqi.entity.Tiezi;
import com.umk.tiebashenqi.entity.TieziPicture;
import com.umk.tiebashenqi.lpi.TiebaLpi;
import com.umk.tiebashenqi.lpi.TieziLpi;
import com.umk.tiebashenqi.util.TiebaUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@NoTitle
@EActivity(R.layout.activity_tieba)
public class TiebaActivity extends BaseActivity {

    @ViewById(R.id.elv_main_tieba)
    ExpandableListView elv_main_tieba;
    @ViewById(R.id.et_main_tieba)
    EditText et_main_tieba;

    TiebaTieziAdapter mAdapter = null;
    List<Tiezi> mGroup = new ArrayList<Tiezi>();
    List<List<TieziPicture>> mData = new ArrayList<List<TieziPicture>>();

    @AfterViews
    void init() {
        initData();
        mAdapter = new TiebaTieziAdapter(this, mGroup, mData);
        elv_main_tieba.setGroupIndicator(getResources().getDrawable(R.drawable.ic_expander));
        elv_main_tieba.setAdapter(mAdapter);
        elv_main_tieba.setOnChildClickListener(onChildClickListener);
    }



    private void initData() {


        Tieba tieba = new Tieba();
        tieba.setTheName("微笑脉脉水悠悠");
        tieba.setTheNameUrl("%E5%BE%AE%E7%AC%91%E8%84%89%E8%84%89%E6%B0%B4%E6%82%A0%E6%82%A0");//%E5%A7%90%E8%84%B1
        //TODO:保存贴吧到数据库
        TiebaLpi tiebaLpi = new TiebaLpi();
        tiebaLpi.saveOrUpdate(instance, tieba);

        //设置主页
        String homePage = "http://tieba.baidu.com/f?ie=utf-8&kw=" + tieba.getTheNameUrl();


        //初始化帖子列表<标题,网址>
        LinkedTreeMap<String, String> map = TiebaUtil.getHomePageHashMap(homePage);
        //TODO:设置帖子
        List<Tiezi> tieziList = new ArrayList<Tiezi>();
        for(String title : map.keySet()){
            Tiezi tiezi = new Tiezi();
            tiezi.setTheName(title);
            tiezi.setTiebaId(tieba.getId());
            tiezi.setUrl(map.get(title));
            mGroup.add(tiezi);
            tieziList.add(tiezi);
        }
        //TODO:保存帖子到数据库
        TieziLpi tieziLpi = new TieziLpi();
        tieziLpi.saveOrUpdate(instance, tieziList);

        //TODO:初始化帖子内部图片
        for(String title : map.keySet()){
            LogUtils.e("准备下载子页面，标题为：" + title);
            //下载贴吧的每一页
//            HashSet<String> set = TiebaUtil.getDetailsPageImageList(map.get(title));
            List<TieziPicture> childList = new ArrayList<TieziPicture>();
//            for (String url : set) {
//                TieziPicture tieziPicture = new TieziPicture();
//                tieziPicture.setImageUrl(url);
//                childList.add(tieziPicture);
//            }
            mData.add(childList);
            //TODO:暂时不显示，点击之后到数据库加载显示，点刷新重新获取网络数据
        }
    }

    /**
     * ChildView 设置 布局很可能onChildClick进不来，要在 ChildView layout 里加上
     * android:descendantFocusability="blocksDescendants",
     * 还有isChildSelectable里返回true
     */
    OnChildClickListener onChildClickListener = new OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            final TieziPicture item = mAdapter.getChild(groupPosition, childPosition);
            //加载可放大的图片View显示
            Intent intent = new Intent(instance, ImageViewActivity.class);
            intent.putExtra("imageUrl", item.getImageUrl());
            instance.startActivity(intent);
            return true;
        }
    };

}