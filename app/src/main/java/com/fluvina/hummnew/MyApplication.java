package com.fluvina.hummnew;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.firebase.FirebaseApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Created by Hitesh on 12-Oct-15.
 */
public class MyApplication extends Application {

    //    private static MyApplication applicationClass;
    public static Context app_context;

    //chat stuff
//    private static final String TAG = MyApplication.class.getSimpleName();
    private static   MyApplication instance;
//    public static GPSTracker gps;


    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static   MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        applicationClass = MyApplication.this;
        app_context = getApplicationContext();

//        gps = new GPSTracker(MyApplication.app_context);
        FirebaseApp.initializeApp(this);
        // MultiDex.install(this); //important line.. do not remove

        try {
            //chat
            instance = this;
            //QBSettings.getInstance().fastConfigInit(APP_ID, AUTH_KEY, AUTH_SECRET);
            //StickersManager.initialize(STICKER_API_KEY, this);

            //for error and crash tracking
            // Mint.initAndStartSession(this, "758827cb");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

//    public static MyApplication getApplication() {
//        return applicationClass;
//    }

    public int getAppVersion() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
}
