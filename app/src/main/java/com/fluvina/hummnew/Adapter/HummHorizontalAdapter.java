package com.fluvina.hummnew.Adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.fluvina.hummnew.Fragments.DashboardHummFeedDetailFragment;
import com.fluvina.hummnew.Fragments.DashboardHummFeedFragment;
import com.fluvina.hummnew.Utilities.MobiUtility;

public class HummHorizontalAdapter extends FragmentStateAdapter {

    final int PAGE_COUNT = 2;
    Context context;
    String TAG = "HummHorizontalAdapter :--> ";
    String health_feed_id;
    String is_survey_quiz_id;
    boolean isFromNotification;

    public HummHorizontalAdapter(FragmentManager fm, Lifecycle lifecycle, String health_feed_id, String is_survey_quiz_id,
                                 boolean isFromNotification) {
        super(fm, lifecycle);
        this.health_feed_id = health_feed_id;
        this.is_survey_quiz_id = is_survey_quiz_id;
        this.isFromNotification = isFromNotification;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                DashboardHummFeedFragment feedFragment = new DashboardHummFeedFragment();

                if (!MobiUtility.isStringEmpty(health_feed_id) || !MobiUtility.isStringEmpty(is_survey_quiz_id)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("health_feed_id", health_feed_id);
                    bundle.putString("is_survey_quiz_id", is_survey_quiz_id);
                    bundle.putBoolean("isFromNotification", isFromNotification);

                    feedFragment.setArguments(bundle);
                }
                return feedFragment;
            case 1:
                DashboardHummFeedDetailFragment detailFragment = new DashboardHummFeedDetailFragment();
                return detailFragment;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return PAGE_COUNT;
    }
}

