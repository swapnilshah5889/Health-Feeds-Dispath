package com.fluvina.hummnew.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.fluvina.hummnew.Activities.MainActivity;
import com.fluvina.hummnew.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by DeskTop on 26-Oct-15.
 */
public class MobiUtility {


    // for Check string is empty or not
    public static boolean isStringEmpty(String s) {
        return s == null || s.length() == 0 || s.trim().equals("") || s.equals(" ");
    }

    public static void closesoftkeyboard(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeSoftkeyboard(Activity activity, View view) {
        try {
            if (view == null) {
                view = activity.getCurrentFocus();
            }
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void openSoftKeyboard(Context context, View linearLayout) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                linearLayout.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }

    public static void closeSoftKeyboard(Activity activity, View view) {
        try {
            if (activity != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    if (view != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    } else {
                        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Internet checking
     */
    public static boolean isInternetOn(Context context) {

        boolean flag = false;
        try {
            if (context != null) {
                // get Connectivity Manager object to check connection
                ConnectivityManager connec =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = connec.getActiveNetworkInfo();
                if (null != activeNetwork) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        flag = true;
                        return flag;
                    }

                    if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        flag = true;
                        return flag;
                    }
                }

            /*// Check for network connections
            if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

                flag = true;
                return flag;

            } else if (
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
                //Toast.makeText(context, "Connection lost ", Toast.LENGTH_LONG).show();
                System.out.println("Connection Lost..@");
                flag = false;
                return flag;
            }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
            return flag;
        }
        return flag;
    }



    public static String extractYTId(String ytUrl) {
        String vId = "";
        /*val pattern = Pattern.compile(
                "http://(?:www\\.)?youtu(?:\\.be/|be\\.com/(?:watch\\?v=|v/|embed/|user/(?:[\\w#]+/)+))([^&#?\n]+)")*/

        Pattern pattern = Pattern.compile(
                "http(?:s)?://(?:www\\.)?youtu(?:\\.be/|be\\.com/(?:watch\\?v=|v/|embed/|user/(?:[\\w#]+/)+))([^&#?\\n]+)");

        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.find()) {
            vId = matcher.group(1);
        }
        return vId;
    }

    public static void firebaseLogEvent(Context context, String eventName, Bundle params) {
        try {
            // Obtain the FirebaseAnalytics instance.
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            mFirebaseAnalytics.logEvent(eventName, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String saveBitmapToFile(Bitmap bitmap, String filePath) {

        try {
            String extension = filePath.substring(filePath.lastIndexOf(".") + 1);

            FileOutputStream out = null;
            try {
                Bitmap.CompressFormat format;
                if (extension.equalsIgnoreCase("JPG") || extension.equalsIgnoreCase("JPEG")) {
                    format = Bitmap.CompressFormat.JPEG;
                } else {
                    format = Bitmap.CompressFormat.PNG;
                }
                out = new FileOutputStream(filePath);
                bitmap.compress(format, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

}
