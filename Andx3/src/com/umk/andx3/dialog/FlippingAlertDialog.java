package com.umk.andx3.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.umk.andx3.R;
import com.umk.andx3.view.flipping.FlippingImageView;


public class FlippingAlertDialog extends Dialog {

    public FlippingAlertDialog(Context context, int theme) {
        super(context, theme);
    }

    public FlippingAlertDialog(Context context) {
        super(context);
    }

//    public static Dialog show(final Context context, final String title, String msg, Boolean progressable) {
//        Builder customBuilder = new Builder(context, progressable);
//                customBuilder.setTitle(title).setMessage(msg);
//        final FlippingAlertDialog x3dialog = customBuilder.create();
//        x3dialog.setCancelable(false);
//        x3dialog.setOnShowListener(new OnShowListener() {
//            @Override
//            public void onShow(final DialogInterface dialog) {
//                new CountDownTimer(15000, 1000) {
//                    public void onTick(long millisUntilFinished) {
//                        //if(x3dialog != null && x3dialog.isShowing()) {
//                        //LogUtils.e("X3FlippingDialog - 正在执行");
//                        //}
//                    }
//                    public void onFinish() {
//                        if(x3dialog != null && x3dialog.isShowing()) {
//                            x3dialog.dismiss();
//
//                            Builder x3FlippingBuilder = new Builder(context, false);
//                            x3FlippingBuilder.setMessage("请求超时，请稍后再试");
//                            X3FlippingDialog dialog = x3FlippingBuilder.create();
//                            dialog.show();
//                        }
//
//                        LogUtils.e("X3FlippingDialog - 请求超时");
//                        cancel();
//                    }
//                }.start();
//            }
//        });
//        x3dialog.show();
//        return x3dialog;
//    }

    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder {

        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private int iconId = -1;
        private View contentView;

        private OnClickListener
                positiveButtonClickListener,
                negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Set the Dialog message from String
         *
         * @param message
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set a custom content view for the Dialog.
         * If a message is set, the contentView is not
         * added to the Dialog...
         *
         * @param v
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it"s listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text and it"s listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button resource and it"s listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button text and it"s listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Create the custom dialog
         */
        public FlippingAlertDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final FlippingAlertDialog dialog = new FlippingAlertDialog(context, R.style.FlippingAlertDialog);
            View layout = inflater.inflate(R.layout.common_flipping_alert_dialog, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            if (iconId != -1) {
                ((FlippingImageView) layout.findViewById(R.id.alertdialog_fiv_icon_title)).setImageResource(iconId);
                ((FlippingImageView) layout.findViewById(R.id.alertdialog_fiv_icon_message)).setImageResource(iconId);
            }

            // set the dialog title
            if (title != null) {
                ((TextView) layout.findViewById(R.id.alertdialog_tv_title)).setText(title);
                layout.findViewById(R.id.alertdialog_fiv_icon_message).setVisibility(View.GONE);//隐藏内容的图标
            } else {
                layout.findViewById(R.id.alertdialog_tv_title).setVisibility(View.GONE);
                layout.findViewById(R.id.alertdialog_fiv_icon_title).setVisibility(View.GONE);//隐藏标题的图标
            }
            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.alertdialog_tv_message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.alertdialog_ll_content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.alertdialog_ll_content))
                        .addView(contentView,
                                new LayoutParams(
                                        LayoutParams.WRAP_CONTENT,
                                        LayoutParams.WRAP_CONTENT));
            } else {
                layout.findViewById(R.id.alertdialog_fiv_icon_message).setVisibility(View.GONE);
            }

            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_positive))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.btn_positive))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(
                                            dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                    dialog.dismiss();
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.btn_positive).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_negative))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.btn_negative))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(
                                            dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                    dialog.dismiss();
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.btn_negative).setVisibility(
                        View.GONE);
            }

            dialog.setContentView(layout);
            return dialog;
        }

        public Builder setIcon(int icon) {
            this.iconId = icon;
            return this;
        }
    }

//    /**
//     * Build the desired Dialog
//     * CUSTOM or DEFAULT
//     */
//    public final static int CUSTOM_DIALOG = 1;
//    public final static int DEFAULT_DIALOG = 0;
//    @Override
//    public Dialog onCreateDialog(int dialogId) {
//        Dialog dialog = null;
//        switch (dialogId) {
//            case CUSTOM_DIALOG :
//                X3FlippingDialog.Builder customBuilder = new
//                        X3FlippingDialog.Builder(CustomDialogActivity.this);
//                customBuilder.setTitle("Custom title")
//                        .setMessage("Custom body")
//                        .setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        CustomDialogActivity.this
//                                                .dismissDialog(CUSTOM_DIALOG);
//                                    }
//                                })
//                        .setPositiveButton("Confirm",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//                dialog = customBuilder.create();
//                break;
//            case DEFAULT_DIALOG :
//                AlertDialog.Builder alertBuilder = new
//                        AlertDialog.Builder(CustomDialogActivity.this);
//                alertBuilder.setTitle("Default title")
//                        .setMessage("Default body")
//                        .setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                })
//                        .setPositiveButton("Confirm",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        CustomDialogActivity.this
//                                                .dismissDialog(DEFAULT_DIALOG);
//                                    }
//                                });
//                dialog = alertBuilder.create();
//                break;
//        }
//        return dialog;
//    }

}
