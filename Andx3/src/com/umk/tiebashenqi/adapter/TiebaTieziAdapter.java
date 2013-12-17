package com.umk.tiebashenqi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.umk.andx3.R;
import com.umk.andx3.util.xutil.BitmapHelp;
import com.umk.demo.entity.Item;
import com.umk.tiebashenqi.activity.tieba.ImageViewActivity;
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
public class TiebaTieziAdapter extends BaseExpandableListAdapter {

    private ExpandableListView expandableListView;
    private Context mContext;
    private LayoutInflater mInflater = null;
    private List<Tiezi> mGroup = null;//改名为mGroup,类型改为List<Tiezi>
    private List<List<TieziPicture>>   mData = null;

    public TiebaTieziAdapter(Context ctx, ExpandableListView view, List<Tiezi> tieziList, List<List<TieziPicture>> pictureList) {
        expandableListView = view;
        mContext = ctx;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGroup = tieziList;
        mData = pictureList;
    }

    public void setData(List<Tiezi> tieziList, List<List<TieziPicture>> list) {
        mGroup = tieziList;
        mData = list;
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mData.get(groupPosition).size();
    }

    @Override
    public List<TieziPicture> getGroup(int groupPosition) {
        return mData.get(groupPosition);
    }

    @Override
    public TieziPicture getChild(int groupPosition, int childPosition) {
        return mData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        final Tiezi group;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.group_tiezi_item, null);
            holder = new GroupViewHolder();
            holder.mGroupName = (TextView) convertView.findViewById(R.id.group_name);
            holder.mGroupCount = (TextView) convertView.findViewById(R.id.group_count);
            holder.btn_group_refresh = (Button) convertView.findViewById(R.id.btn_group_refresh);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        group = mGroup.get(groupPosition);
        holder.mGroupName.setText(group.getTheName());
        holder.mGroupCount.setText("[" + mData.get(groupPosition).size() + "]");
        holder.btn_group_refresh.setFocusable(false);
        holder.btn_group_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO(OK):刷新当前帖子图片数据
                //下载贴吧的每一页
                HashSet<String> set = TiebaUtil.getDetailsPageImageList(group.getUrl());
                List<TieziPicture> childList = new ArrayList<TieziPicture>();
                for (String url : set) {
                    TieziPicture tieziPicture = new TieziPicture();
                    tieziPicture.setImageUrl(url);
                    childList.add(tieziPicture);
                }
                mData.set(groupPosition, childList);
                notifyDataSetChanged();
                expandableListView.expandGroup(groupPosition);
                //TODO(OK):保存数据到数据库
                TieziPictureLpi tieziPictureLpi = new TieziPictureLpi();
                tieziPictureLpi.saveOrUpdate(mContext, childList);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        TieziPicture item;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.group_tiezi_picture_child_item, null);
            holder = new ChildViewHolder();
            holder.mImg = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        item = getChild(groupPosition, childPosition);
        BitmapHelp.getBitmapUtils(mContext).display(holder.mImg, item.getImageUrl());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        /*很重要：实现ChildView点击事件，必须返回true*/
        return true;
    }

    private class GroupViewHolder {
        TextView mGroupName;
        TextView mGroupCount;
        Button btn_group_refresh;
    }

    private class ChildViewHolder {
        ImageView mImg;
    }
}
