package com.fluvina.hummnew.Fragments;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fluvina.hummnew.R;
import com.fluvina.hummnew.Utilities.SessionManager;

import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardHummFeedDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardHummFeedDetailFragment extends Fragment {

    WebView dashboard_blogs_dashboard_fragment_layout_webview;
    TextView dashboard_blogs_dashboard_fragment_layout_back_webview;

    ProgressBar progressBar;

    SessionManager sessionManager;

    //Toolbar
    LinearLayout ll_back;
    TextView tv_toolbar_title;
    String title = "";

    @Override
    public void onPause() {
        super.onPause();
        clearWebView();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideParentToolBar();
        try {
            clearWebView();
            Fragment fragmentNew = getParentFragment();
            if (fragmentNew instanceof DashboardHummMainFragment) {
                String url = ((DashboardHummMainFragment) fragmentNew).getCurrentUrl();
                int feedId = ((DashboardHummMainFragment) fragmentNew).getCurrentFeedId();
                dashboard_blogs_dashboard_fragment_layout_webview.loadUrl(url);
                saveIdInSession(feedId);
                setTitle(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveIdInSession(int feedId) {

        ArrayList<Integer> list = sessionManager.getReadFeedIds();
        if (!list.contains(feedId)) {
            list.add(feedId);
        }

        sessionManager.setReadFeedIds(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard_humm_feed_detail, container, false);

        dashboard_blogs_dashboard_fragment_layout_back_webview = rootView.findViewById(R.id.dashboard_blogs_dashboard_fragment_layout_back_webview);
        dashboard_blogs_dashboard_fragment_layout_webview = rootView.findViewById(R.id.dashboard_blogs_dashboard_fragment_layout_webview);
        progressBar = rootView.findViewById(R.id.progressBar);
        ll_back = rootView.findViewById(R.id.llToolbarLeft);
        tv_toolbar_title = rootView.findViewById(R.id.tvToolbarTitle);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        tv_toolbar_title.setText("");

        sessionManager = new SessionManager(getActivity());
        progressBar.setMax(100);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getParentFragment();
                if (fragment instanceof DashboardHummMainFragment) {
                    ((DashboardHummMainFragment) fragment).setCurrentPage(0);
                    clearWebView();
                } else {

                }
            }
        });


        WebSettings webSettings = dashboard_blogs_dashboard_fragment_layout_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        dashboard_blogs_dashboard_fragment_layout_webview.setEnabled(true);


        dashboard_blogs_dashboard_fragment_layout_webview.setWebChromeClient(new WebChromeClient() {
            // SSL error handler
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }

                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });

        dashboard_blogs_dashboard_fragment_layout_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                hideParentToolBar();
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideParentToolBar();
                progressBar.setVisibility(View.GONE);
                progressBar.setProgress(100);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
            }
        });

        loadBundle();
        hideParentToolBar();

        dashboard_blogs_dashboard_fragment_layout_back_webview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (dashboard_blogs_dashboard_fragment_layout_webview.canGoBack()) {
                        dashboard_blogs_dashboard_fragment_layout_webview.goBack();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void hideParentToolBar() {
        try {
            Fragment fragment = getParentFragment();
            if (fragment instanceof DashboardHummMainFragment) {
                ((DashboardHummMainFragment) fragment).hideToolBar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearWebView() {
        try {
            dashboard_blogs_dashboard_fragment_layout_webview.loadUrl("about:blank");
            setTitle("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBundle() {
        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                String currentUrl = "";
                int currentFeedId = 0;
                if (getArguments().containsKey("currentFeedId")) {
                    currentFeedId = getArguments().getInt("currentFeedId");
                }
                if (getArguments().containsKey("currentUrl")) {
                    currentUrl = getArguments().getString("currentUrl");
                }
                if (currentUrl == null || currentUrl.isEmpty()) {
                    clearWebView();
                } else {
                    dashboard_blogs_dashboard_fragment_layout_webview.loadUrl(currentUrl);
                    saveIdInSession(currentFeedId);
                    setTitle(currentUrl);
                }
            } else if (getParentFragment() != null) {
                DashboardHummMainFragment fragmentNew = (DashboardHummMainFragment) getParentFragment();
                String url = fragmentNew.getCurrentUrl();
                int feedId = fragmentNew.getCurrentFeedId();
                dashboard_blogs_dashboard_fragment_layout_webview.loadUrl(url);
                saveIdInSession(feedId);
                setTitle(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onBackPressed() {
        try {
//            if (dashboard_blogs_dashboard_fragment_layout_webview.canGoBack()) {
//                dashboard_blogs_dashboard_fragment_layout_webview.goBack();
//            } else {
            Fragment fragment = getParentFragment();
            if (fragment instanceof DashboardHummMainFragment) {
                ((DashboardHummMainFragment) fragment).setCurrentPage(0);
            }
            clearWebView();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTitle(String feedurl){
        try {
            if(feedurl.isEmpty()){
                tv_toolbar_title.setText("");
            }
            else{
                java.net.URL url  = new URL(feedurl);
                title = url.getHost().toLowerCase();
                title = title.replace("www.","");
                tv_toolbar_title.setText(title);
            }
        }
        catch (Exception e){e.printStackTrace();}
    }
}