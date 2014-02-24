package com.umk.tiebashenqi.activity.me;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.googlecode.androidannotations.annotations.*;

import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.x3list.X3ListView;
import com.umk.tiebashenqi.adapter.GalleryAdapter;
import com.umk.tiebashenqi.entity.TieziPicture;
import com.umk.tiebashenqi.lpi.TieziPictureLpi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Winnid
 * @title 查看贴吧缓存的图库
 * @version:1.0
 * @since：13-12-28
 */
@NoTitle
@EActivity(R.layout.activity_gallery)
public class GalleryActivity extends BaseActivity{

    public static Context instance = null;

    @ViewById(R.id.header_layout_right_imagebuttonlayout)
    LinearLayout header_layout_right_imagebuttonlayout;
    @ViewById(R.id.header_stv_title)
    ScrollingTextView header_stv_title;
    @ViewById(R.id.header_stv_subtitle)
    ScrollingTextView header_stv_subtitle;



    @ViewById(R.id.gallery_lv_picture)
    GridView mGridView;
    GalleryAdapter mAdapter;

    List<TieziPicture> pictures = new ArrayList<TieziPicture>();
    TieziPictureLpi tieziPictureLpi = new TieziPictureLpi();

    @AfterViews
    void init() {
        instance = this;
        header_stv_title.setText("图库");
        header_layout_right_imagebuttonlayout.setVisibility(View.GONE);

        initData();
        initView();
    }

    @Click
    void header_layout_left_imagebuttonlayout() {
        finish();
    }

    private void initData() {
        loadPicture();
    }

    private void loadPicture() {
        pictures = tieziPictureLpi.findAll(instance);
    }

    private void initView() {
        mAdapter = new GalleryAdapter(instance, pictures);
        mGridView.setAdapter(mAdapter);
    }

}
