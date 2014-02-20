package com.umk.andx3.base;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.lidroid.xutils.util.LogUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Winnid
 * @title 收集错误信息
 * @version:1.0
 * @since：14-1-9
 */
public class BaseCrashException implements java.lang.Thread.UncaughtExceptionHandler {

    private final Context uncaughtExceptionContext;

    public BaseCrashException(Context context) {
        uncaughtExceptionContext = context;
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        System.err.println(stackTrace);

        Intent intent = new Intent(
                "android.fbreader.action.CRASH",
                new Uri.Builder().scheme(exception.getClass().getSimpleName()).build()
        );
        try {
            uncaughtExceptionContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //启动收集异常Activity
            //intent = new Intent(uncaughtExceptionContext, BugReportActivity.class);
            //intent.putExtra(BugReportActivity.STACKTRACE, stackTrace.toString());
            //uncaughtExceptionContext.startActivity(intent);
            LogUtils.e("莫慌, 正在收集异常信息 : " + stackTrace.toString());
        }

        if (uncaughtExceptionContext instanceof Activity) {
            ((Activity) uncaughtExceptionContext).finish();
        }

    }
}
