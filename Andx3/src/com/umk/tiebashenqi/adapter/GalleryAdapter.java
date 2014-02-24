package com.umk.tiebashenqi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ToggleButton;
import com.umk.andx3.base.BaseActivity;
import com.umk.tiebashenqi.R;
import com.umk.tiebashenqi.activity.favorite.GalleryBitmapUtilActivity;
import com.umk.tiebashenqi.entity.TieziPicture;
import com.umk.tiebashenqi.util.TiebaUtil;
import com.umk.tiebashenqi.util.imagemanager.ImageManager2;

import java.util.ArrayList;
import java.util.List;

/**
* @author Winnid
* @title 帖子预览数据集
* @version:1.0
* @since：13-12-6
*/
public class GalleryAdapter extends BaseAdapter{

    private Context mContext;
    private List<TieziPicture> objects;
    private int mScreenWidth;

    public GalleryAdapter(Context context, List<TieziPicture> objects) {
        this.mContext = context;
        this.objects = objects;
        this.mScreenWidth = BaseActivity.getScreenWidth(mContext);
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public TieziPicture getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    /**
     * 存放列表项控件句柄
     */
    private class ViewHolder {
        public ImageView imageView;
        public ToggleButton toggleButton;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_gallery, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.gallery_item_iv_picture);
            viewHolder.toggleButton = (ToggleButton) convertView.findViewById(R.id.gallery_item_tbtn_choose);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        String path;
        if (objects != null && objects.size() > position) {
            path = objects.get(position).getImageUrl();
        } else {
            path = "picture_z_default";
        }

        if (path.contains("picture_z_default")) {
            viewHolder.imageView.setImageResource(R.drawable.bg_pic_default);
        } else {
            ImageManager2.from(mContext).displayImage(viewHolder.imageView, path, R.drawable.bg_pic_default, 100, 100);

            //这个适合单张图片，多图，有点卡，而且无法充分利用缓存空间
            //BitmapHelp.getBitmapUtils(mContext).display(viewHolder.imageView, path);
        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载可放大的图片View显示
                Intent intent = new Intent(mContext, GalleryBitmapUtilActivity.class);
                ArrayList<String> urlList = new ArrayList<String>();
                for (TieziPicture tieziPicture : objects) {
                    urlList.add(TiebaUtil.getOriginalImageUrl(tieziPicture.getImageUrl()));
                }
                intent.putStringArrayListExtra(GalleryBitmapUtilActivity.intentUrlList, urlList);
                intent.putExtra(GalleryBitmapUtilActivity.intentUrlPosition, position);
                mContext.startActivity(intent);
            }
        });

        viewHolder.toggleButton.setTag(position);

        return convertView;
    }





}