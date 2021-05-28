package com.fluvina.hummnew.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fluvina.hummnew.Adapter.HummHorizontalAdapter;
import com.fluvina.hummnew.R;
import com.fluvina.hummnew.Utilities.MobiBroadcaster;
import com.fluvina.hummnew.Utilities.MobiLogger;

public class DashboardHummMainFragment extends Fragment implements View.OnClickListener {

    private ViewPager2 vpHummMain;
    private LinearLayout llToolbarLeft;
    private RelativeLayout rlToolbar;
    private LinearLayout ivToolbarRight, ivToolbarRight2;

    HummHorizontalAdapter adapter;

    String health_feed_id = "";
    String is_survey_quiz_id = "";
    String currentUrl = "";
    boolean isFromNotification = false;
    int currentFeedId = 0;
    int currPage = 0;

    public void setCurrentUrl(String url) {
        currentUrl = url;
        vpHummMain.setUserInputEnabled(!TextUtils.isEmpty(currentUrl));
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentFeedId(int id) {
        currentFeedId = id;
    }

    public int getCurrentFeedId() {
        return currentFeedId;
    }

    public void setCurrentPage(int position) {
        try {
            vpHummMain.setCurrentItem(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCurrentPage() {
        try {
            return vpHummMain.getCurrentItem();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG", "onResume: Tab1");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard_humm_main, container, false);

        vpHummMain = rootView.findViewById(R.id.vpHummMain);

        ivToolbarRight2 = rootView.findViewById(R.id.ivToolbarRight2);
        ivToolbarRight = rootView.findViewById(R.id.llToolbarRight);
        rlToolbar = rootView.findViewById(R.id.rlToolbar);
        llToolbarLeft = rootView.findViewById(R.id.llToolbarLeft);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadBundle();

        adapter = new HummHorizontalAdapter(getChildFragmentManager(), getLifecycle(), health_feed_id, is_survey_quiz_id, isFromNotification);
        vpHummMain.setAdapter(adapter);

        ivToolbarRight.setOnClickListener(this);
        llToolbarLeft.setOnClickListener(this);
        ivToolbarRight2.setOnClickListener(this);

        vpHummMain.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        vpHummMain.setPageTransformer(DepthPageTransformer);
        vpHummMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                currPage = position;
                super.onPageSelected(position);
                if (currPage == 1) {
                    vpHummMain.setUserInputEnabled(false);
                } else {
                    vpHummMain.setUserInputEnabled(!TextUtils.isEmpty(currentUrl));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        setCurrentUrl("");
        try {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(refreshreceiver, new IntentFilter(MobiBroadcaster.UPDATE_DASHBOARD_EVENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBundle() {
        try {
            if (getArguments() != null) {
                if (getArguments().containsKey("health_feed_id")) {
                    health_feed_id = getArguments().getString("health_feed_id");
                }
                if (getArguments().containsKey("is_survey_quiz_id")) {
                    is_survey_quiz_id = getArguments().getString("is_survey_quiz_id");
                }
                if (getArguments().containsKey("isFromNotification")) {
                    isFromNotification = getArguments().getBoolean("isFromNotification");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llToolbarRight:
//                Intent intent = new Intent(getActivity(), QrScannerActivity.class);
//                startActivity(intent);
                break;
            case R.id.ivToolbarRight2:
                break;
        }
    }

    public void toggleToolbar() {
        try {
            if (rlToolbar.getVisibility() == View.VISIBLE) {
                //bdashboard_redesigned_dashboard_fragment_layouts.setVisibility(View.GONE);

                Animation topUp = AnimationUtils.loadAnimation(getActivity(),
                        R.anim.top_up);


                rlToolbar.startAnimation(topUp);
                topUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rlToolbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


            } else {
                //bdashboard_redesigned_dashboard_fragment_layouts.setVisibility(View.VISIBLE);
                rlToolbar.setVisibility(View.VISIBLE);
                Animation topDown = AnimationUtils.loadAnimation(getActivity(),
                        R.anim.top_down);

                rlToolbar.startAnimation(topDown);
                topDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //dashboard_medical_feeds_fragment_toolbar_layout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideToolBar() {
        try {
            if (rlToolbar.getVisibility() == View.VISIBLE) {

                Animation topUp = AnimationUtils.loadAnimation(getActivity(),
                        R.anim.top_up);


                rlToolbar.startAnimation(topUp);
                topUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rlToolbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final BroadcastReceiver refreshreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                String message = intent.getStringExtra(MobiBroadcaster.UPDATE_DASHBOARD_TAG);
                MobiLogger.printmsg("BroadcastReceiver message : " + message);
                if (message != null) {
                    if (message.equals(MobiBroadcaster.UPDATE_DASHBOARD_TAG)) {
                        //refreshView();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(refreshreceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //just to test
        // java.lang.IllegalStateException:
        // Failure saving state: active DashboardMedicalFeedsFragment{42f71868} has cleared index: -1
        setTargetFragment(null, -1);
    }

    public Fragment getFragmentAtPosition(int position) {
        Fragment fragment = null;
        try {
            fragment = getChildFragmentManager().getFragments().get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }


    ViewPager2.PageTransformer DepthPageTransformer = new ViewPager2.PageTransformer() {
        private static final float MIN_SCALE = 0.80f;

        @Override
        public void transformPage(@NonNull View view, float position) {

            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]

                view.setAlpha(1+position);

                view.setTranslationX(pageWidth*-position);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setTranslationZ(-1f);
                }


                float scaleFactor = 1 - (1-MIN_SCALE)*Math.abs(position);
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else if (position <= 1) { // (0,1]

                view.setAlpha(1f);
                view.setTranslationX(0f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setTranslationZ(1f);
                }
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(1f);
            }
        }

        ;
    };
}