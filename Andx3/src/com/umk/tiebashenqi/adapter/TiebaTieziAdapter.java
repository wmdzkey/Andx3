package com.umk.tiebashenqi.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;
import com.smartybean.android.http.HttpInterface;

import com.umk.tiebashenqi.R;
import com.umk.andx3.api.ProgressCallBack;
import com.umk.andx3.base.BaseActivity;
import com.umk.andx3.base.BaseToast;
import com.umk.andx3.lib.config.Code;
import com.umk.andx3.view.dialog.SimpleProgressDialog;
import com.umk.tiebashenqi.activity.me.MeActivity;
import com.umk.tiebashenqi.activity.favorite.GalleryBitmapUtilActivity;
import com.umk.tiebashenqi.activity.tieba.TiebaTieziActivity;
import com.umk.tiebashenqi.api.FavoriteTieziApi;
import com.umk.tiebashenqi.config.SystemAdapter;
import com.umk.tiebashenqi.entity.FavoriteTiezi;
import com.umk.tiebashenqi.entity.Tiezi;
import com.umk.tiebashenqi.entity.TieziPicture;
import com.umk.tiebashenqi.lpi.FavoriteTieziLpi;
import com.umk.tiebashenqi.lpi.TieziPictureLpi;
import com.umk.tiebashenqi.util.TiebaUtil;

import java.util.*;

/**
* @author Winnid
* @title 帖子预览数据集
* @version:1.0
* @since：13-12-6
*/
public class TiebaTieziAdapter extends BaseExpandableListAdapter{

    private ExpandableListView expandableListView;
    private Context mContext;
    private LayoutInflater mInflater = null;
    private List<Tiezi> mFavoriteTiezi = null;
    private List<Tiezi> mGroup = null;
    private List<List<TieziPicture>> mData = null;
    private Map<Integer, Boolean> mCache = new HashMap<Integer, Boolean>();
    private Handler mHandler = null;

    public TiebaTieziAdapter(Context ctx, ExpandableListView view, List<Tiezi> tieziList, List<List<TieziPicture>> pictureList) {
        expandableListView = view;
        mContext = ctx;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGroup = tieziList;
        mData = pictureList;
        mHandler = new Handler();
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
        GroupViewHolder holder = null;
        Tiezi group;
        if (convertView != null) {
            holder = (GroupViewHolder) convertView.getTag();
        } else if (holder == null) {
            convertView = mInflater.inflate(R.layout.group_item_tiezi, null);
            holder = new GroupViewHolder();
            holder.mGroupName = (TextView) convertView.findViewById(R.id.group_name);
            holder.mGroupCount = (TextView) convertView.findViewById(R.id.group_count);
            holder.btn_group_fresh = (Button) convertView.findViewById(R.id.group_ibtn_fresh);
            holder.btn_group_favorite = (Button) convertView.findViewById(R.id.group_ibtn_favorite);
            convertView.setTag(holder);
        }

        group = mGroup.get(groupPosition);
        holder.mGroupName.setText(group.getTheName());

        if (mCache.get(groupPosition) == null) {
            mCache.put(groupPosition, false);
        }
        if (!mCache.get(groupPosition)) {
            holder.mGroupName.setOnClickListener(getGroupNameClickListener(holder, group, groupPosition));
        }

        holder.mGroupCount.setText("[" + mData.get(groupPosition).size() + "]");
        holder.btn_group_fresh.setOnClickListener(getGroupFreshClickListener(holder, group, groupPosition));



        holder.isFavorite = false;
        if (mFavoriteTiezi != null && mFavoriteTiezi.size() != 0) {
            for (Tiezi tiezi : mFavoriteTiezi) {
                if (tiezi.getUrl().equals(group.getUrl())) {
                    holder.isFavorite = true;
                }
            }
        }

        if (holder.isFavorite) {
            holder.btn_group_favorite.setBackgroundResource(R.drawable.ic_favorite_yes);
        } else {
            holder.btn_group_favorite.setBackgroundResource(R.drawable.ic_favorite_no);
        }

        holder.btn_group_favorite.setOnClickListener(getGroupFavoriteClickListener(holder, group, groupPosition));

        return convertView;
    }

