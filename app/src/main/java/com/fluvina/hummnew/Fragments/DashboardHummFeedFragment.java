package com.fluvina.hummnew.Fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fluvina.hummnew.Activities.FullImageViewActivity;
import com.fluvina.hummnew.Activities.HummFilterActivity;
import com.fluvina.hummnew.Adapter.DashboardPagerHISVerticalAdapter;
import com.fluvina.hummnew.Custom.CustomLoaderDialog;
import com.fluvina.hummnew.Custom.HummEffectTransformer;
import com.fluvina.hummnew.Model.DashBoardDataClassMain;
import com.fluvina.hummnew.Model.HummFeedModel;
import com.fluvina.hummnew.R;
import com.fluvina.hummnew.Utilities.DBHelper;
import com.fluvina.hummnew.Utilities.MobiBroadcaster;
import com.fluvina.hummnew.Utilities.MobiConstants;
import com.fluvina.hummnew.Utilities.MobiLogger;
import com.fluvina.hummnew.Utilities.MobiUtility;
import com.fluvina.hummnew.Utilities.SessionManager;
import com.fluvina.hummnew.interfaces.RecyclerItemActionClick;
import com.fluvina.hummnew.network.ApiClient;
import com.fluvina.hummnew.network.ApiService;
import com.fluvina.hummnew.network.Apis;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardHummFeedFragment extends Fragment implements View.OnClickListener {


    SessionManager sessionManager;
    DBHelper db;

    ViewPager2 vpVerticalFeed;
    LinearLayout llMainDataView, llDashboardNoData, llDashboardTop, llPricePlanNoInternet;
    //LinearLayout llDataLoadingView;
    TextView btnInternetTryAgain;
    // Bottom View
    LinearLayout linear_bottom_layout;
    LinearLayout vBottomShare, vBottomView;
    //ToolBar
    TextView tvToolbarTitle;
    LinearLayout llToolbarLeft;
    RelativeLayout rlToolbar;
    ImageView imgRight, imgRight2;
    LinearLayout ivToolbarRight2, ivToolbarRight;

    DashboardPagerHISVerticalAdapter verticalAdapter;
    ArrayList<DashBoardDataClassMain.DashBoardDataClass> arrayList = new ArrayList<>();
    String device_id, refreshedToken;

    int page = 1;
    int totalCounts = 0;
    String health_feed_id = "";
    String is_survey_quiz_id = "";
    boolean isFromNotification = false;
    String currentUrl = "";
    int currentFeedId = 0;
    boolean isLoadMore = true;
    HashMap<String, Integer> hummHist = new HashMap<>();
    boolean isVaccinationConfirm = false, isShowAllInsights = false, applyCategroyFilter = false, applySearchfilter = false, insightFilter = false;
    String searchKeyword = "",specificFeedId = "", subCategoryFilter = "", categoryFilter = "", insightId = "";

    public void setCurrentUrl(String url) {
        currentUrl = url;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard_humm_feed, container, false);

        vpVerticalFeed = rootView.findViewById(R.id.vpVerticalFeed);
        llDashboardTop = rootView.findViewById(R.id.llDashboardTop);
        llMainDataView = rootView.findViewById(R.id.llMainDataView);
        //llDataLoadingView = rootView.findViewById(R.id.llDataLoadingView);
        llDashboardNoData = rootView.findViewById(R.id.llDashboardNoData);
        btnInternetTryAgain = rootView.findViewById(R.id.btnInternetTryAgain);
        llPricePlanNoInternet = rootView.findViewById(R.id.llPricePlanNoInternet);
        ivToolbarRight2 = rootView.findViewById(R.id.ivToolbarRight2);
        rlToolbar = rootView.findViewById(R.id.rlToolbar);
        llToolbarLeft = rootView.findViewById(R.id.llToolbarLeft);
        ivToolbarRight = rootView.findViewById(R.id.llToolbarRight);
        linear_bottom_layout = rootView.findViewById(R.id.linear_bottom_layout);
        vBottomShare = rootView.findViewById(R.id.vBottomShare);
        vBottomView = rootView.findViewById(R.id.vBottomView);
        tvToolbarTitle = rootView.findViewById(R.id.tvToolbarTitle);
        imgRight = rootView.findViewById(R.id.imgRight);
        imgRight2 = rootView.findViewById(R.id.imgRight2);
        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sessionManager = new SessionManager(getActivity());
        db = new DBHelper(getActivity());
        loadBundle();
        llToolbarLeft.setVisibility(View.GONE);

        tvToolbarTitle.setText(R.string.txt_medical_feeds);

        ivToolbarRight.setVisibility(View.VISIBLE);
        ivToolbarRight2.setVisibility(View.VISIBLE);

        arrayList = new ArrayList<>();
        verticalAdapter = new DashboardPagerHISVerticalAdapter(getActivity(), arrayList, DashboardHummFeedFragment.this, onItemClick);
        vpVerticalFeed.setAdapter(verticalAdapter);
        vpVerticalFeed.setPageTransformer(new HummEffectTransformer());
        vpVerticalFeed.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        vpVerticalFeed.setCurrentItem(0);
        vpVerticalFeed.setOffscreenPageLimit(3);

        vpVerticalFeed.registerOnPageChangeCallback(pageChangeListener);
        pageChangeListener.onPageSelected(vpVerticalFeed.getCurrentItem());

        vBottomView.setOnClickListener(this);
        vBottomShare.setOnClickListener(this);
        ivToolbarRight2.setOnClickListener(this);
        ivToolbarRight.setOnClickListener(this);
        btnInternetTryAgain.setOnClickListener(this);

        page = 1;
        device_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        refreshedToken = sessionManager.getDeviceToken();

        HummFeedAPI(true, false);

        if (!sessionManager.isLoggedIn()) {

            ivToolbarRight2.setVisibility(View.VISIBLE);
            imgRight.setImageDrawable(getResources().getDrawable(R.drawable.refresh_icon));
            imgRight2.setImageDrawable(getResources().getDrawable(R.drawable.ic_filter_humm));

        }
        else{
            imgRight.setImageDrawable(getResources().getDrawable(R.drawable.refresh_icon));
            imgRight2.setImageDrawable(getResources().getDrawable(R.drawable.ic_filter_humm));
        }

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(showInsights,
                new IntentFilter(MobiBroadcaster.ShowInsights));

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(subCatClicked,
                new IntentFilter(MobiBroadcaster.CategoryFilter));

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(searchClicked,
                new IntentFilter(MobiBroadcaster.SearchFeeds));

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(resetHumm,
                new IntentFilter(MobiBroadcaster.HummReset));

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(insightsFilter,
                new IntentFilter(MobiBroadcaster.ShowPInsights));

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

    RecyclerItemActionClick onItemClick = new RecyclerItemActionClick() {
        @Override
        public void itemActionClick(int position, String action) {
            if (action.equalsIgnoreCase("Full Image View")) {
                Intent intent = new Intent(getActivity(), FullImageViewActivity.class);
                intent.putExtra("imageUrl", arrayList.get(position).getImageName());
                startActivity(intent);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llToolbarRight:
                HummFeedAPI(true, true);
                break;
            case R.id.ivToolbarRight2:
                //Humm Filter
                Intent intent = new Intent(getActivity(), HummFilterActivity.class);
                startActivity(intent);
                break;

            case R.id.btnInternetTryAgain:
                HummFeedAPI(true, true);
                break;
            case R.id.vBottomView:
                try {
                    try {
                        Fragment fragment = getParentFragment();
                        if (fragment instanceof DashboardHummMainFragment) {
                            ((DashboardHummMainFragment) fragment).setCurrentUrl(currentUrl == null ? "" : currentUrl);
                            ((DashboardHummMainFragment) fragment).setCurrentFeedId(currentFeedId);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setCurrentUrl(currentUrl == null ? "" : currentUrl);
                    setCurrentFeedId(currentFeedId);
                    Fragment fragment = getParentFragment();
                    if (fragment instanceof DashboardHummMainFragment) {
                        ((DashboardHummMainFragment) fragment).setCurrentPage(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.vBottomShare:
                try {

                    checkStoragePermission();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void goToNextPage(int position) {
        try {
            vpVerticalFeed.setCurrentItem(position + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ViewPager2.OnPageChangeCallback pageChangeListener = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //MobiLogger.printmsg("scrolled" + String.valueOf(position));
        }

        @Override
        public void onPageSelected(int position) {

            /*if (position != 0) {
                swipeToRefresh.setEnabled(false);
            } else {
                swipeToRefresh.setEnabled(true);
            }*/

            // Play Current Position Video
//            try {
//                if (arrayList.size() > 0) {
//                    DashBoardDataClassMain.DashBoardDataClass dataClass = arrayList.get(position);
//                    View view = dataClass.getPlayerView();
//                    if (view != null) {
//                        if (dataClass.getVideoType() == 0) {
//                            try {
//                                //SimpleExoPlayerView exoPlayerView = (SimpleExoPlayerView) view;
//                                //exoPlayerView.getPlayer().setPlayWhenReady(true);
//                                //exoPlayerView.getPlayer().prepare();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            // Pause Previous Position Video
            try {
                if (arrayList.size() > 0) {
                    if (position > 0) {
                        DashBoardDataClassMain.DashBoardDataClass dataClass = arrayList.get(position - 1);
                        View view = dataClass.getPlayerView();
                        if (view != null) {
                            if (dataClass.getVideoType() == 1) {
                                if (dataClass.getYouTubePlayer() != null) {
                                    dataClass.getYouTubePlayer().pause();
                                }
                            } else if (dataClass.getVideoType() == 0) {
                                try {
                                    //UniversalVideoView video_view = (UniversalVideoView) view;
                                    //video_view.pause();
                                    //SimpleExoPlayerView exoPlayerView = (SimpleExoPlayerView) view;
                                    //exoPlayerView.getPlayer().setPlayWhenReady(false);
                                    //exoPlayerView.getPlayer().release();
                                    //BetterVideoPlayer videoPlayer = (BetterVideoPlayer) view;
                                    //videoPlayer.pause();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (dataClass.getVideoType() == 2) {
//                        try {
//                            WebView webView = (WebView) view;
//                            webView.onPause();
//                            //webView.pauseTimers();
//                            //Class.forName("android.webkit.WebView").getMethod("onPause", (Class[]) null).invoke(webView, (Object[]) null);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Pause Next Position Video
            try {
                if (arrayList.size() > 1) {
                    if (position < arrayList.size() - 1) {
                        DashBoardDataClassMain.DashBoardDataClass dataClass = arrayList.get(position + 1);
                        View view = dataClass.getPlayerView();
                        if (view != null) {
                            if (dataClass.getVideoType() == 1) {
                                if (dataClass.getYouTubePlayer() != null) {
                                    dataClass.getYouTubePlayer().pause();
                                }
                            } else if (dataClass.getVideoType() == 0) {
                                try {
//                                BetterVideoPlayer videoPlayer = (BetterVideoPlayer) view;
//                                videoPlayer.pause();

                                    //UniversalVideoView video_view = (UniversalVideoView) view;
                                    //video_view.pause();
                                    //SimpleExoPlayerView exoPlayerView = (SimpleExoPlayerView) view;
                                    //exoPlayerView.getPlayer().setPlayWhenReady(false);
                                    //exoPlayerView.getPlayer().release();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (dataClass.getVideoType() == 2) {
//                        try {
//                            WebView webView = (WebView) view;
//                            webView.onPause();
//                            //webView.pauseTimers();
//                            //Class.forName("android.webkit.WebView").getMethod("onPause", (Class[]) null).invoke(webView, (Object[]) null);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Fetch New Feeds (Pagination)
            try {
                if (arrayList.size() > 0) {

//                    if (position == arrayList.size() - 4 && !(arrayList.size() == totalCounts) && isLoadMore) {
//                        page = page + 1;
//                        //apiHandler();
//                        loaddataFromserver();
//                    }

                    try {
                        //int index = bdashboard_redesigned_dashboard_fragment_layout_viewpager.getCurrentItem();
                        if (position == verticalAdapter.getItemCount() - MobiConstants.HUMM_PRE_CALL_NUMBER) {
                            if ((arrayList.size() != totalCounts) && isLoadMore) {
                                Log.v("LoadMore Called", isLoadMore + " size =>" + arrayList.size());
                                page = page + 1;
                                //apiHandler();
                                HummFeedAPI(true, false);

                                //Print Histogram
                                int count = 0;
                                for (Map.Entry<String, Integer> entry : hummHist.entrySet()) {
                                    if (entry.getValue() > 1) {
                                        MobiLogger.printmsg(entry.getKey() + " : " + entry.getValue());
                                        count++;
                                    }
                                }
                                MobiLogger.printmsg("Total Feeds Read : " + (arrayList.size() - MobiConstants.HUMM_PRE_CALL_NUMBER));
                                MobiLogger.printmsg("Feeds repeated : " + count);
                                HummFeedAPI(true, false);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    /*else if (arrayList.size() < 4){
                    page = page + 1;
                    //apiHandler();
                    loaddataFromserver();
                }*/

                    //hideToolBar();
                    //toggleToolbar();
                    if (rlToolbar.getVisibility() == View.VISIBLE) {
                        toggleToolbar();
                    }
                    String detailUrl = arrayList.get(position).getDetailUrl();
                    if (arrayList.get(position).getPageType() == MobiConstants.HIS_PAGE_TYPE_INFO_SLIDER) {
                        detailUrl = "";
                    }
                    if (detailUrl == null || detailUrl.isEmpty()) {
                        vBottomView.setVisibility(View.GONE);
                    } else {
                        vBottomView.setVisibility(View.VISIBLE);
                    }
                    try {
                        Fragment fragment = getParentFragment();
                        if (fragment instanceof DashboardHummMainFragment) {
                            ((DashboardHummMainFragment) fragment).setCurrentUrl(detailUrl == null ? "" : detailUrl);
                            ((DashboardHummMainFragment) fragment).setCurrentFeedId(arrayList.get(position).getId());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setCurrentUrl(detailUrl == null ? "" : detailUrl);
                    setCurrentFeedId(arrayList.get(position).getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Toast.makeText(getActivity(), "Vertical " + position, Toast.LENGTH_SHORT).show();

            //Insert into humm feeds read table on new feed selected

            //Insert humm feed into local database
            try {
                if (arrayList.size() > 0 && position >= 0 && position < arrayList.size()) {

                    String type = arrayList.get(position).getFeedCategoryType();
                    if (type != null) {
                        if (!type.equals("")) {

                            String id = arrayList.get(position).getId() + "";
                            db.insert_into_humm_feeds(id, type);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                hummHist.put(id, hummHist.getOrDefault(id, 0) + 1);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Call Humm feeds read api when last feed reached
            if (!isShowAllInsights && !insightFilter && !applyCategroyFilter && !applySearchfilter)
                try {
                    if (position == arrayList.size() - 1) {

                        HummFeedAPI(false, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            try {
                Log.v("PageScrollStateChanged", String.valueOf(state));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    ViewPager2.PageTransformer DepthPageTransformer = new ViewPager2.PageTransformer() {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(@NonNull View view, float position) {

            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setTranslationZ(0f);
                }
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationY(pageWidth * -position);
                // Move it behind the left page
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setTranslationZ(-1f);
                }

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }

    };

    ViewPager2.PageTransformer ZoominPageTransformer = new ViewPager2.PageTransformer() {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.75f;

        @Override
        public void transformPage(@NonNull View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            //System.out.println("viewpager2-position :" + position);
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);
            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                //Log.i("viewpager2-trans<0", "" + position);
                //System.out.println("viewpager2-trans<0 :" + (horzMargin - vertMargin / 2));
                //System.out.println("viewpager2-trans>0 :" + scaleFactor);
                //System.out.println("scalefactor :" + (-horzMargin + vertMargin / 2));
                //System.out.println("viewpager2-trans>0 :"+(-horzMargin + vertMargin / 2));

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    };

    private Bitmap convertViewToImage(View view) {
        try {
//            view.setDrawingCacheEnabled(true);
//            // this is the important code :)
//            // Without it the view will have a dimension of 0,0 and the bitmap will be null
//
//            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//
//            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//
//            view.buildDrawingCache(true);
//            Bitmap b = Bitmap.createBitmap(view.getDrawingCache());
//            view.setDrawingCacheEnabled(false); // clear drawing cache

            //Define a bitmap with the same size as the view
            Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            //Bind a canvas to it
            Canvas canvas = new Canvas(returnedBitmap);
            //Get the view's background
            Drawable bgDrawable = view.getBackground();
            if (bgDrawable != null)
                //has background drawable, then draw it on the canvas
                bgDrawable.draw(canvas);
            else
                //does not have background drawable, then draw white background on the canvas
                canvas.drawColor(Color.WHITE);
            // draw the view on the canvas
            view.draw(canvas);
            //return the bitmap

            return returnedBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/MobiHealth/HealthInShorts/");
        Log.i("Directory", "==" + myDir);
        myDir.mkdirs();

        String fname = "MHIS_" + System.currentTimeMillis();
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public void toggleToolbar() {
        try {
            int currentPage = 0;
            try {
                Fragment fragment = getParentFragment();
                if (fragment instanceof DashboardHummMainFragment) {
                    currentPage = ((DashboardHummMainFragment) fragment).getCurrentPage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (currentPage == 0) {
                if (rlToolbar.getVisibility() == View.VISIBLE) {
                    try {
                        //bdashboard_redesigned_dashboard_fragment_layouts.setVisibility(View.GONE);

                        Animation topUp = AnimationUtils.loadAnimation(getActivity(),
                                R.anim.top_up);
                        Animation bottomDown = AnimationUtils.loadAnimation(getActivity(),
                                R.anim.bottom_down);

                        rlToolbar.startAnimation(topUp);
                        linear_bottom_layout.startAnimation(bottomDown);

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

                        bottomDown.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                linear_bottom_layout.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        //bdashboard_redesigned_dashboard_fragment_layouts.setVisibility(View.VISIBLE);

                        Animation topDown = AnimationUtils.loadAnimation(getActivity(),
                                R.anim.top_down);
                        Animation bottomUp = AnimationUtils.loadAnimation(getActivity(),
                                R.anim.bottom_up);

                        rlToolbar.startAnimation(topDown);
                        linear_bottom_layout.startAnimation(bottomUp);

                        rlToolbar.setVisibility(View.VISIBLE);
                        linear_bottom_layout.setVisibility(View.VISIBLE);

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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //hideParentToolBar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void hideParentToolBar() {
//        try {
//            Fragment fragment = getParentFragment();
//            if (fragment instanceof DashboardHummMainFragment) {
//                ((DashboardHummMainFragment) fragment).toggleToolbar();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void hideToolBar() {

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
    }

    public void getHummFeedData(boolean isReset, String hummFeedJson, boolean callHumm) {
        try {
            if (isReset) {
                vpVerticalFeed.setCurrentItem(0);
                arrayList.clear();
                verticalAdapter.notifyDataSetChanged();
                page = 1;
                isLoadMore = true;
                health_feed_id = "";
                is_survey_quiz_id = "";
                hummHist = new HashMap<>();
            }

            if (MobiUtility.isInternetOn(getActivity())) {
                llPricePlanNoInternet.setVisibility(View.GONE);
                try {

                    getHummFeedDataApi(hummFeedJson, callHumm);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                llPricePlanNoInternet.setVisibility(View.VISIBLE);
                //llDataLoadingView.setVisibility(View.GONE);
                llMainDataView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), getString(R.string.toast_no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getHummFeedDataApi(String hummFeeds, boolean callHumm) {

        CustomLoaderDialog progressDialog = new CustomLoaderDialog(getActivity());
        if (page == 1) {
            progressDialog.showDialog();
        }
        try {

            LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();

            //Without login and logged in
            if (sessionManager.isLoggedIn()) {

                hashMap.put("user_id", sessionManager.getuserid());

            } else {

                hashMap.put("user_id", "0");
            }

            //Apply insights humm filter
            if ((isShowAllInsights || insightFilter) && callHumm) {
                hashMap.put("action", MobiConstants.ACTION_get_humm_insights);
                if (insightFilter)
                    hashMap.put("specific_feed_id", insightId);
            }

            //apply category filter
            else if(applyCategroyFilter && callHumm){
                hashMap.put("action", MobiConstants.ACTION_humm_search_keyword);
                hashMap.put("specific_category", categoryFilter);
                hashMap.put("specific_sub_category", subCategoryFilter);
            }

            //apply search feed filter
            else if(applySearchfilter && callHumm){
                hashMap.put("action", MobiConstants.ACTION_humm_search_keyword);
                hashMap.put("search_keyword", searchKeyword);
                hashMap.put("specific_feed_id", specificFeedId);
            }

            //load normal humm feeds
            else{

                hashMap.put("action", MobiConstants.ACTION_get_my_feeds);

                //Feeds that have been read
                if (!hummFeeds.trim().equals(""))
                    hashMap.put("feed_read_json", hummFeeds);

                //if new feeds are required
                if (callHumm)
                    hashMap.put("fetch_updated_feeds", MobiConstants.HUMM_FETCH_MORE_FEEDS);
                    //if only read feeds need to be saved
                else
                    hashMap.put("fetch_updated_feeds", MobiConstants.HUMM_ONLY_SAVE_READ_FEEDS);

                //if is from notification
                if (isFromNotification) {
                    hashMap.put("notification_feed_id", health_feed_id);
                    hashMap.put("IsSurveyQuizId", is_survey_quiz_id);
                }

                if (isVaccinationConfirm) {
                    hashMap.put("is_vaccine_confirmed", isVaccinationConfirm + "");
                    isVaccinationConfirm = false;
                }
            }


            if (refreshedToken == null || refreshedToken.isEmpty()) {
                refreshedToken = sessionManager.getDeviceToken();
            }
            if(refreshedToken.equals(""))
                refreshedToken = "cloum-token";

            hashMap.put("device_token", refreshedToken);
            hashMap.put("device_id", device_id);
            hashMap.put("page", page + "");
            hashMap.put("app_type", MobiConstants.APP_TYPE);

            MobiLogger.printmsg("Api : " + Apis.BASE_URL + Apis.patient_api);
            MobiLogger.printMap(hashMap);

            ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = service.callPatientApi(hashMap);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    DashBoardDataClassMain dashBoardData = null;
                    try {
                        if (response.body() != null) {
                            try {
                                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                                String strResponse = response.body().string();
                                MobiLogger.printmsg("response : " + strResponse);

                                dashBoardData = gson.fromJson(strResponse, DashBoardDataClassMain.class);
                                totalCounts = dashBoardData.getTotal_records();
                                health_feed_id = "";
                                is_survey_quiz_id = "";
                                JSONObject responseJson = new JSONObject(strResponse);
                                if (responseJson.has("status")) {
                                    if (responseJson.getBoolean("status")) {
                                        db.deleteAllHummFeeds();
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    manageData(dashBoardData);
                    progressDialog.dismissDialog();
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    progressDialog.dismissDialog();
                }
            });
        } catch (Exception e) {
            progressDialog.dismissDialog();
            e.printStackTrace();
        }
    }


    private void manageData(DashBoardDataClassMain dashBoardData) {
        try {
            /*if (llDataLoadingView.getVisibility() == View.VISIBLE) {
                llMainDataView.setVisibility(View.VISIBLE);
                llDataLoadingView.setVisibility(View.GONE);
            }*/
            if (dashBoardData != null && dashBoardData.getStatus()) {
                isLoadMore = !dashBoardData.getData().isEmpty();
                arrayList.addAll(dashBoardData.getData());
            }

            verticalAdapter.notifyDataSetChanged();

            if (arrayList.size() <= 0) {
                isLoadMore = false;
                llDashboardNoData.setVisibility(View.VISIBLE);
                llMainDataView.setVisibility(View.GONE);
                try {
                    Fragment fragment = getParentFragment();
                    if (fragment instanceof DashboardHummMainFragment) {
                        ((DashboardHummMainFragment) fragment).setCurrentUrl("");
                        ((DashboardHummMainFragment) fragment).setCurrentFeedId(-1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setCurrentUrl("");
                setCurrentFeedId(-1);
            } else {
                llDashboardNoData.setVisibility(View.GONE);
                llMainDataView.setVisibility(View.VISIBLE);
//                vpVerticalFeed.setOffscreenPageLimit(arrayList.size());
            }

            if (page == 1) {
                pageChangeListener.onPageSelected(vpVerticalFeed.getCurrentItem());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSurveyData(int position, int answerPosition) {
        SaveSurveyResultApi(position, answerPosition);
    }

    public void SaveSurveyResultApi(int position, int answerPosition) {

        CustomLoaderDialog progressDialog = new CustomLoaderDialog(getActivity());
        progressDialog.showDialog();

        try {

            LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
            if (sessionManager.isLoggedIn()) {

                hashMap.put("user_id", sessionManager.getuserid());

            } else {

                hashMap.put("user_id", "0");
            }
            hashMap.put("device_token", refreshedToken);
            hashMap.put("device_id", device_id);
            hashMap.put("survey_id", arrayList.get(position).getId() + "");
            hashMap.put("selected_option", arrayList.get(position).getOptions().get(answerPosition).getOption() + "");
            hashMap.put("action", MobiConstants.ACTION_USER_OPTION_SAVE);
            hashMap.put("app_type", MobiConstants.APP_TYPE);

            MobiLogger.printmsg("Api : " + Apis.BASE_URL + Apis.SAVE_HUMM_FEED_ANSWER_API);
            MobiLogger.printMap(hashMap);

            ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = service.callSaveSurveyAnswerApi(hashMap);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismissDialog();
                    ResponseBody dashBoardData = null;
                    try {
                        if (response.body() != null) {
                            String strResponse = response.body().string();
                            MobiLogger.printmsg("saveSurveyResult response : " + strResponse);

                            JSONObject jsonObject = new JSONObject(strResponse);
                            boolean status = jsonObject.getBoolean("status");
                            if (status) {
                                try {
                                    ArrayList<DashBoardDataClassMain.DashboardQuestions> questions = new ArrayList<>();
                                    int isUserSelectedOption = jsonObject.getInt("isUserSelectedOption");
                                    try {
                                        JSONArray jsonArray = jsonObject.getJSONArray("Options");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            try {
                                                JSONObject object = jsonArray.getJSONObject(i);

                                                DashBoardDataClassMain.DashboardQuestions dashboardQuestions = new DashBoardDataClassMain().new DashboardQuestions();
                                                dashboardQuestions.setOption(object.getInt("option"));
                                                dashboardQuestions.setValue(object.getString("value"));
                                                //dashboardQuestions.setAveragePercentage(Float.valueOf(object.getString("AveragePercentage")));
                                                dashboardQuestions.setAveragePercentage(object.getInt("AveragePercentage"));
                                                dashboardQuestions.setSelectedOption(object.getInt("SelectedOption"));

                                                questions.add(dashboardQuestions);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        arrayList.get(position).setOptions(questions);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    arrayList.get(position).setIsUserSelectedOption(isUserSelectedOption);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //verticalAdapter.notifyDataSetChanged();
                    verticalAdapter.notifyItemChanged(position);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //verticalAdapter.notifyDataSetChanged();
                    progressDialog.dismissDialog();
                    verticalAdapter.notifyItemChanged(position);
                }
            });
        } catch (Exception e) {
            progressDialog.dismissDialog();
            e.printStackTrace();
        }
    }

    public void SaveMedConfirmationApi(int position, int answerPosition) {

        CustomLoaderDialog progressDialog = new CustomLoaderDialog(getActivity());
        progressDialog.showDialog();

        try {

            LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
            hashMap.put("user_id", sessionManager.getuserid());
            hashMap.put("survey_id", arrayList.get(position).getId() + "");
            hashMap.put("selected_option", arrayList.get(position).getOptions().get(answerPosition).getOption() + "");
            hashMap.put("action", MobiConstants.ACTION_MED_CONFIRMATION_SAVE);
            hashMap.put("stuff", MobiConstants.STUFF);
            hashMap.put("app_type", MobiConstants.APP_TYPE);

            MobiLogger.printmsg("Api : " + Apis.BASE_URL + Apis.SAVE_HUMM_MED_CONFIRMATION_API);
            MobiLogger.printMap(hashMap);

            ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = service.callMedConfirmationApi(hashMap);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismissDialog();
                    ResponseBody dashBoardData = null;
                    try {
                        if (response.body() != null) {
                            String strResponse = response.body().string();
                            MobiLogger.printmsg("Medicine confirmation response : " + strResponse);

                            JSONObject jsonObject = new JSONObject(strResponse);
                            boolean status = jsonObject.getBoolean("status");
                            if (status) {
                                try {
                                    ArrayList<DashBoardDataClassMain.DashboardQuestions> questions = new ArrayList<>();
                                    int isUserSelectedOption = jsonObject.getInt("isUserSelectedOption");
                                    try {
                                        JSONArray jsonArray = jsonObject.getJSONArray("Options");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            try {
                                                JSONObject object = jsonArray.getJSONObject(i);

                                                DashBoardDataClassMain.DashboardQuestions dashboardQuestions = new DashBoardDataClassMain().new DashboardQuestions();
                                                dashboardQuestions.setOption(object.getInt("option"));
                                                dashboardQuestions.setValue(object.getString("value"));
                                                dashboardQuestions.setSelectedOption(object.getInt("SelectedOption"));

                                                questions.add(dashboardQuestions);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        arrayList.get(position).setOptions(questions);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    arrayList.get(position).setIsUserSelectedOption(isUserSelectedOption);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //verticalAdapter.notifyDataSetChanged();
                    verticalAdapter.notifyItemChanged(position);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismissDialog();
                    //verticalAdapter.notifyDataSetChanged();
                    verticalAdapter.notifyItemChanged(position);
                }
            });
        } catch (Exception e) {
            progressDialog.dismissDialog();
            e.printStackTrace();
        }
    }

    private void HummFeedAPI(boolean callHumm, boolean isReset) {
        try {

            List<HummFeedModel> hummFeeds = db.get_all_HUMM_FEED();
            if (hummFeeds != null) {

                //Read Feeds Available
                if (hummFeeds.size() > 0) {
                    Gson gson = new Gson();
                    String hummFeedJson = gson.toJson(hummFeeds);
                    MobiLogger.printmsg("HummJson : " + hummFeedJson);
                    getHummFeedData(isReset, hummFeedJson, callHumm);

                }

                //No Read Feeds
                else if (callHumm) {
                    getHummFeedData(isReset, "", callHumm);
                }
            }

            //No Feed read tuple available
            else if (callHumm) {
                getHummFeedData(isReset, "", callHumm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private void CallReadHummAPI(String hummFeeds, boolean callHumm, boolean isReset) {
        CustomLoaderDialog customLoaderDialog = new CustomLoaderDialog(getActivity());
        try {

            if (callHumm && page == 1)
                customLoaderDialog.showDialog();

            LinkedHashMap<String, String> params = new LinkedHashMap<>();

            params.put("action", MobiConstants.ACTION_update_health_feed_read_flag);
            if (sessionManager.isLoggedIn()) {
                params.put("user_id", sessionManager.getuserid());
            } else {
                params.put("user_id", "0");
            }
            params.put("device_id", device_id);
            params.put("device_token", refreshedToken);
            params.put("feed_read_json", hummFeeds);
            params.put("stuff", MobiConstants.STUFF);
            params.put("app_type", MobiConstants.APP_TYPE);

            MobiLogger.printmsg("Api : " + Apis.BASE_URL + Apis.patient_api);
            MobiLogger.printMap(params);

            ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = service.callPatientApi(params);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        customLoaderDialog.dismissDialog();
                        String strResponse = response.body().string();
                        MobiLogger.printmsg("response : " + strResponse);
                        JSONObject responseJson = new JSONObject(strResponse);
                        if (responseJson.has("status")) {
                            if (responseJson.getBoolean("status")) {
                                db.deleteAllHummFeeds();
                            }
                        }

                        if (callHumm) {
                            getHummFeedData(isReset);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    customLoaderDialog.dismissDialog();
                    System.out.println(t.getMessage());
                }
            });

        } catch (Exception e) {
            customLoaderDialog.dismissDialog();
            e.printStackTrace();
        }
    }*/

    private void shareHUMM() {
        try {
            View layout = vpVerticalFeed.findViewWithTag("linear" + vpVerticalFeed.getCurrentItem());
            if (layout != null) {
                Bitmap b = convertViewToImage(layout);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
//                                b, "Title", null);
//                        String path = saveImage(b);
//                        Uri imageUri = Uri.parse(path);
                Bitmap mobihealthIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_share_logo);
                Bitmap bWaterMark = addWatermark(b,mobihealthIcon,MobiConstants.HUMM_ICON_SCALE);
                if(bWaterMark!=null)
                    b= bWaterMark;

                /*String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), b, "HUMM", null);
                Uri imageUri = Uri.parse(path);
                share.putExtra(Intent.EXTRA_TEXT, getString(R.string.inshort_share_message) + "\nhttps://play.google.com/store/apps/details?id=" + getActivity().getPackageName() + "&hl=en ");
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(share, "Select"));*/

                Uri imageUri = null;
                File file = getImageUri(b);
                if (file != null) {
                    imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", new File(file.getAbsolutePath()));
                    share.putExtra(Intent.EXTRA_TEXT, getString(R.string.inshort_share_message) + "\nhttps://play.google.com/store/apps/details?id=" + getActivity().getPackageName() + "&hl=en ");
                    share.putExtra(Intent.EXTRA_STREAM, imageUri);
                    startActivity(Intent.createChooser(share, "Select"));

                    try {
                        int id = 0;
                        try {
                            int position = vpVerticalFeed.getCurrentItem();
                            id = arrayList.get(position).getId();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Bundle params = new Bundle();
                        params.putString("action", "Share HUMM");
                        params.putString("inshort_id", id + "");
                        params.putString("mobile", sessionManager.getmobileNo());
                        MobiUtility.firebaseLogEvent(getActivity(), MobiConstants.FIREBASE_ANALYTICS_SHARE_INSHORT, params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                try {
                    int id = 0;
                    try {
                        int position = vpVerticalFeed.getCurrentItem();
                        id = arrayList.get(position).getId();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Bundle params = new Bundle();
                    params.putString("action", "Share HUMM");
                    params.putString("inshort_id", id + "");
                    params.putString("mobile", sessionManager.getmobileNo());
                    MobiUtility.firebaseLogEvent(getActivity(), MobiConstants.FIREBASE_ANALYTICS_SHARE_INSHORT, params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getImageUri(Bitmap inImage) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            //String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "ShareImage", null);

            File myDir = new File(MobiConstants.SHARED_INSHORTS_DIRECTORY);

            if (!myDir.exists()) {
                if (!myDir.mkdir()) {
                    if (!myDir.mkdirs()) {

                    }
                }
            }
            myDir.mkdirs();

            String fname = "HUMM_" + System.currentTimeMillis() + ".png";
            File file = new File(myDir, fname);
            Log.i("Directory", "==" + file);
            String path = MobiUtility.saveBitmapToFile(inImage, file.getAbsolutePath());
            //return Uri.parse(path);
            //return Uri.fromFile(new File(path));
            return new File(path);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void checkStoragePermission() {
        try {
            Dexter.withContext(getActivity())
                    .withPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        shareHUMM();
                    } else if (report.isAnyPermissionPermanentlyDenied()) {
                        showSettingsDialog();
                    }
                    MobiLogger.printmsg("Permission Denied : " + report.getDeniedPermissionResponses().toString());
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).check();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSettingsDialog() {

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Need Permissions");
            builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
            builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    openSettings();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSettings() {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap addWatermark(Bitmap temp, Bitmap watermark, float ratio) {

        try {

            Canvas canvas;
            Paint paint;
            Bitmap bmp;
            Matrix matrix;
            RectF r;

            int width, height;
            float scale;

            float icon_height = temp.getHeight();
            float icon_width = watermark.getWidth();

            Bitmap source = addWhiteBorder(temp, (int)(icon_height* (ratio*1.2)));

            width = source.getWidth();
            height = source.getHeight();

            // Create the new bitmap
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);

            // Copy the original bitmap into the new one
            canvas = new Canvas(bmp);
            canvas.drawBitmap(source, 0, 0, paint);

            // Scale the watermark to be approximately to the ratio given of the source image height
            scale = (float) (((float) height * ratio) / (float) watermark.getHeight());

            // Create the matrix
            matrix = new Matrix();
            matrix.postScale(scale, scale);

            // Determine the post-scaled size of the watermark
            r = new RectF(0, 0, watermark.getWidth(), watermark.getHeight());
            matrix.mapRect(r);

            float h_direction = (width/2) - ((watermark.getWidth()*scale)/2);
            // Move the watermark to the bottom right corner
            matrix.postTranslate(h_direction, height - r.height() - MobiConstants.HUMM_ICON_PADDING);

            // Draw the watermark
            canvas.drawBitmap(watermark, matrix, paint);

            return bmp;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap addWhiteBorder(Bitmap bmp, int borderSize) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth(), (int)(bmp.getHeight() + borderSize * 1.1), bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bmp, 0, 0, null);
        return bmpWithBorder;
    }

    BroadcastReceiver showInsights = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                String message = intent.getStringExtra(MobiBroadcaster.ShowInsights_Tag);
                MobiLogger.printmsg("BroadcastReceiver message : " + message);
                if (message != null) {
                    manageFilters(0);
                    HummFeedAPI(true, true);
                    tvToolbarTitle.setText(getString(R.string.title_insights));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    BroadcastReceiver subCatClicked = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String message = intent.getStringExtra(MobiBroadcaster.CategoryFilter_Tag);
                if (message != null) {
                    manageFilters(1);
                    String[] temp = message.split(":");
                    categoryFilter = temp[0];
                    subCategoryFilter = temp[1];
                    HummFeedAPI(true, true);
                    tvToolbarTitle.setText(subCategoryFilter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    BroadcastReceiver searchClicked = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String message = intent.getStringExtra(MobiBroadcaster.SearchFeeds_Tag);
                if (message != null) {

                    manageFilters(2);
                    String[] temp = message.split(":");
                    specificFeedId = temp[0];
                    searchKeyword = temp[1];
                    HummFeedAPI(true, true);
                    tvToolbarTitle.setText("\"" + searchKeyword + "\"");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    BroadcastReceiver resetHumm = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String message = intent.getStringExtra(MobiBroadcaster.HummReset_Tag);
                if (message != null) {
                    manageFilters(4);
                    HummFeedAPI(true, true);
                    tvToolbarTitle.setText(getString(R.string.drawer_humm));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    BroadcastReceiver insightsFilter = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String message = intent.getStringExtra(MobiBroadcaster.ShowPInsights_Tag);
                if (message != null) {
                    insightId = message;
                    manageFilters(3);
                    HummFeedAPI(true, true);
                    tvToolbarTitle.setText(getString(R.string.title_insights));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void manageFilters(int type) {
        switch (type) {

            //Show All Insights
            case 0:
                isShowAllInsights = true;
                applySearchfilter = false;
                applyCategroyFilter = false;
                insightFilter = false;
                break;

            //Apply sub category filter
            case 1:
                applyCategroyFilter = true;
                applySearchfilter = false;
                isShowAllInsights = false;
                insightFilter = false;
                break;

            //Show Search Results
            case 2:
                applySearchfilter = true;
                applyCategroyFilter = false;
                isShowAllInsights = false;
                insightFilter = false;
                break;
            //Apply Insight Filter
            case 3:
                applySearchfilter = false;
                applyCategroyFilter = false;
                isShowAllInsights = false;
                insightFilter = true;
                break;

            //Reset Humm
            case 4:
                applySearchfilter = false;
                applyCategroyFilter = false;
                isShowAllInsights = false;
                insightFilter = false;
                break;
            default:
                break;
        }
    }
}