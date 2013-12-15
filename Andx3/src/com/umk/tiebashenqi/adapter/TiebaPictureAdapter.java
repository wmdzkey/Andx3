package com.umk.tiebashenqi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.umk.andx3.R;
import com.umk.andx3.util.xutil.BitmapHelp;
import com.umk.tiebashenqi.activity.tieba.ImageViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
* @author Winnid
* @title 相册预览数据集
* @version:1.0
* @since：13-12-6
*/
public class TiebaPictureAdapter extends BaseAdapter {

    private Context mContext;//用于接收传递过来的Context对象
    public LayoutInflater mInflater;

    public List<String> urls = new ArrayList<String>();

    public TiebaPictureAdapter(Context context) {
        super();
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public TiebaPictureAdapter(Context context, List<String> urls) {
        super();
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.urls = urls;
    }
    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //实现ViewHolder加载Item的自定义Adapter
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        final String url;
        // 显示优化（只要之前显示过的就可以不再再次从布局文件读取，直接从缓存中读取——ViewHolder的作用）
        // 其实是setTag和getTag中Tag的作用
        if (convertView == null) {// 如果是第一次显示该页面(要记得保存到viewholder中供下次直接从缓存中调用)
            convertView = mInflater.inflate(R.layout.griditem_tieba_pic, null);
            holder = new ViewHolder();
            // 以下为保存这一屏的内容，供下次回到这一屏的时候直接refresh，而不用重读布局文件
            holder.iv_tieba_pic = (ImageView) convertView.findViewById(R.id.iv_tieba_pic);
            convertView.setTag(holder);
        } else {// 如果之前已经显示过该页面，则用viewholder中的缓存直接刷屏
            holder = (ViewHolder) convertView.getTag();
        }

        url = urls.get(position);
        //TODO:图片有问题不应该显示
        BitmapHelp.getBitmapUtils(mContext).display(holder.iv_tieba_pic, url);
        //点击大图进行预览
        holder.iv_tieba_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ImageViewActivity.class);
                intent.putExtra("imageUrl", url);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    //缓存
    class ViewHolder{
        public ImageView iv_tieba_pic;
    }
}
