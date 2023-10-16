package com.fluvina.hummnew.fcm;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.core.app.NotificationCompat;


import com.fluvina.hummnew.R;
import com.fluvina.hummnew.Utilities.MobiConstants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtils {

    private  final String TAG = NotificationUtils.class.getSimpleName();

    private final Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotificationMessage(String title, String message, long timeStamp, Intent intent) {
        showNotificationMessage(title, message, timeStamp, intent, null);
    }

    public void showNotificationMessage(final String title, final String message, final long timeStamp, Intent intent, String imageUrl) {
        // Check for empty push message
        //if (TextUtils.isEmpty(message))
        //return;


        // notification icon
        int icon = R.mipmap.ic_launcher;
        int iconSmall = R.mipmap.ic_launcher;


        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        @SuppressLint("UnspecifiedImmutableFlag") final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, MobiConstants.CHANNEL_ID);

        final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + mContext.getPackageName() + "/raw/notification");

        if (!TextUtils.isEmpty(imageUrl)) {

            if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                Bitmap bitmap = getBitmapFromURL(imageUrl);

                if (bitmap != null) {
                    showBigNotification(bitmap, mBuilder, icon, iconSmall, title, message, timeStamp, resultPendingIntent, alarmSound);
                } else {
                    showSmallNotification(mBuilder, icon, iconSmall, title, message, timeStamp, resultPendingIntent, alarmSound);
                }
            }
        } else {
            showSmallNotification(mBuilder, icon, iconSmall, title, message, timeStamp, resultPendingIntent, alarmSound);
            //playNotificationSound();
        }
    }


    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, int smallIcon, String title, String message, long timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {

        try {
            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText(message);

            Notification notification;
            notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    /*.setSound(alarmSound)*/
                    .setStyle(bigText)
                    .setWhen(timeStamp)
                    .setSmallIcon(smallIcon)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                    .setContentText(message)
                    .build();

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(MobiConstants.CHANNEL_ID, MobiConstants.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.setSound(null, null);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.BLUE);
                notificationChannel.enableVibration(true);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(notificationChannel);
                }
            }
            if (notificationManager != null) {
                notificationManager.notify((int) System.currentTimeMillis(), notification); //Config.NOTIFICATION_ID
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, int icon, int smallIcon, String title, String message, long timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {

        try {

            NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
            bigPictureStyle.setBigContentTitle(title);
            bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
            bigPictureStyle.bigPicture(bitmap);
            Notification notification;
            notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    /*.setSound(alarmSound)*/
                    .setStyle(bigPictureStyle)
                    .setWhen(timeStamp)
                    .setSmallIcon(smallIcon)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                    .setContentText(message)
                    .build();

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(MobiConstants.CHANNEL_ID, MobiConstants.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.setSound(null, null);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.BLUE);
                notificationChannel.enableVibration(true);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(notificationChannel);
                }
            }
            if (notificationManager != null) {
                notificationManager.notify((int) System.currentTimeMillis(), notification); //Config.NOTIFICATION_ID
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Playing notification sound
    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(MobiConstants.CHANNEL_ID, MobiConstants.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setSound(null, null);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return Long.parseLong(timeStamp);
            } catch (Exception ex) {
                ex.printStackTrace();
                return System.currentTimeMillis();
            }

        }
    }
}
