package com.umk.andx3.view.gridheader;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * Created by shalafi on 6/6/13.
 * Modify by winnid on 13/12/27.
 */
public abstract class GridViewWithHeaderBaseAdapter extends BaseAdapter {

	public interface GridItemClickListener {

		void onGridItemClicked(View v, int position, long itemId);

	}

	private class ListItemClickListener implements OnClickListener {

		private int mPosition;

		public ListItemClickListener(int currentPos) {
			mPosition = currentPos;
		}

		@Override
		public void onClick(View v) {
			onGridItemClicked (v, mPosition);
		}
	}

	private int mUnusedId = 0x87654321;
	private int mNumColumns;
	private Context mContext;
	private GridItemClickListener mGridItemClickListener;

	public GridViewWithHeaderBaseAdapter(Context context) {
		mContext = context;
		mNumColumns = 1;
	}

	public final void setOnGridClickListener(GridItemClickListener listener) {
		mGridItemClickListener = listener;
	}

	private final void onGridItemClicked(View v, int position) {
		if (mGridItemClickListener != null) {
			mGridItemClickListener.onGridItemClicked(v, position, getItemId(position));
		}
	}

	public final int getNumColumns() {
		return mNumColumns;
	}

	public final void setNumColumns(int numColumns) {
		mNumColumns = numColumns;
		notifyDataSetChanged();
	}

	@Override
	public final int getCount() {
		return (int) Math.ceil(getItemCount()*1f / getNumColumns());
	}

	public abstract int getItemCount();

	protected abstract View getItemView(int position, View view, ViewGroup parent);

	@Override
	public final View getView(int position, View view, ViewGroup viewGroup) {
		LinearLayout layout;
		int columnWidth = 0;
		if (viewGroup !=  null) {
			columnWidth = viewGroup.getWidth()/mNumColumns;    		
		}
		else if (view != null) {
			columnWidth = view.getWidth()/mNumColumns;;
		}
		// Make it be rows of the number of columns
		if (view == null) {
			// This is items view
			layout = createItemRow(position, viewGroup, columnWidth);            
		}
		else {
			layout = (LinearLayout) view;
			updateItemRow(position, viewGroup, layout, columnWidth);
		}
		return layout;
	}

	private LinearLayout createItemRow(int position, ViewGroup viewGroup, int columnWidth) {		
		LinearLayout layout;
		layout = new LinearLayout(mContext);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		// Now add the sub views to it
		for (int i = 0; i < mNumColumns; i++) {
			int currentPos = position * mNumColumns + i;
			// Get the new View
			View insideView;
			if (currentPos < getItemCount()) {            		
				insideView = getItemView(currentPos, null, viewGroup);            	
				insideView.setVisibility(View.VISIBLE);
				View theView = getItemView(currentPos, insideView, viewGroup);
				theView.setOnClickListener(new ListItemClickListener (currentPos));				
			}
			else {
				insideView = new View(mContext);
				insideView.setId(mUnusedId);
				insideView.setVisibility(View.INVISIBLE);
			}            	
			layout.addView(insideView);
//			insideView.setBackgroundResource(android.R.drawable.list_selector_background);
			// Set the width of this column
			LayoutParams params = insideView.getLayoutParams();
			params.width = columnWidth;
			insideView.setLayoutParams(params);			
		}
		return layout;
	}	

	private void updateItemRow(int position, ViewGroup viewGroup, LinearLayout layout, int columnWidth) {
		for (int i=0; i<mNumColumns; i++) {
			int currentPos = position * mNumColumns + i;
			View insideView = layout.getChildAt(i);
			// If there are less views than objects. add a view here
			// 解决动态移除后，Tag没有绑定问题
			if (insideView == null || insideView.getId() == mUnusedId) {
                if (currentPos < getItemCount()) {
                    insideView = getItemView(currentPos, null, viewGroup);
                } else {
                    insideView = new View(mContext);
    				insideView.setId(mUnusedId);
                }
                layout.addView(insideView, i);
			}
			
			
			// Set the width of this column
			LayoutParams params = insideView.getLayoutParams();
			params.width = columnWidth;
			insideView.setLayoutParams(params);

			if (currentPos < getItemCount()) {
                insideView.setVisibility(View.VISIBLE);
				// Populate the view
				View theView = getItemView(currentPos, insideView, viewGroup);
				theView.setOnClickListener(new ListItemClickListener (currentPos));				
				if (!theView.equals(insideView)) {
					// DO NOT CHANGE THE VIEWS
				}
			}
			else {
				insideView.setVisibility(View.INVISIBLE);
			}
		}
	}
}