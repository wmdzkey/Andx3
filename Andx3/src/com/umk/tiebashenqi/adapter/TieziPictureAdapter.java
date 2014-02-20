package com.umk.tiebashenqi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.umk.tiebashenqi.R;
import com.umk.andx3.util.xutil.BitmapHelp;
import com.umk.tiebashenqi.entity.TieziPicture;

import java.util.List;

/**
* @author Winnid
* @title 帖子图片数据集
* @version:1.0
* @since：13-12-6
*/
public class TieziPictureAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<TieziPicture> objects;
    private int mScreenWidth;

    public TieziPictureAdapter(Context context, List<TieziPicture> objects, int screenWidth) {
        this.objects = objects ;
        this.mContext = context;
        this.mScreenWidth = screenWidth;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null ;

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.grid_item_picture, parent, false);
            AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    mScreenWidth/3);
            convertView.setLayoutParams(param);

            holder = new ViewHolder();
            holder.mTieziImgItem = (ImageView) convertView.findViewById(R.id.tiezi_iv_item_img);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        TieziPicture item = getItem(position);
        BitmapHelp.getBitmapUtils(mContext).display(holder.mTieziImgItem, item.getImageUrl());
        return convertView;
    }

    class ViewHolder{
        ImageView mTieziImgItem;
    }
}
