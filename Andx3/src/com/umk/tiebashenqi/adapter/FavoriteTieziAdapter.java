package com.umk.tiebashenqi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.smartybean.android.http.HttpInterface;

import com.umk.tiebashenqi.R;
import com.umk.andx3.api.ProgressCallBack;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.base.BaseToast;
import com.umk.andx3.lib.config.Code;
import com.umk.andx3.view.dialog.SimpleProgressDialog;
import com.umk.tiebashenqi.activity.favorite.GalleryBitmapUtilActivity;
import com.umk.tiebashenqi.api.FavoriteTieziApi;
import com.umk.tiebashenqi.api.TieziApi;
import com.umk.tiebashenqi.config.SystemAdapter;
import com.umk.tiebashenqi.entity.FavoriteTiezi;
import com.umk.tiebashenqi.entity.Tiezi;
import com.umk.tiebashenqi.entity.TieziPicture;
import com.umk.tiebashenqi.lpi.TieziPictureLpi;
import com.umk.tiebashenqi.util.TiebaUtil;

import java.util.*;

/**
* @author Winnid
* @title 帖子预览数据集
* @version:1.0
* @since：13-12-6
*/
public class FavoriteTieziAdapter extends BaseExpandableListAdapter {

    private ExpandableListView expandableListView;
    private Context mContext;
    private LayoutInflater mInflater = null;
    private List<Tiezi> mGroup = null;
    private List<List<TieziPicture>> mData = null;
    private Map<Integer, Boolean> mCache = new HashMap<Integer, Boolean>();

    public FavoriteTieziAdapter(Context ctx, ExpandableListView view, List<Tiezi> tieziList, List<List<TieziPicture>> pictureList) {
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
        //return mData.get(groupPosition).size();
        return mData.get(groupPosition).size() == 0 ? 0 : 1;
    }

    @Override
    public List<TieziPicture> getGroup(int groupPosition) {
        return mData.get(groupPosition);
    }

    @Override
    public List<TieziPicture> getChild(int groupPosition, int childPosition) {
        return mData.get(groupPosition);
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
        final GroupViewHolder holder;
        final Tiezi group;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.group_item_tiezi, null);
            holder = new GroupViewHolder();
            holder.mGroupName = (TextView) convertView.findViewById(R.id.group_name);
            holder.mGroupCount = (TextView) convertView.findViewById(R.id.group_count);
            holder.btn_group_fresh = (Button) convertView.findViewById(R.id.group_ibtn_fresh);
            holder.btn_group_favorite = (Button) convertView.findViewById(R.id.group_ibtn_favorite);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        group = mGroup.get(groupPosition);
        holder.mGroupName.setText(group.getTheName());
        holder.btn_group_favorite.setVisibility(View.GONE);

        if (mCache.get(groupPosition) == null) {
            mCache.put(groupPosition, false);
        }

