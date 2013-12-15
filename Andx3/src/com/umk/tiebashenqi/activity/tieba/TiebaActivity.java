package com.umk.tiebashenqi.activity.tieba;

import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import com.google.gson.internal.LinkedTreeMap;
import com.googlecode.androidannotations.annotations.*;
import com.lidroid.xutils.util.LogUtils;
import com.umk.andx3.R;
import com.umk.andx3.base.BaseActivity;
import com.umk.tiebashenqi.activity.MainActivity_;
import com.umk.tiebashenqi.adapter.TiebaPictureAdapter;
import com.umk.tiebashenqi.util.TiebaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoTitle
@EActivity(R.layout.activity_tieba)
public class TiebaActivity extends BaseActivity {


    @ViewById(R.id.et_main_tieba)
    EditText et_main_tieba;
    @ViewById(R.id.btn_main_tieba)
    Button btn_main_tieba;
    @ViewById(R.id.tv_main_tieba)
    TextView tv_main_tieba;
    @ViewById(R.id.gv_main_pic)
    GridView gv_main_pic;


    TiebaPictureAdapter tiebaPictureAdapter;
    int clickNum = 0;

    @AfterViews
    void init() {

    }

    @Click
    void btn_main_tieba() {
        clickNum++;
        // et_main_tieba.getText().toString();

        //String homePage = "http://tieba.baidu.com/f?ie=utf-8&kw=微笑脉脉水悠悠";
        //String homePage = "http://tieba.baidu.com/f?ie=utf-8&kw=%E5%BE%AE%E7%AC%91%E8%84%89%E8%84%89%E6%B0%B4%E6%82%A0%E6%82%A0";
        String homePage = "http://tieba.baidu.com/f?ie=utf-8&kw=%E5%A7%90%E8%84%B1";
        LinkedTreeMap<String, String> map = TiebaUtil.getHomePageHashMap(homePage);

        tv_main_tieba.setText("主页下载完毕，准备解析" + "\n******\n");
        tv_main_tieba.setText(tv_main_tieba.getText().toString() + "总共：" + map.size() + " 个帖子" + "\n******\n");

//        String path = "";
//        String dPage = "";

//        for(String s : map.keySet()){
//            LogUtils.e(s);
//            //tv_main_tieba.setText(tv_main_tieba.getText().toString() + "******\n" + s + "\n******\n");
//
//            path = s;
//            dPage = map.get(s);
////            TiebaDownloader td = new TiebaDownloader();
////            td.dPage = s;
////            td.path = hsmap.get(s).trim().replace(".", "").replace(":", "").replace("*", "").replace("?", "").replace("\"", "").replace("<", "").replace(">", "").replace("|", "");
////
////            pool.submit(new Thread(td));
//        }


        List<String> list = new ArrayList<String>();
        tiebaPictureAdapter = new TiebaPictureAdapter(instance, list);
        gv_main_pic.setAdapter(tiebaPictureAdapter);

        int i = 0;
        for(String s : map.keySet()){
            if(i == clickNum) {
                list.addAll(get(s, map.get(s)));
                break;
            } else {
                i++;
                continue;
            }
//            list.addAll(get(path, dPage));
        }
        tiebaPictureAdapter.notifyDataSetChanged();
        tv_main_tieba.setText("读取帖子内部图片完成");


//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            LogUtils.e("线程池等待失败");
//        }
//        pool.shutdown();
//        LogUtils.e("线程池关闭");

    }



    /**
     * 启动子线程，下载贴吧的每一页
     */
    public Set<String> get(String path, String dPage) {
        Set<String> set;
        LogUtils.e("准备下载子页面，标题为：" + path);
        set = TiebaUtil.getDetailsPageImageList(dPage);
        return set;
    }




}
