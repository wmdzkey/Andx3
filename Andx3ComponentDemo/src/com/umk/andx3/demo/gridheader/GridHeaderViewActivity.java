package com.umk.andx3.demo.gridheader;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umk.andx3.demo.R;
import com.umk.andx3.view.x3list.X3ListView;
import com.umk.andx3.view.x3list.X3ListView.IX3ListViewListener;

public class GridHeaderViewActivity extends Activity {
	public static Context instance = null;
	public Handler mHandler;

	private X3ListView mListView;
	private GridViewWithHeaderExampleAdapter mAdapter;
	private ImageView mBackTop;

	public static final int MSG_DISMISS_PROGRESS_DIALOG = 17;
	public static final int MSG_UPDATE_LIST = 18;

	public LayoutInflater inflater;
	ArrayList<HashMap<String, Object>> lstImageItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gridheaderview);
		instance = this;
		mHandler = new Handler();
		inflater = LayoutInflater.from(this);

		mListView = (X3ListView) findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		
		View header = View.inflate(getBaseContext(), R.layout.simple_list_item, null);
		TextView tv = (TextView) header.findViewById(R.id.text);
		tv.setText("This is a Header");
		mListView.addHeaderView(tv);
		
		
		mAdapter = new GridViewWithHeaderExampleAdapter(this);
		mAdapter.setNumColumns(2);
		

		mBackTop = (ImageView) findViewById(R.id.lv_backtotop);
 		mListView.setBackToTopView(mBackTop);
		
		mListView.setAdapter(mAdapter);
		mListView.setX3ListViewListener(x3ListListener);
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime(new Date());
	}
	
	IX3ListViewListener x3ListListener = new IX3ListViewListener() {
		
		@Override
		public void onRefresh() {
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {

					Log.e("com.umk.android.X3ListView", "状态：刷新数据");
					mAdapter.notifyDataSetChanged();
					onLoad();
				}
			}, 2000);
			
		}
		
		@Override
		public void onLoadMore() {
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {

					Log.e("com.umk.android.X3ListView", "状态：读取数据");

					mAdapter.notifyDataSetChanged();
					onLoad();
				}
			}, 2000);
			
		}
	};

}
