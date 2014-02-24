//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.umk.tiebashenqi.activity.tieba;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.fortysevendeg.android.swipelistview.SwipeListView;
import com.googlecode.androidannotations.api.SdkVersionHelper;
import com.umk.andx3.view.ScrollingTextView;
import com.umk.tiebashenqi.R.id;
import com.umk.tiebashenqi.R.layout;

public final class TiebaActivity_
    extends TiebaActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_tieba);
    }

    private void init_(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void afterSetContentView_() {
        header_layout_search = ((RelativeLayout) findViewById(id.header_layout_search));
        header_layout_rightview_container = ((LinearLayout) findViewById(id.header_layout_rightview_container));
        header_stv_title = ((ScrollingTextView) findViewById(id.header_stv_title));
        tieba_slv = ((SwipeListView) findViewById(id.tieba_slv));
        header_layout_right_imagebuttonlayout = ((LinearLayout) findViewById(id.header_layout_right_imagebuttonlayout));
        header_layout_title = ((LinearLayout) findViewById(id.header_layout_title));
        header_ib_right_imagebutton = ((ImageButton) findViewById(id.header_ib_right_imagebutton));
        header_et_search = ((EditText) findViewById(id.header_et_search));
        {
            View view = findViewById(id.header_layout_right_imagebuttonlayout);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        TiebaActivity_.this.header_layout_right_imagebuttonlayout();
                    }

                }
                );
            }
        }
        init();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView_();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    public static TiebaActivity_.IntentBuilder_ intent(Context context) {
        return new TiebaActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, TiebaActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public TiebaActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (context_ instanceof Activity) {
                ((Activity) context_).startActivityForResult(intent_, requestCode);
            } else {
                context_.startActivity(intent_);
            }
        }

    }

}