        if (!mCache.get(groupPosition)) {
            holder.mGroupName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initTieziPicture(holder, group, groupPosition);
                    mCache.put(groupPosition, true);
                    holder.mGroupName.setClickable(false);
                    holder.mGroupName.setFocusable(false);
                }
            });
        }

        holder.btn_group_fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCache.put(groupPosition, true);
                //TODO智能追加
                refreshTieziPicture(holder, group, groupPosition);
            }
        });

        holder.mGroupCount.setText("[" + mData.get(groupPosition).size() + "]");

        return convertView;
    }


    private void cancelFavorite(GroupViewHolder holder, Tiezi group, int groupPosition) {
        BaseToast.showToast(mContext, "收藏了暂时没有办法取消哦");
    }


    private void initTieziPicture(final GroupViewHolder holder, final Tiezi tiezi, final int groupPosition) {
        FavoriteTieziApi favoriteTieziApi = HttpInterface.proxy(mContext, FavoriteTieziApi.class);
        favoriteTieziApi.findFavoriteTieziPicture(SystemAdapter.getCurrentUser().getId(), tiezi.getUrl(), new ProgressCallBack<List<TieziPicture>>() {
            @Override
            protected Context getContext() {
                return mContext;
            }

            @Override
            public void call(List<TieziPicture> tieziPictures) {
                mData.set(groupPosition, tieziPictures);
                notifyDataSetChanged();
                expandableListView.expandGroup(groupPosition);
                //保存数据到数据库
                TieziPictureLpi tieziPictureLpi = new TieziPictureLpi();
                tieziPictureLpi.saveOrUpdate(mContext, tieziPictures);
            }
        });
    }

    private void refreshTieziPicture(final GroupViewHolder holder, final Tiezi tiezi, final int groupPosition) {
        new SimpleProgressDialog<List<TieziPicture>>(mContext, "正在加载...") {
            @Override
            public List<TieziPicture> doInBackground() {
                //刷新当前帖子图片数据
                //下载贴吧的每一页
                HashSet<String> set = TiebaUtil.getDetailsPageImageList(tiezi.getUrl());
                List<TieziPicture> childList = null;
                if (set != null) {
                    childList = new ArrayList<TieziPicture>();
                    for (String url : set) {
                        TieziPicture tieziPicture = new TieziPicture();
                        tieziPicture.setImageUrl(url);
                        childList.add(tieziPicture);
                    }
                }
                return childList;
            }

            @Override
            public void doInUiThread(List<TieziPicture> childList) {
                if(childList != null) {
                    final int updatePictureCount = childList.size() - mData.get(groupPosition).size();
                    mData.set(groupPosition, childList);
                    notifyDataSetChanged();
                    expandableListView.expandGroup(groupPosition);
                    //保存数据到数据库
                    TieziPictureLpi tieziPictureLpi = new TieziPictureLpi();
                    tieziPictureLpi.saveOrUpdate(mContext, childList);

                    if(updatePictureCount == 0) {
                        BaseToast.showToast(mContext, "本帖暂无更新图");
                    } else {
                        TieziApi tieziApi = HttpInterface.proxy(mContext, TieziApi.class);
                        //传递List只需要转换Json即可
                        tieziApi.update(tiezi, SystemAdapter.gson.toJson(childList), new ProgressCallBack<String>() {
                            @Override
                            protected Context getContext() {
                                return mContext;
                            }

                            @Override
                            public void call(String s) {
                                BaseToast.showToast(mContext, s + ", 已加载本帖图 +" + updatePictureCount );
                            }
                        });
                    } 
                    
                } else {
                    initTieziPicture(holder, tiezi, groupPosition);
                    BaseToast.showToast(mContext, "帖子已经被删了，好好珍惜吧");
                }
            }
        }.show();
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        List<TieziPicture> item;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.group_item_tiezi_child, null);
            holder = new ChildViewHolder();
            holder.mGridView = (GridView) convertView.findViewById(R.id.tiezi_gv_img);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        item = getChild(groupPosition, childPosition);


        TieziPictureAdapter baImageItems = new TieziPictureAdapter(mContext, item, BaseActivity.getScreenWidth(mContext));
        holder.mGridView.setAdapter(baImageItems);
        holder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPicture(mContext, groupPosition, position);
            }
        });

        return convertView;
    }

    /**显示图片*/
    private void showPicture(Context context, int groupPosition, int childPosition) {
        //加载可放大的图片View显示
        Intent intent = new Intent(context, GalleryBitmapUtilActivity.class);
        ArrayList<String> urlList = new ArrayList<String>();
        for (TieziPicture tieziPicture : mData.get(groupPosition)) {
            urlList.add(TiebaUtil.getOriginalImageUrl(tieziPicture.getImageUrl()));
        }
        intent.putStringArrayListExtra(GalleryBitmapUtilActivity.intentUrlList, urlList);
        intent.putExtra(GalleryBitmapUtilActivity.intentUrlPosition, childPosition);
        context.startActivity(intent);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        /*很重要：实现ChildView点击事件，必须返回true*/
        return true;
    }

    private class GroupViewHolder {
        TextView mGroupName;
        TextView mGroupCount;
        Button btn_group_fresh;
        Button btn_group_favorite;
    }

    private class ChildViewHolder {
        GridView mGridView;
    }
}
