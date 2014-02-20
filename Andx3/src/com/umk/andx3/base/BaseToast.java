package com.umk.andx3.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.umk.tiebashenqi.R;

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
