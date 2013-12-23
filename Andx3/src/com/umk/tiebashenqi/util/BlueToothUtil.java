package com.umk.tiebashenqi.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.lidroid.xutils.util.LogUtils;
import com.umk.andx3.base.BaseToast;

import java.io.File;

/**
 * @author Winnid
 * @title 蓝牙工具
 * @version:1.0
 * @description:调用系统蓝牙接口发送本应用apk包
 * @since：13-12-23
 */
public class BlueToothUtil {

    public static void sendApkFile(Context context) {
        //获取apk资源路径
        String packageFilePath = context.getPackageResourcePath();
        File file = new File(packageFilePath);

        if(file == null || !file.isFile()) {
            //应用安装包不存在提示
            BaseToast.showToast(context, "安装包好像被清理掉了~");
        } else {

            Uri uri = Uri.fromFile(file);
            /*获取当前系统的android版本号*/
            int currentApiVersion = android.os.Build.VERSION.SDK_INT;
            //系统4.2以后的
            if(currentApiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                LogUtils.e("我是4.2以后的系统,API=" + currentApiVersion);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("APK安装包");
                ComponentName comp=new ComponentName("com.mediatek.bluetooth","com.mediatek.bluetooth.BluetoothShareGatewayActivity");
                intent.setComponent(comp);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                context.startActivity(intent);
            } else {
                LogUtils.e("我是4.2以前的系统,API=" + currentApiVersion);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("APK安装包");
                intent.setClassName("com.android.bluetooth", "com.android.bluetooth.opp.BluetoothOppLauncherActivity");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                context.startActivity(intent);
            }
        }
    }

}
