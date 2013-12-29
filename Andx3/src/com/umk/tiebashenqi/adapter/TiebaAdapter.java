package com.umk.tiebashenqi.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.fortysevendeg.android.swipelistview.SwipeListView;
import com.umk.andx3.R;
import com.umk.andx3.util.xutil.BitmapHelp;
import com.umk.tiebashenqi.config.SystemConfig;
import com.umk.tiebashenqi.entity.Tieba;
import com.umk.tiebashenqi.entity.Tiezi;
import com.umk.tiebashenqi.entity.TieziPicture;
import com.umk.tiebashenqi.lpi.TieziPictureLpi;
import com.umk.tiebashenqi.util.TiebaUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
* @author Winnid
* @title 帖子预览数据集
* @version:1.0
* @since：13-12-6
*/
public class TiebaAdapter extends ArrayAdapter<Tieba> {

    private LayoutInflater mInflater ;
    private List<Tieba> objects ;
    private SwipeListView mSwipeListView ;

    public TiebaAdapter(Context context, int viewResourceId,List<Tieba> objects, SwipeListView mSwipeListView) {
        super(context, viewResourceId, objects);
        this.objects = objects ;
        this.mSwipeListView = mSwipeListView ;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        holder.mFrontTiebaImage.setImageResource(R.drawable.ic_nav_tieba_active);
        return convertView;
    }

    class ViewHolder{
        TextView mFrontTiebaName ;
        TextView mFrontTiebaInfo ;
        ImageView mFrontTiebaImage ;
        LinearLayout mBackDelete ;
    }
}
