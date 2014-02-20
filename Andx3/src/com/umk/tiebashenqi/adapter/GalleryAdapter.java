package com.umk.tiebashenqi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.umk.andx3.util.xutil.BitmapHelp;
import com.umk.andx3.view.gridheader.GridViewWithHeaderBaseAdapter;
import com.umk.tiebashenqi.R;
import com.umk.tiebashenqi.activity.tieba.ImageViewActivity;
import com.umk.tiebashenqi.entity.TieziPicture;

import java.util.List;

/**
* @author Winnid
* @title 帖子预览数据集
* @version:1.0
* @since：13-12-6
*/
public class GalleryAdapter extends GridViewWithHeaderBaseAdapter {

    private Context mContext ;
    private LayoutInflater mInflater ;
    private List<TieziPicture> objects ;

    public GalleryAdapter(Context context, List<TieziPicture> objects) {
        super(context);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @Override
    public TieziPicture getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    protected View getItemView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null ;
        final TieziPicture item = getItem(position);

        if(view == null){
            view = mInflater.inflate(R.layout.grid_item_gallery, parent, false);
            holder = new ViewHolder();
            holder.mPicture = (ImageView) view.findViewById(R.id.gallery_item_iv_picture);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        BitmapHelp.getBitmapUtils(mContext).display( holder.mPicture, item.getImageUrl());
        holder.mPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载可放大的图片View显示
                Intent intent = new Intent(mContext, ImageViewActivity.class);
                intent.putExtra("imageUrl", item.getImageUrl());
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHolder{
        ImageView mPicture ;
    }
}
