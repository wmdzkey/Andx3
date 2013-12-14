package com.umk.andx3.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.umk.andx3.R;

/**
 * @author Winnid
 * @title 基础Activity
 * @version:1.0
 * @description:
 *  1.instance实例化监测
 *  2.显示Toast
 *  3.CustomToast
 * @since：13-12-14
 */
public abstract class BaseActivity extends Activity{

    public static Context instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }


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

}
