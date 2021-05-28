package com.fluvina.hummnew.Custom;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.fluvina.hummnew.R;


public class CustomLoaderDialog {

    Context context;
    Dialog dialog;

    public CustomLoaderDialog(Context context) {
        this.context = context;
        initProgressDialog();
    }

    private void initProgressDialog() {
        try {
            dialog = new Dialog(context, R.style.MyDialogTheme);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_loader_2);
            dialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog() {
        try {
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
