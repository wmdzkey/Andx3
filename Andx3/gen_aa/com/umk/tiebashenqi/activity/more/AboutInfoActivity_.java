//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.umk.tiebashenqi.activity.more;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.umk.andx3.R.id;
import com.umk.andx3.R.layout;
import com.umk.andx3.view.ScrollingTextView;

public final class AboutInfoActivity_
    extends AboutInfoActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_about_info);
    }

    private void init_(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void afterSetContentView_() {
        header_tv_subtitle = ((TextView) findViewById(id.header_tv_subtitle));
        header_layout_right_imagebuttonlayout = ((LinearLayout) findViewById(id.header_layout_right_imagebuttonlayout));
        header_stv_title = ((ScrollingTextView) findViewById(id.header_stv_title));
        {
            View view = findViewById(id.header_stv_title);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        AboutInfoActivity_.this.header_stv_title();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.about_btn_bluetooth);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        AboutInfoActivity_.this.about_btn_bluetooth();
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

    public static AboutInfoActivity_.IntentBuilder_ intent(Context context) {
        return new AboutInfoActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, AboutInfoActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public AboutInfoActivity_.IntentBuilder_ flags(int flags) {
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
