package com.umk.tiebashenqi.activity.more;

import android.content.Context;
import android.util.Log;
import android.widget.GridView;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.ViewById;

import com.umk.tiebashenqi.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.view.x3list.X3ListView;
import com.umk.tiebashenqi.adapter.GalleryAdapter;
import com.umk.tiebashenqi.entity.TieziPicture;
import com.umk.tiebashenqi.lpi.TieziPictureLpi;

import java.util.ArrayList;
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

    @ViewById(R.id.gallery_lv_picture)
    X3ListView mListView;
    GalleryAdapter mAdapter;
    List<TieziPicture> pictures = new ArrayList<TieziPicture>();
    List<TieziPicture> newPictureList = new ArrayList<TieziPicture>();
    int pageNo = 0;
    int pageSize = 12;

    TieziPictureLpi tieziPictureLpi = new TieziPictureLpi();

    @AfterViews
    void init() {
        instance = this;
        initData();
        initView();
    }

    private void initData() {
        loadPicture();
    }

    private void loadPicture() {
        newPictureList = tieziPictureLpi.findByPage(instance, pageNo, pageSize);
        if(newPictureList != null && newPictureList.size() != 0) {
            pictures.addAll(newPictureList);
            pageNo++;
        } else {
            showCustomToast("已经到底了~");
        }
    }

    private void initView() {
        mAdapter = new GalleryAdapter(instance, pictures);
        mAdapter.setNumColumns(2);
        mListView.setPullRefreshEnable(false);
        mListView.setAdapter(mAdapter);
        mListView.setOnLoadMoreListener(x3LoadMoreListener);
    }

    X3ListView.OnLoadMoreListener x3LoadMoreListener = new X3ListView.OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            loadPicture();
            mAdapter.notifyDataSetChanged();
            mListView.onComplete();
        }
    };
}
