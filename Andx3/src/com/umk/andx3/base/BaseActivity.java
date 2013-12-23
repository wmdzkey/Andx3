package com.umk.andx3.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.util.LogUtils;
import com.umk.andx3.R;
import com.umk.andx3.dialog.FlippingAlertDialog;
import com.umk.andx3.util.NetWorkUtil;
import com.umk.andx3.util.xutil.BitmapHelp;

/**
 * @author Winnid
 * @title 基础Activity
 * @version:1.0
 * @description:
 *  1.instance实例化监测
 *  2.显示Toast
 *  3.CustomToast
 *  4.startActivity
 *  5.LogUtils
 *  6.NetWorkUtil
 *  7.屏幕的宽度、高度、密度
 *  8.showDialog
 *  8.showAlertDialog
 * @since：13-12-14
 */
public abstract class BaseActivity extends Activity{

    public static Context instance = null;

    protected NetWorkUtil mNetWorkUtil;

    /**
     * 屏幕的宽度、高度、密度
     */
    protected int mScreenWidth;
    protected int mScreenHeight;
    protected float mDensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        initUtil();
        initParam();
        initLog();
    }


    private void initUtil() {
        mNetWorkUtil = new NetWorkUtil(this);
    }

    private void initParam() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        mDensity = metric.density;
    }

    private void initLog() {
        LogUtils.customTagPrefix = "com.umk.andx3";
    }


    /***************************************************************************************************
     *              Toast
     * *************************************************************************************************/

    /** 短暂显示Toast提示(来自String) **/
    public void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /** 长时间显示Toast提示(来自String) **/
    public void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    /** 显示Toast提示(来自res) **/
    public void showToast(int resId, int length) {
        Toast.makeText(this, getString(resId), length).show();
    }

    /** 短暂在中间显示Toast提示(来自String) **/
    public void showShortToastGravity(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /** 长时间在中间显示Toast提示(来自String) **/
    public void showLongToastGravity(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /** 在自定义位置显示Toast提示(来自String) **/
    public void showToastGravity(String text, int gravity) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    /** 显示自定义Toast提示(来自String) **/
    protected void showCustomToast(String text) {
        View toastRoot = LayoutInflater.from(BaseActivity.this).inflate(R.layout.common_toast, null);
        ((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }




    /***************************************************************************************************
     *              startActivity
     * *************************************************************************************************/

    /** 通过Class跳转界面 **/
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }




    /***************************************************************************************************
     *              Dialog
     * *************************************************************************************************/

    /** 含有标题和内容的对话框 **/
    protected FlippingAlertDialog showAlertDialog(String title, String message) {
        FlippingAlertDialog.Builder builder = new FlippingAlertDialog.Builder(this).setTitle(title)
                .setMessage(message);
        FlippingAlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /** 含有标题、内容、两个按钮的对话框 **/
    protected FlippingAlertDialog showAlertDialog(String title, String message,
                                          String positiveText,
                                          DialogInterface.OnClickListener onPositiveClickListener,
                                          String negativeText,
                                          DialogInterface.OnClickListener onNegativeClickListener) {
        FlippingAlertDialog.Builder builder = new FlippingAlertDialog.Builder(this).setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener);
        FlippingAlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /** 含有标题、内容、图标、两个按钮的对话框 **/
    protected FlippingAlertDialog showAlertDialog(String title, String message,
                                          int icon, String positiveText,
                                          DialogInterface.OnClickListener onPositiveClickListener,
                                          String negativeText,
                                          DialogInterface.OnClickListener onNegativeClickListener) {
        FlippingAlertDialog.Builder builder = new FlippingAlertDialog.Builder(this).setTitle(title)
                .setMessage(message).setIcon(icon)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener);
        FlippingAlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }


}
