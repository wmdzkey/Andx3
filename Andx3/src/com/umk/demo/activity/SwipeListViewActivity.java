package com.umk.demo.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fortysevendeg.android.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.android.swipelistview.SwipeListView;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import com.umk.andx3.R;
import com.umk.demo.adapter.SwipeAdapter;

/**
 * @author Winnid
 * @title 可以左右滑动的Listview(仿微信聊天列表)
 * @version:1.0
 * @since：13-12-18
 */
public class SwipeListViewActivity extends Activity {
    private SwipeListView mSwipeListView ;
    private SwipeAdapter mAdapter ;
    public static int deviceWidth ;
    private List<String> testData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_swipelistview);
        mSwipeListView = (SwipeListView) findViewById(R.id.example_lv_list);


        testData = getTestData();
        mAdapter = new SwipeAdapter(this, R.layout.list_item_tieba, testData, mSwipeListView);
        deviceWidth = getDeviceWidth();
        mSwipeListView.setAdapter(mAdapter);
        mSwipeListView.setSwipeListViewListener( new TestBaseSwipeListViewListener());
        reload();
    }

    private List<String> getTestData() {
        String [] obj = new String[]{"背对背拥抱","第几个一百天","江南","那些你很冒险的梦","醉赤壁",
                "西界","爱与希望","你要的不是我","不潮不用花钱","只对你有感觉","简简单单"};
        List<String> list = new ArrayList<String>(Arrays.asList(obj));
        return list;
    }

    private int getDeviceWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    private void reload() {
        mSwipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
        mSwipeListView.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
//		mSwipeListView.setSwipeActionRight(settings.getSwipeActionRight());
        mSwipeListView.setOffsetLeft(deviceWidth * 1 / 3);
//		mSwipeListView.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
        mSwipeListView.setAnimationTime(0);
        mSwipeListView.setSwipeOpenOnLongPress(false);
    }

    class TestBaseSwipeListViewListener extends BaseSwipeListViewListener{

        @Override
        public void onClickFrontView(int position) {
            super.onClickFrontView(position);
            Toast.makeText(getApplicationContext(), testData.get(position), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDismiss(int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                testData.remove(position);
            }
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onStartOpen(int position, int action, boolean right) {
            //仿微信打开新的旧的Item恢复原位
            mSwipeListView.closeOpenedItems();
            super.onStartOpen(position, action, right);
        }
    }
}
