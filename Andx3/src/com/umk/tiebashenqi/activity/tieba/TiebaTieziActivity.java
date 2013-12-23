package com.umk.tiebashenqi.activity.tieba;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.gson.internal.LinkedTreeMap;
import com.googlecode.androidannotations.annotations.*;
import com.lidroid.xutils.util.LogUtils;
import com.umk.andx3.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.dialog.FlippingAlertDialog;
import com.umk.tiebashenqi.adapter.TiebaTieziAdapter;
import com.umk.tiebashenqi.entity.Tieba;
import com.umk.tiebashenqi.entity.Tiezi;
import com.umk.tiebashenqi.entity.TieziPicture;
import com.umk.tiebashenqi.lpi.TiebaLpi;
import com.umk.tiebashenqi.lpi.TieziLpi;
import com.umk.tiebashenqi.util.TiebaUtil;

import java.util.ArrayList;
import java.util.List;



@NoTitle
@EActivity(R.layout.activity_tieba_tiezi)
public class TiebaTieziActivity extends BaseActivity {

    public static String intentTiebaId = "tiebaId";


    @ViewById(R.id.tiezi_elv)
    ExpandableListView tiezi_elv;
    @ViewById(R.id.header_layout_right_imagebuttonlayout)
    LinearLayout header_layout_right_imagebuttonlayout;
    @ViewById(R.id.header_ib_right_imagebutton)
    ImageButton header_ib_right_imagebutton;

    TiebaTieziAdapter mAdapter = null;
    List<Tiezi> mGroup = new ArrayList<Tiezi>();
    List<List<TieziPicture>> mData = new ArrayList<List<TieziPicture>>();

    Long tiebaId;


    @AfterViews
    void init() {
        initParam();
        initData();
        initView();
        initList();
    }

    private void initList() {
        mAdapter = new TiebaTieziAdapter(this, tiezi_elv, mGroup, mData);
        tiezi_elv.setGroupIndicator(getResources().getDrawable(R.drawable.ic_expander));
        tiezi_elv.setAdapter(mAdapter);
        tiezi_elv.setOnChildClickListener(onChildClickListener);
    }

    private void initView() {
        header_ib_right_imagebutton.setImageDrawable(getResources().getDrawable(R.drawable.btn_fresh));
    }

    private void initParam() {
        //TODO:初始化参数
        tiebaId = getIntent().getLongExtra(intentTiebaId, -1L);
    }



    private void initData() {
        if(tiebaId != -1L) {
            TiebaLpi tiebaLpi = new TiebaLpi();
            Tieba tieba = tiebaLpi.find(instance, tiebaId);

            //设置主页
            String homePage = "http://tieba.baidu.com/f?ie=utf-8&kw=" + tieba.getTheNameUrl();

            //初始化帖子列表<标题,网址>
            LinkedTreeMap<String, String> map = TiebaUtil.getHomePageHashMap(homePage);
            //TODO(OK):设置帖子
            List<Tiezi> tieziList = new ArrayList<Tiezi>();
            for(String title : map.keySet()){
                Tiezi tiezi = new Tiezi();
                tiezi.setTheName(title);
                tiezi.setTiebaId(tieba.getId());
                tiezi.setUrl(map.get(title));
                mGroup.add(tiezi);
                tieziList.add(tiezi);
            }
            //TODO(OK):保存帖子到数据库
            TieziLpi tieziLpi = new TieziLpi();
            tieziLpi.saveOrUpdate(instance, tieziList);

            //TODO(OK):初始化帖子内部图片
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
                //TODO(OK):暂时不显示，点击之后到数据库加载显示，点刷新重新获取网络数据
            }
        } else {
            showLongToast("贴吧加载出错");
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


    /**
     * 返回
     * */
    @Click
    void header_layout_left_imagebuttonlayout() {
        //finish();
        showAlertDialog("测试一下", "我是内容");
    }

    /**
     * 刷新组
     * */
    @Click
    void header_layout_right_imagebuttonlayout() {
//        mGroup.clear();
//        mData.clear();
//        initData();
//        mAdapter.notifyDataSetChanged();
//        showCustomToast("已刷新");
        FlippingAlertDialog.Builder customBuilder = new
                FlippingAlertDialog.Builder(this);
                customBuilder.setTitle("Custom title").setIcon(R.drawable.ic_tab_more)
                        .setMessage("Custom body")
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                        .setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
        customBuilder.create().show();
    }

}