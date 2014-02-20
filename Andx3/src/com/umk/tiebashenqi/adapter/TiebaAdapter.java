package com.umk.tiebashenqi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.fortysevendeg.android.swipelistview.SwipeListView;

import com.umk.tiebashenqi.R;
import com.umk.andx3.util.xutil.BitmapHelp;
import com.umk.tiebashenqi.entity.Tieba;
import com.umk.andx3.lib.util.ListMap;

/**
* @author Winnid
* @title 帖子预览数据集
* @version:1.0
* @since：13-12-6
*/
public class TiebaAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ListMap<Long, Tieba> objects;
    private SwipeListView mSwipeListView;

    public TiebaAdapter(Context context, ListMap<Long, Tieba> objects, SwipeListView mSwipeListView) {
        this.objects = objects ;
        this.mContext = context;
        this.mSwipeListView = mSwipeListView;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Tieba getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.list_item_tieba, parent, false);
            holder = new ViewHolder();
            holder.mFrontTiebaName = (TextView) convertView.findViewById(R.id.tieba_list_item_tv_name);
            holder.mFrontTiebaInfo = (TextView) convertView.findViewById(R.id.tieba_list_item_tv_info);
            holder.mFrontTiebaImage = (ImageView) convertView.findViewById(R.id.tieba_list_item_iv_img);
            holder.mBackDelete = (LinearLayout) convertView.findViewById(R.id.tieba_list_item_ll_delete);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


        //默认移除按钮(只做移除操作，事件dismiss在外部进行)
        holder.mBackDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeListView.closeAnimate(position);
                mSwipeListView.dismiss(position);
            }
        });

        Tieba item = getItem(position);
        holder.mFrontTiebaName.setText(item.getTheName());
        BitmapHelp.getBitmapUtils(mContext).display(holder.mFrontTiebaImage, item.getLogoUrl());
        return convertView;
    }

    class ViewHolder{
        TextView mFrontTiebaName ;
        TextView mFrontTiebaInfo ;
        ImageView mFrontTiebaImage ;
        LinearLayout mBackDelete ;
    }
}
