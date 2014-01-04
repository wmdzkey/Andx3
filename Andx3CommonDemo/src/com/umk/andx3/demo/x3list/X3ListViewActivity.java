package com.umk.andx3.demo.x3list;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.umk.andx3.demo.R;
import com.umk.andx3.view.x3list.X3ListView;

import java.util.ArrayList;


public class X3ListViewActivity extends Activity implements X3ListView.OnRefreshListener, X3ListView.OnLoadMoreListener {
	
	private X3ListView mListView;
	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> items = new ArrayList<String>();
	private ImageView mBackTop;
	
	private Handler mHandler;
	
	private int start = 0;
	private static int refreshCnt = 0;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_x3listview);
		
		geneItems();
		
		mListView = (X3ListView) findViewById(R.id.xListView);
		//mListView.setPullRefreshEnable(false);
		//mListView.setPullLoadEnable(false);
		
		mBackTop = (ImageView) findViewById(R.id.lv_backtotop);
 		mListView.setBackToTopView(mBackTop);
 		
		mAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_text_item, items);
		mListView.setAdapter(mAdapter);
        mListView.setOnRefreshListener(this);
        mListView.setOnLoadMoreListener(this);
		mHandler = new Handler();
	}

	private void geneItems() {
		for (int i = 0; i != 20; i++) {
			items.add("refresh cnt " + (++start));	// 1 - 20
		}
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				items.clear();
				geneItems();
				mAdapter.notifyDataSetChanged();
				mListView.onComplete();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				mAdapter.notifyDataSetChanged();
				mListView.onComplete();
			}
		}, 2000);
	}


}
