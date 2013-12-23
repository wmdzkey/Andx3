package com.umk.andx3.base;

import android.app.Activity;
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

/**
 * @author Winnid
 * @title 加强版Toast
 * @version:1.0
 * @description:
 * @since：13-12-14
 */
public class BaseToast {

    public static void showToast(Context context, String text) {
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.common_toast, null);
        ((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }

}