    public View.OnClickListener getGroupNameClickListener(final GroupViewHolder holder, final Tiezi group, final int groupPosition) {

        View.OnClickListener groupNameClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshTieziPicture(holder, group, groupPosition, false);
                mCache.put(groupPosition, true);
                holder.mGroupName.setClickable(false);
                holder.mGroupName.setFocusable(false);
            }
        };
        return groupNameClickListener;
    }

    public View.OnClickListener getGroupFreshClickListener(final GroupViewHolder holder, final Tiezi group, final int groupPosition) {

        View.OnClickListener groupFreshClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCache.put(groupPosition, true);
                refreshTieziPicture(holder, group, groupPosition, false);
            }
        };
        return groupFreshClickListener;
    }

    public View.OnClickListener getGroupFavoriteClickListener(final GroupViewHolder holder, final Tiezi group, final int groupPosition) {

        View.OnClickListener groupFavoriteClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemAdapter.getCurrentUser() == null) {
                    BaseToast.showToast(mContext, "注册后才可使使用收藏功能哦");
                    return;
                }

                if(!holder.isFavorite) {
                    payForFavorite(holder, group, groupPosition);
                } else {
                    cancelFavorite(holder, group, groupPosition);
                }
            }
        };
        return groupFavoriteClickListener;
    }




    private void payForFavorite(final GroupViewHolder holder, final Tiezi group, final int groupPosition) {
        //收藏前提示
        if(SystemAdapter.getNoticeableTieziFavorite()) {
            DialogInterface.OnClickListener leftClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    payMoney(10, holder, group, groupPosition);
                }
            };

            DialogInterface.OnClickListener rightClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    payMoney(10, holder, group, groupPosition);
                    SystemAdapter.setNoticeableTieziFavorite(false);
                }
            };

            ((TiebaTieziActivity)mContext).showAlertDialog("温馨提示",
                    "收藏帖子可以永久查看\n即使被删，也可永久保存在您的手机中\n每收藏一个帖子将消耗您10贴币\n按返回可取消",
                    "收藏", leftClickListener,
                    "收藏且不再提醒", rightClickListener);
        } else {
            payMoney(10, holder, group, groupPosition);
        }
    }

    private void payMoney(int amount, final GroupViewHolder holder, final Tiezi group, final int groupPosition) {
        UpdatePointsNotifier upn = new UpdatePointsNotifier() {
            @Override
            public void getUpdatePoints(final String currencyName, final int pointTotal) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MeActivity.updateMoney(currencyName, pointTotal);
                        addFavoriteToServer(holder, group, groupPosition);
                    }
                });
            }

            @Override
            public void getUpdatePointsFailed(String error) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        BaseToast.showToast(mContext, "收藏失败, 没有贴币了，点点广告再去赚点吧");
                        //TODO:以后要做一个发送错误报告到服务器的功能
                    }
                });
            }
        };
        AppConnect.getInstance(mContext).spendPoints(amount, upn);
    }

    private void addFavoriteToServer(final GroupViewHolder holder, final Tiezi group, final int groupPosition) {
        if(!holder.isFavorite) {
            //这些要先初始化图片，加入数据库
            if(!mCache.get(groupPosition)) {
                refreshTieziPicture(holder, group, groupPosition, true);
                mCache.put(groupPosition, true);
                holder.mGroupName.setClickable(false);
                holder.mGroupName.setFocusable(false);
            } else {
                addTieziPicture(holder, group, groupPosition);
            }
        } else {
            //TODO 解除收藏
        }
    }

    private void cancelFavorite(GroupViewHolder holder, Tiezi group, int groupPosition) {
        BaseToast.showToast(mContext, "收藏了暂时没有办法取消哦");
    }

    private void addTieziPicture(final GroupViewHolder holder, final Tiezi tiezi, final int groupPosition) {

        FavoriteTiezi favoriteTiezi = new FavoriteTiezi();
        favoriteTiezi.setTiebaId(tiezi.getTiebaId());
        favoriteTiezi.setUserId(SystemAdapter.getCurrentUser().getId());
        favoriteTiezi.setState(Code.state.Normal);

        List<TieziPicture> tieziPictureList = mData.get(groupPosition);//后台补充其余数据
        //传递List只需要转换Json即可
        List<String> tieziPictureUrlList = new ArrayList<String>();
        for (TieziPicture tieziPicture : tieziPictureList) {
            tieziPictureUrlList.add(tieziPicture.getImageUrl());
        }
        String json = SystemAdapter.gson.toJson(tieziPictureUrlList);

        FavoriteTieziApi favoriteTieziApi = HttpInterface.proxy(mContext, FavoriteTieziApi.class);
        favoriteTieziApi.add(tiezi, favoriteTiezi, json, new ProgressCallBack<FavoriteTiezi>() {
            @Override
            protected Context getContext() {
                return mContext;
            }

            @Override
            public void call(FavoriteTiezi ft) {
                BaseToast.showToast(mContext, "收藏成功, 消耗10贴币");
                FavoriteTieziLpi favoriteTieziLpi = new FavoriteTieziLpi();
                ft.setId(null);
                favoriteTieziLpi.saveOrUpdate(mContext, ft);
                mFavoriteTiezi.add(tiezi);
                holder.btn_group_favorite.setBackgroundResource(R.drawable.ic_favorite_yes);
                holder.isFavorite = true;
            }
        });
    }

    private void refreshTieziPicture(final GroupViewHolder holder, final Tiezi tiezi, final int groupPosition, final boolean afterAdd) {
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
                    mData.set(groupPosition, childList);
                    notifyDataSetChanged();
                    expandableListView.expandGroup(groupPosition);
                    //保存数据到数据库
                    TieziPictureLpi tieziPictureLpi = new TieziPictureLpi();
                    tieziPictureLpi.saveOrUpdate(mContext, childList);

                    //SAVE
                    if (afterAdd && SystemAdapter.getCurrentUser() != null) {
                        addTieziPicture(holder, tiezi, groupPosition);
                    }
                } else {
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

    public void setFavoriteTiezi(List<Tiezi> favoriteTiezi) {
        this.mFavoriteTiezi = favoriteTiezi;
    }


    private class GroupViewHolder {
        TextView mGroupName;
        TextView mGroupCount;
        Button btn_group_fresh;
        Button btn_group_favorite;
        Boolean isFavorite = false;
    }

    private class ChildViewHolder {
        GridView mGridView;
    }
}
