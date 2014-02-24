package com.umk.andx3.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

/**
 * @author Winnid
 * @title 应用程序信息
 * @version:1.0
 * @since：13-12-18
 */
public class AppInfoUtil {

    public static PackageManager packageManager = null;
    public static PackageInfo packageInfo = null;
    public static ApplicationInfo applicationInfo = null;

    private static PackageManager getPackageManager(Context context) {
        if(packageManager == null) {
            packageManager = context.getApplicationContext().getPackageManager();
        }
        return packageManager;
    }

    public static PackageInfo getPackageInfo(Context context) {
        if(packageInfo == null) {
            try {
                packageInfo = getPackageManager(context).getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                packageInfo = null;
            }
        }
        return packageInfo;
    }

    private static ApplicationInfo getApplicationInfo(Context context) {
        if(applicationInfo == null) {
            try {
                applicationInfo = getPackageManager(context).getApplicationInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                applicationInfo = null;
            }
        }
        return applicationInfo;
    }

    public static String getName(Context context) {
        String applicationName = getPackageManager(context).getApplicationLabel(getApplicationInfo(context)).toString();
        return applicationName;
    }



    public static String getPackageName(Context context) {
        String packageName = getPackageInfo(context).packageName;
        return packageName;
    }

    public static String getVersionName(Context context) {
        String versionName = getPackageInfo(context).versionName;
        return versionName;
    }

    public static int getVersionCode(Context context) {
        int versionCode = getPackageInfo(context).versionCode;
        return versionCode;
    }

    public static Drawable getIcon(Context context) {
        Drawable appIcon = getApplicationInfo(context).loadIcon(getPackageManager(context));
        return appIcon;
    }

    /*
     * 获取程序的签名
     */
    public String getSignature(Context context){
        String appSignature = getPackageInfo(context).signatures[0].toCharsString();
        return appSignature;
    }
}
