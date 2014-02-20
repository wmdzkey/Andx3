package com.umk.andx3.util.xutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.BitmapGlobalConfig;

import java.io.ByteArrayOutputStream;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-15
 */
public class BitmapHelp {

    private BitmapHelp() {
    }

    private static BitmapUtils bitmapUtils;
    private static BitmapDisplayConfig bitmapDisplayConfig;
    private static BitmapGlobalConfig bitmapGlobalConfig;

    /**
     * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
     * @param appContext application context
     * @return
     */
    public static BitmapUtils getBitmapUtils(Context appContext) {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(appContext);
            bitmapDisplayConfig = new BitmapDisplayConfig();
            bitmapDisplayConfig.setShowOriginal(true);
            bitmapUtils.configDefaultDisplayConfig(bitmapDisplayConfig);
        }
        return bitmapUtils;
    }
}
