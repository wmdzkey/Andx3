package com.umk.andx3.demo.gridheader;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umk.andx3.demo.R;
import com.umk.andx3.view.gridheader.GridViewWithHeaderBaseAdapter;

/**
 * Created by shalafi on 6/6/13.
 */
public class GridViewWithHeaderExampleAdapter extends GridViewWithHeaderBaseAdapter {

	Integer[] mArray = new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12, 13, 14, 15, 16};
    //Integer[] mArray = new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100};

    private LayoutInflater mInflater;

	public GridViewWithHeaderExampleAdapter(Context context) {
		super(context);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public Integer getItem(int position) {
		return mArray[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemCount() {
		return mArray.length;
	}

	@Override
	protected View getItemView(int position, View view, ViewGroup parent) {
		if (view == null) {
			view = mInflater.inflate(R.layout.simple_list_item, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.text);        
		tv.setText(String.valueOf(getItem(position)));
		return view;
	}

}
