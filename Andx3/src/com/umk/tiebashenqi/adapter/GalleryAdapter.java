package com.umk.tiebashenqi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.fortysevendeg.android.swipelistview.SwipeListView;
import com.umk.andx3.R;
import com.umk.andx3.util.xutil.BitmapHelp;
import com.umk.tiebashenqi.activity.tieba.ImageViewActivity;
import com.umk.tiebashenqi.entity.Tieba;
import com.umk.tiebashenqi.entity.TieziPicture;

import java.util.List;

/**
* @author Winnid
* @title 帖子预览数据集
* @version:1.0
* @since：13-12-6
*/
public class GalleryAdapter extends ArrayAdapter<TieziPicture> {

    private Context mContext ;
    private LayoutInflater mInflater ;
    private List<TieziPicture> objects ;
    private GridView mGridView ;

    public GalleryAdapter(Context context, int viewResourceId, List<TieziPicture> objects, GridView gridView) {
        super(context, viewResourceId, objects);
        this.mContext = context;
        this.objects = objects ;
        this.mGridView = gridView ;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null ;
        final TieziPicture item = getItem(position);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.grid_item_gallery, parent, false);
            holder = new ViewHolder();
            holder.mPicture = (ImageView) convertView.findViewById(R.id.gallery_item_iv_picture);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //默认移除按钮(只做移除操作，事件dismiss在外部进行)
        holder.mPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载可放大的图片View显示
                Intent intent = new Intent(mContext, ImageViewActivity.class);
                intent.putExtra("imageUrl", item.getImageUrl());
                mContext.startActivity(intent);
            }
        });
        BitmapHelp.getBitmapUtils(mContext).display( holder.mPicture, item.getImageUrl());
        return convertView;
    }

    class ViewHolder{
        ImageView mPicture ;
    }
}
