package com.umk.andx3.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.telephony.TelephonyManager;

/**
 * @author Winnid
 * @title 应用程序信息
 * @version:1.0
 * @since：13-12-18
 */
public class AndroidInfoUtil {

    /***sdk版本*/
    public static int getSdkVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /***frimware版本号(系统版本号)*/
    public static String getSysVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /***手机型号*/
    public static String getDevice() {
        return android.os.Build.MODEL;
    }

    /***手机型号*/
    public static String getDeviceId(Context context) {
        TelephonyManager phoneMgr= (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return phoneMgr.getDeviceId();
    }

    /***获取本机号码*/
    public static String getPhoneNumber(Context context) {
        TelephonyManager phoneMgr= (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return phoneMgr.getLine1Number();
    }
}
