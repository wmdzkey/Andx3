package com.umk.andx3.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.util.LogUtils;
import com.smartybean.android.http.HttpInterface;

import com.umk.tiebashenqi.R;
import com.umk.andx3.api.Api;
import com.umk.andx3.dialog.FlippingAlertDialog;
import com.umk.andx3.util.NetWorkUtil;
import com.umk.andx3.util.xutil.BitmapHelp;
import com.umk.tiebashenqi.activity.MainActivity;

import java.lang.reflect.Field;

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
 *  9.是否处于活动状态
 *  10.是否处于顶端
 *
 *  11.注解Api
 *  12.加入BaseApplication
 *  13.catchException捕获意外退出信息
 *  14.AlertPopWindow加入 TODO
 * @since：13-12-14
 */
public abstract class BaseActivity extends Activity {

    public BaseApplication mApplication;

    /**工具类*/
    protected NetWorkUtil mNetWorkUtil;
    protected BitmapUtils mBitmapUtils;

    /**组件类*/
    protected FlippingAlertDialog loadingDialog;

    /**
     * 屏幕的宽度、高度、密度
     */
    protected int mScreenWidth;
    protected int mScreenHeight;
    protected float mDensity;

    /**
     * Activity状态
     * */
    private boolean runTopState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBase();
        initUtil();
        initParam();
        initLog();
        injectApi();
    }

    private void initBase() {
        mApplication = (BaseApplication) getApplication();
        Thread.setDefaultUncaughtExceptionHandler(new BaseCrashException(this)); // 程序崩溃时触发线程
    }

    private void initUtil() {
        mNetWorkUtil = new NetWorkUtil(this);
        mBitmapUtils = BitmapHelp.getBitmapUtils(this);
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
     *              运行状态
     * *************************************************************************************************/

    /** 是否处于运行状态 */
    public static boolean getRun(BaseActivity context) {
        return (context == null);
    }

    /** 是否处于顶端（当前）运行 */
    public static boolean getRunTop(BaseActivity context) {
        return context.runTopState;
    }

    @Override
    protected void onStart() {
        super.onStart();
        runTopState = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        runTopState = false;
    }

    /** 是否处于运行状态 */
    public static int getScreenWidth(Context context) {
        return ((BaseActivity)context).mScreenWidth;
    }

    /** 是否处于运行状态 */
    public static int getScreenHeight(Context context) {
        return ((BaseActivity)context).mScreenHeight;
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
     *              SimpleLoadingDialog
     * *************************************************************************************************/

    /** 含有标题和内容的对话框 **/
    protected void showLoadingDialog(String message) {
        FlippingAlertDialog.Builder builder = new FlippingAlertDialog.Builder(this)
                .setMessage(message);
        loadingDialog = builder.create();
        loadingDialog.setCancelable(false);
        loadingDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                new CountDownTimer(20000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        if (loadingDialog != null && loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                            Dialog timeoutDialog = showAlertDialog(null, "请求超时，请稍后再试");
                            timeoutDialog.setCancelable(true);
                            timeoutDialog.setCanceledOnTouchOutside(true);
                        }
                        cancel();
                    }
                }.start();
            }
        });
        loadingDialog.show();
    }

    /** 含有标题和内容的对话框 **/
    protected void dismissLoadingDialog() {
        if(loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    /***************************************************************************************************
     *              AlertDialog
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
    public FlippingAlertDialog showAlertDialog(String title, String message,
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

    /***************************************************************************************************
     *              AlertPopWindow TODO
     * *************************************************************************************************/


    /***************************************************************************************************
     *              注入Api对象。
     * *************************************************************************************************/
    private void injectApi(){
        Field[] fields = null;
        if(this.getClass().getName().endsWith("_")){
            fields = this.getClass().getSuperclass().getDeclaredFields();
        }else{
            fields = this.getClass().getDeclaredFields();
        }
        for(Field f: fields){
            if(f.isAnnotationPresent(Api.class)){
                f.setAccessible(true);
                try {
                    f.set(this, HttpInterface.proxy(this, f.getType()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                f.setAccessible(false);
            }
        }
    }



}
