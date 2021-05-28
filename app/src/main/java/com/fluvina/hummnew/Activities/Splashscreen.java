package com.fluvina.hummnew.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.fluvina.hummnew.Model.AppVersionModel;
import com.fluvina.hummnew.MyApplication;
import com.fluvina.hummnew.R;
import com.fluvina.hummnew.Utilities.MobiConstants;
import com.fluvina.hummnew.Utilities.MobiLogger;
import com.fluvina.hummnew.Utilities.MobiUtility;
import com.fluvina.hummnew.Utilities.SessionManager;
import com.fluvina.hummnew.network.ApiClient;
import com.fluvina.hummnew.network.ApiService;
import com.fluvina.hummnew.network.Apis;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splashscreen extends AppCompatActivity {

    Context context;
    SessionManager session;


    boolean isHandlerDone = false;
    boolean isAfterLoginApiDone = false;
    boolean isVersionApiDone = false;
    boolean isAllowedVersion = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //This will not let night mode of the phone affect the application
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        context = this;
        MyApplication myApplication = new MyApplication();
        FirebaseApp.initializeApp(context);
        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            callAfterLoginDataApi();
        } else {
            isAfterLoginApiDone = true;
        }
        callVersionCheckApi();


        new Handler().postDelayed(new Runnable() {
            public void run() {

                isHandlerDone = true;
                gotoNextScreen();
            }
        }, 2000);

    }

    private String getVersion() {
        String version = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    public void callAfterLoginDataApi() {

        if (MobiUtility.isInternetOn(context)) {
            GetUserData();
        } else {
            Toast.makeText(context, getString(R.string.txt_no_internet), Toast.LENGTH_SHORT).show();
        }

    }

    private void GetUserData() {
//        CustomLoaderDialog dialog = new CustomLoaderDialog(Splashscreen.this);
//        dialog.showDialog();

        try {
            LinkedHashMap<String, String> params = new LinkedHashMap<>();
//            params.put("user_id", session.getuserid());
            params.put("user_id", MobiConstants.USER_ID);
            params.put("action", MobiConstants.ACTION_PROFILE_REQUIRED_SETUP_CHECK);
            params.put("stuff", MobiConstants.STUFF);
            params.put("app_type", MobiConstants.APP_TYPE);

            MobiLogger.printmsg("Api : " + Apis.BASE_URL + Apis.Save_LOGIN_PROFILE_DATA);
            MobiLogger.printMap(params);

            ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = service.Save_LOGIN_PROFILE_DATA(params);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    dialog.dismissDialog();
                    try {
                        if (response.body() != null) {
                            String strResponse = response.body().string();
                            MobiLogger.printmsg("GetUserData Response : " + strResponse);

                            JSONObject jsonobj = new JSONObject(strResponse);
                            boolean stat = jsonobj.getBoolean("stat");
                            if (stat) {
                                int profile_required_setup = 0;
                                boolean profile_completed = false;

                                try {
                                    try {
                                        JSONObject afterLoginJson = new JSONObject();
                                        afterLoginJson = jsonobj.getJSONObject("my_profile_info");
                                        session.setLogin_setup_json(afterLoginJson.toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    profile_required_setup = jsonobj.getInt("profile_required_setup");
                                    profile_completed = jsonobj.getBoolean("all_disease_setup_completed");
                                    JSONArray my_disease_array = jsonobj.getJSONArray("my_disease");


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                session.setReqSetup(profile_required_setup);
                                session.setDisease_profile_completed(profile_completed);

                            }

                            isAfterLoginApiDone = true;
                            gotoNextScreen();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    dialog.dismissDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void callVersionCheckApi() {

        if (MobiUtility.isInternetOn(context)) {
            VersionCheckApi();
        } else {
            //Toast.makeText(context, MobiHealthUserConstants.no_internet, Toast.LENGTH_SHORT).show();
        }

    }

    private void VersionCheckApi() {
//        CustomLoaderDialog dialog = new CustomLoaderDialog(this);
//        dialog.showDialog();

        try {
            LinkedHashMap<String, String> params = new LinkedHashMap<>();
            params.put("action", MobiConstants.ACTION_ALLOW_APP_VERSION);
            params.put("stuff", MobiConstants.STUFF);
            params.put("app_type", MobiConstants.APP_TYPE);

            MobiLogger.printmsg("Api : " + Apis.BASE_URL + Apis.APP_VERSION_API);
            MobiLogger.printMap(params);

            ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<AppVersionModel> call = service.callVersionCheckApi(params);

            call.enqueue(new Callback<AppVersionModel>() {
                @Override
                public void onResponse(Call<AppVersionModel> call, Response<AppVersionModel> response) {
//                    dialog.dismissDialog();
                    try {
                        if (response.body() != null) {
                            try {
                                Gson gson = new Gson();
                                String strResponse = gson.toJson(response.body());
                                MobiLogger.printmsg("response : " + strResponse);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            final AppVersionModel versionModel = response.body();
                            ArrayList<String> arrayList = versionModel.getVersions();
                            try {
                                PackageInfo pInfo = context.getPackageManager().getPackageInfo(getPackageName(), 0);
                                String version = pInfo.versionName;
                                //version = "1.0.0";
                                if (!arrayList.contains(version)) {
                                    isAllowedVersion = false;
                                    showVersionDialog();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            isVersionApiDone = true;
                            gotoNextScreen();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<AppVersionModel> call, Throwable t) {

                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gotoNextScreen() {

        if (isHandlerDone && isAfterLoginApiDone && isVersionApiDone) {
            if (isAllowedVersion) {
                if (session.checkLogin()) {
                    Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                    intent.putExtra("isFromSplash", true);
                    startActivity(intent);
                    overridePendingTransition(MobiConstants.ANIM_IN,MobiConstants.ANIM_OUT);
                    finish();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Splashscreen.this.finish();
                        }
                    }, 2000);
                } else {

                    Intent i = new Intent(Splashscreen.this, MainActivity.class);
                    i.putExtra("isFromSplash", true);
                    startActivity(i);
                    overridePendingTransition(MobiConstants.ANIM_IN,MobiConstants.ANIM_OUT);
                    finish();
                }
            } else {
//                showVersionDialog();
            }
        }

    }

    private void showVersionDialog() {
        try {
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(context.getString(R.string.lbl_dialog_alert))
                    .setContentText(context.getString(R.string.lbl_dialog_version_update_message))
                    .setConfirmText(context.getString(R.string.lbl_dialog_button_update))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                overridePendingTransition(MobiConstants.ANIM_IN,MobiConstants.ANIM_OUT);
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                overridePendingTransition(MobiConstants.ANIM_IN,MobiConstants.ANIM_OUT);
                            }
                            finish();
                        }
                    })
                    .setCancelText(context.getString(R.string.lbl_dialog_button_cancel))
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            finish();
                        }
                    })
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}