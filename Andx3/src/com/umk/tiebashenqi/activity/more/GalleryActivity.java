package com.umk.tiebashenqi.activity.more;

import android.widget.GridView;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.ViewById;
import com.umk.andx3.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.tiebashenqi.adapter.GalleryAdapter;
import com.umk.tiebashenqi.entity.TieziPicture;
import com.umk.tiebashenqi.lpi.TieziPictureLpi;

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

    @ViewById(R.id.gallery_gv_picture)
    GridView mGridView;
    GalleryAdapter mAdapter;
    List<TieziPicture> pictures;

    TieziPictureLpi tieziPictureLpi;

    @AfterViews
    void init() {
        initData();
        initView();
    }

    private void initData() {
        tieziPictureLpi = new TieziPictureLpi();
        pictures = tieziPictureLpi.findAll(instance);
    }

    private void initView() {
        mAdapter = new GalleryAdapter(instance, R.layout.grid_item_gallery, pictures, mGridView);
        mGridView.setAdapter(mAdapter);
    }

}
