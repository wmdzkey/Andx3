package com.umk.tiebashenqi.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;
import com.lidroid.xutils.util.LogUtils;

import java.io.File;
import java.util.List;

/**
 * @author Winnid
 * @title 蓝牙工具
 * @version:1.0
 * @description:调用系统蓝牙接口发送本应用apk包
 * @since：13-12-23
 */
public class BlueToothUtil {

    /**检测 Android 中的某个 Intent 是否有效*/
    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static void sendApkFile(Context context) {
        //获取apk资源路径
        String packageFilePath = context.getPackageResourcePath();
        File file = new File(packageFilePath);

        if(file == null || !file.isFile()) {
            //应用安装包不存在提示
            Toast.makeText(context, "安装包好像被清理掉了~", Toast.LENGTH_LONG);
        } else {

            Uri uri = Uri.fromFile(file);

            /*获取当前系统的android版本号*/
            int currentApiVersion = android.os.Build.VERSION.SDK_INT;

            //这种方法不适用于小米机型，经测试发现米3和红米都是 Android - 4.2.1 ，红米使用的是4.2以后的，而米3要用4.2以前的
            //系统4.2以后的
            //if(currentApiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {}

            Intent intentNewBluetooth = new Intent();
            intentNewBluetooth.setAction(Intent.ACTION_SEND);
            intentNewBluetooth.setType("APK安装包");
            ComponentName comp=new ComponentName("com.mediatek.bluetooth","com.mediatek.bluetooth.BluetoothShareGatewayActivity");
            intentNewBluetooth.setComponent(comp);
            intentNewBluetooth.putExtra(Intent.EXTRA_STREAM, uri);

            Intent intentOldBluetooth = new Intent();
            intentOldBluetooth.setAction(Intent.ACTION_SEND);
            intentOldBluetooth.setType("APK安装包");
            intentOldBluetooth.setClassName("com.android.bluetooth", "com.android.bluetooth.opp.BluetoothOppLauncherActivity");
            intentOldBluetooth.putExtra(Intent.EXTRA_STREAM, uri);

            if(isIntentAvailable(context, intentNewBluetooth)) {
                LogUtils.e("系统API = " + currentApiVersion + " ， 调用intentNewBluetooth");
                context.startActivity(intentNewBluetooth);
            } else if(isIntentAvailable(context, intentOldBluetooth)) {
                LogUtils.e("系统API = " + currentApiVersion + " ， intentOldBluetooth");
                context.startActivity(intentOldBluetooth);
            }

        }
    }

}
