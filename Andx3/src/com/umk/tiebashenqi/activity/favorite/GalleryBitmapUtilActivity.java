/*
 Copyright (c) 2012 Roman Truba

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.umk.tiebashenqi.activity.favorite;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.umk.tiebashenqi.R;
import com.umk.tiebashenqi.view.touchgallery.gallerywidget.BitmapUtilPagerAdapter;
import com.umk.tiebashenqi.view.touchgallery.gallerywidget.GalleryViewPager;

import java.util.List;

public class GalleryBitmapUtilActivity extends Activity {

    public static Context instance = null;
    public static String intentUrlList = "intentUrlList";
    public static String intentUrlPosition = "intentUrlPosition";

    private GalleryViewPager mViewPager;

    List<String> urlList;
    int urlPosition;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_url);
        instance = this;
        initParam();
        initView();
    }


    private void initParam() {
        urlList = getIntent().getStringArrayListExtra(intentUrlList);
        urlPosition = getIntent().getIntExtra(intentUrlPosition, -1);
    }


    private void initView() {

        BitmapUtilPagerAdapter pagerAdapter = new BitmapUtilPagerAdapter(this, urlList);

        mViewPager = (GalleryViewPager)findViewById(R.id.viewer);
        mViewPager.setOffscreenPageLimit(3);//预加载数量
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(urlPosition);//当前显示项

    }
}