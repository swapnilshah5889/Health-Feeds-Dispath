package com.fluvina.hummnew.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.fluvina.hummnew.Fragments.DashboardHummFeedDetailFragment;
import com.fluvina.hummnew.Fragments.DashboardHummMainFragment;
import com.fluvina.hummnew.R;
import com.fluvina.hummnew.Utilities.DBHelper;
import com.fluvina.hummnew.Utilities.MobiConstants;
import com.fluvina.hummnew.Utilities.MobiLogger;
import com.fluvina.hummnew.Utilities.SessionManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = " MainActivity ";
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    DBHelper db;
    public Context context;
    public SessionManager sessionManager;

    boolean isFromNotification = false;
    String notificationTag = "";
    String health_feed_id = "";
    String is_survey_quiz_id = "";
    String device_id, refreshedToken;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        try {
            isFromNotification = intent.getBooleanExtra("isFromNotification", false);

            if (intent.hasExtra("tag")) {
                notificationTag = intent.getStringExtra("tag");
            }

            if (intent.hasExtra("health_feed_id")) {
                health_feed_id = intent.getStringExtra("health_feed_id");
            }
            if (intent.hasExtra("is_survey_quiz_id")) {
                is_survey_quiz_id = intent.getStringExtra("is_survey_quiz_id");
            }

            if (isFromNotification) {
                if (notificationTag.equalsIgnoreCase(MobiConstants.NC_HealthFeedsTag)) {
                    String menuName = getResources().getString(R.string.drawer_home);
                }
            } else {
                String menuName = getResources().getString(R.string.drawer_home);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        db = new DBHelper(getApplication());
        sessionManager = new SessionManager(this);

        db.initiate();
        if (isFromNotification) {
            if (notificationTag.equalsIgnoreCase(MobiConstants.NC_HealthFeedsTag)) {
                String menuName = getResources().getString(R.string.drawer_home);
            }
        } else {
            String menuName = getResources().getString(R.string.drawer_home);
        }

        device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        initializeViews();
        loadBundle();

        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if (refreshedToken == null || refreshedToken.isEmpty()) {
            refreshedToken = sessionManager.getDeviceToken();
        }


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = new DashboardHummMainFragment();
        fragmentTransaction.replace(R.id.maincontainer, fragment).commit();

        //Logged in
        if (sessionManager.isLoggedIn()) {

        }

        //Without Login
        else {
            SetWithoutLoginData();
        }
    }


    private void SetWithoutLoginData() {
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initializeViews() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBundle() {
        try {
            isFromNotification = getIntent().getBooleanExtra("isFromNotification", false);
            if (getIntent().hasExtra("tag")) {
                notificationTag = getIntent().getStringExtra("tag");
            }
            if (getIntent().hasExtra("health_feed_id")) {
                health_feed_id = getIntent().getStringExtra("health_feed_id");
            }
            if (getIntent().hasExtra("is_survey_quiz_id")) {
                is_survey_quiz_id = getIntent().getStringExtra("is_survey_quiz_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            MobiLogger.printmsg("back stack count : " + getSupportFragmentManager().getBackStackEntryCount());

            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                MobiLogger.printmsg("do no show dialog");

                FragmentManager fm = getSupportFragmentManager();
                List<Fragment> fragments = fm.getFragments();
                //Fragment lastFragment = fragments.get(fragments.size() - 2);
                Fragment lastFragment = fragments.get(fragments.size() - 1); // By Jitesh
                if (lastFragment instanceof SupportRequestManagerFragment) {
                    try {
                        lastFragment = fragments.get(fragments.size() - 2); //By Jitesh
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //controlling pillbox add madicine fragment

                if (lastFragment instanceof DashboardHummFeedDetailFragment) {
                    ((DashboardHummFeedDetailFragment) lastFragment).onBackPressed();
                } else {
                    super.onBackPressed();
                }
                //super.onBackPressed();
            } else {
                MobiLogger.printmsg("do show dialog");

                FragmentManager fm = getSupportFragmentManager();
                List<Fragment> fragments = fm.getFragments();
                Fragment lastFragment = fragments.get(fragments.size() - 1); //By Jitesh
                if (lastFragment instanceof SupportRequestManagerFragment) {
                    try {
                        lastFragment = fragments.get(fragments.size() - 2); //By Jitesh
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (lastFragment instanceof DashboardHummMainFragment) {
                    int position = ((DashboardHummMainFragment) lastFragment).getCurrentPage();
                    if (position == 0) {
                        showExitDialog();
                    } else {
                        Fragment fragment = ((DashboardHummMainFragment) lastFragment).getFragmentAtPosition(position);
                        if (fragment instanceof DashboardHummFeedDetailFragment) {
                            ((DashboardHummFeedDetailFragment) fragment).onBackPressed();
                        } else {
                            ((DashboardHummMainFragment) lastFragment).setCurrentPage(0);
                        }
                    }
                } else {
                    showExitDialog();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showExitDialog() {
        try {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setTitleText(getString(R.string.txt_dialog_exit));
            sweetAlertDialog.setConfirmText(getString(R.string.txt_dialog_button_yes));
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    MainActivity.this.finish();
                }
            });
            sweetAlertDialog.showCancelButton(true);
            sweetAlertDialog.setCancelText(getString(R.string.txt_dialog_button_no));
            sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            });
            sweetAlertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

    }
}