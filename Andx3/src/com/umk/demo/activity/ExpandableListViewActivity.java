package com.umk.demo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;

import com.umk.tiebashenqi.R;
import com.umk.demo.adapter.ExpandableListViewAdapter;
import com.umk.demo.entity.Item;

/**
 * @author Winnid
 * @title 带扩展的listview(仿QQ好友分组)
 * @version:1.0
 * @since：13-12-18
 */
public class ExpandableListViewActivity extends Activity {

    public static Context instance = null;

    private ExpandableListView mListView = null;
    private ExpandableListViewAdapter mAdapter = null;
    private List<String> mGroup = new ArrayList<String>();
    private List<List<Item>> mData = new ArrayList<List<Item>>();

    private List<String[]> mGroupArrays = new ArrayList<String[]>();

    private List<String[]> mDetailArrays = new ArrayList<String[]>();

    private int[][] mImageIds = new int[][] {
            { R.drawable.demo_img_00,
                    R.drawable.demo_img_01,
                    R.drawable.demo_img_02},
            { R.drawable.demo_img_10,
                    R.drawable.demo_img_11,
                    R.drawable.demo_img_12,
                    R.drawable.demo_img_13,
                    R.drawable.demo_img_14,
                    R.drawable.demo_img_15,
                    R.drawable.demo_img_16},
            { R.drawable.demo_img_20,
                    R.drawable.demo_img_21} };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        initData();
        mListView = new ExpandableListView(this);
        mListView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        setContentView(mListView);

        mListView.setGroupIndicator(getResources().getDrawable(R.drawable.drawable_expand_open));
        mAdapter = new ExpandableListViewAdapter(this, mGroup, mData);
        mListView.setAdapter(mAdapter);
        mListView.setDescendantFocusability(ExpandableListView.FOCUS_AFTER_DESCENDANTS);
        mListView.setOnChildClickListener(onChildClickListener);
    }

    /*
    * ChildView 设置 布局很可能onChildClick进不来，要在 ChildView layout 里加上
    * android:descendantFocusability="blocksDescendants",
    * 还有isChildSelectable里返回true
    */
    ExpandableListView.OnChildClickListener onChildClickListener = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            Item item = mAdapter.getChild(groupPosition, childPosition);
            new AlertDialog.Builder(instance)
                    .setTitle(item.getName())
                    .setMessage(item.getDetail())
                    .setIcon(android.R.drawable.ic_menu_more)
                    .setNegativeButton(android.R.string.cancel,
                            new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create().show();
            return true;
        }
    };

    private void initData() {
        mGroup.add("天龙八部");
        mGroup.add("射雕英雄传");
        mGroup.add("神雕侠侣");

        String[] group1 =  {"萧峰", "段誉", "虚竹"};
        String[] group2 =  {"郭靖", "黄蓉", "王重阳", "黄药师", "欧阳锋", "一灯", "洪七公"};
        String[] group3 =  {"杨过", "小龙女"};
        mGroupArrays.add(group1);
        mGroupArrays.add(group2);
        mGroupArrays.add(group3);

        String[] detail1 =  {"降龙十八掌,打狗棒法,龙爪手",
                "凌波微步,六脉神剑",
                "北冥神功,小无相功,天山折梅手,天山六阳掌"};
        String[] detail2 =  {"降龙十八掌,九阴真经,天罡北斗阵,双手互搏,空明拳",
                "碧波掌法,落英神剑掌,玉箫剑法,兰花拂穴手",
                "先天功,金雁功,全真剑法,一剑化三清",
                "桃花影落飞神剑，碧海潮生按玉箫",
                "蛤蟆功,瞬息千里,神驼雪山掌,透骨打穴法",
                "一阳指,先天功",
                "降龙十八掌,打狗棒法,伏虎拳"};
        String[] detail3 =  {"姓杨名过字改之,黯然销魂掌,玉女素心剑法,全真剑法,蛤蟆功",
                "玉女素心剑法,古墓派轻功,金铃锁、淑女剑、玉蜂针、白金丝掌套"};
        mDetailArrays.add(detail1);
        mDetailArrays.add(detail2);
        mDetailArrays.add(detail3);


        for (int i = 0; i < mGroupArrays.size(); i++) {
            List<Item> list = new ArrayList<Item>();
            String[] childs = mGroupArrays.get(i);
            String[] details = mDetailArrays.get(i);
            for (int j = 0; j < childs.length; j++) {
                Item item = new Item(mImageIds[i][j], childs[j], details[j]);
                list.add(item);
            }
            mData.add(list);
        }
    }

}
