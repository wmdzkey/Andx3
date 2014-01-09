package com.umk.andx3.api;

import android.app.Dialog;
import android.content.Context;
import com.smartybean.core.AbstractCallBack;
import com.umk.andx3.dialog.NetworkProgressDialog;

/**
 * //网络请求进度
 * //call方法内部运行于主线程中。
 */
public abstract class ProgressCallBack<T> extends AbstractCallBack<T> {
    private Dialog dialog;
    private String msg = "数据加载中...";
    public void onPreExecute() {
        showProgress();
    }
    abstract protected Context getContext();
    private void showProgress(){
        if(dialog == null){
            try{
                dialog = NetworkProgressDialog.show(getContext(), msg);
            }catch (Exception e){
                dialog = null;
            }
        }else {
            dialog.show();
        }
    }
    protected void hideProgress(){
        if(dialog!=null){
            dialog.dismiss();
            dialog = null;
        }
    }
    public void onPostExecute() {
        hideProgress();
    }
}
