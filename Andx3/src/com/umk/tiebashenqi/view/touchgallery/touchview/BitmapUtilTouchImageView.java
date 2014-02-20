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
package com.umk.tiebashenqi.view.touchgallery.touchview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.SimpleBitmapLoadCallBack;

import com.umk.tiebashenqi.R;
import com.umk.andx3.util.xutil.BitmapHelp;


public class BitmapUtilTouchImageView extends RelativeLayout {
    protected ProgressBar mProgressBar;
    protected TouchImageView mImageView;

    protected Context mContext;

    public BitmapUtilTouchImageView(Context ctx)
    {
        super(ctx);
        mContext = ctx;
        init();

    }
    public BitmapUtilTouchImageView(Context ctx, AttributeSet attrs)
    {
        super(ctx, attrs);
        mContext = ctx;
        init();
    }
    public TouchImageView getImageView() { return mImageView; }

    @SuppressWarnings("deprecation")
    protected void init() {
        mImageView = new TouchImageView(mContext);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mImageView.setLayoutParams(params);
        this.addView(mImageView);
        mImageView.setVisibility(GONE);

        mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.setMargins(30, 0, 30, 0);
        mProgressBar.setLayoutParams(params);
        mProgressBar.setIndeterminate(false);
        mProgressBar.setMax(100);
        this.addView(mProgressBar);
    }

    public void setUrl(String imageUrl)
    {
        BitmapHelp.getBitmapUtils(mContext).display(mImageView, imageUrl, new SimpleBitmapLoadCallBack<TouchImageView>() {

            @Override
            public void onLoadStarted(TouchImageView container, String uri, BitmapDisplayConfig config) {
                super.onLoadStarted(container, uri, config);
            }

            @Override
            public void onLoading(TouchImageView container, String uri, BitmapDisplayConfig config, long total, long current) {
                super.onLoading(container, uri, config, total, current);
                mProgressBar.setProgress((int)(current/total * 100));
            }

            @Override
            public void onLoadFailed(TouchImageView container, String uri, Drawable drawable) {
                super.onLoadFailed(container, uri, drawable);
                mImageView.setScaleType(ScaleType.CENTER);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.no_photo);
                mImageView.setImageBitmap(bitmap);

                mImageView.setVisibility(VISIBLE);
                mProgressBar.setVisibility(GONE);
            }

            @Override
            public void onLoadCompleted(TouchImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
                super.onLoadCompleted(container, uri, bitmap, config, from);

                mImageView.setScaleType(ScaleType.MATRIX);
                mImageView.setImageBitmap(bitmap);

                mImageView.setVisibility(VISIBLE);
                mProgressBar.setVisibility(GONE);
            }
        });
    }

}
