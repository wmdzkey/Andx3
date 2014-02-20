package com.umk.tiebashenqi.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.umk.tiebashenqi.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * @author Winnid
 * @title
 * @version:1.0
 * @since：13-12-18
 */
public class TempUtil {

    /**
     *转换Url的中文
     * */
    public static String convertChineseUrl(String url) {
        char[] tp = url.toCharArray();
        String now = "";
        for (char ch : tp) {
            if (ch >= 0x4E00 && ch <= 0x9FA5) {
                try {
                    now += URLEncoder.encode(ch + "", "gbk");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                now += ch;
            }
        }
        return now;
    }

    /**
     * 添加超链接
     * @param textView  超链接的TextView
     * @param start 超链接开始的位置
     * @param end 超链接结束的位置
     * @param listener 超链接的单击监听事件
     */
    public static void addHyperlinks(final TextView textView,
                                     final int start, final int end,
                                     final View.OnClickListener listener) {

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                listener.onClick(widget);
            }
        };

        String text = textView.getText().toString().trim();

        SpannableString sp = new SpannableString(text);
        sp.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(textView.getContext().getResources().getColor(R.color.black)),
                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(sp);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

    }


    /**
     * 添加下划线
     * @param context 上下文
     * @param textView 添加下划线的TextView
     * @param start 添加下划线开始的位置
     * @param end 添加下划线结束的位置
     */
    public static void addUnderlineText(final Context context,
                                        final TextView textView, final int start, final int end) {
        textView.setFocusable(true);
        textView.setClickable(true);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(
                textView.getText().toString().trim());
        spannableStringBuilder.setSpan(new UnderlineSpan(), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableStringBuilder);
    }

    public static boolean matchEmail(String text) {
        if (Pattern.compile("\\w[\\w.-]*@[\\w.]+\\.\\w+").matcher(text)
                .matches()) {
            return true;
        }
        return false;
    }

    public static boolean matchPhone(String text) {
        if (Pattern.compile("(\\d{11})|(\\+\\d{3,})").matcher(text).matches()) {
            return true;
        }
        return false;
    }

    public static boolean isNull(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text != null && text.length() > 0) {
            return false;
        }
        return true;
    }

}
