package com.fluvina.hummnew.Utilities;

import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.fluvina.hummnew.MyApplication;


/**
 * Created by DeskTop on 27-May-16.
 */
public class MobiBroadcaster {
    //Broadcast Tags
    public static String UPDATE_PROFILEPIC_TAG = "UPDATE_PROFILEPIC_TAG"; //updateprofilepic
    public static String UPDATE_DASHBOARD_TAG = "UPDATE_DASHBOARD_TAG"; // UpdateDashboard
    public static String REFRESH_MY_CARETAKER_TAG = "REFRESH_MY_CARETAKER_TAG";
    public static String REFRESH_BE_CARETAKER_TAG = "REFRESH_BE_CARETAKER_TAG"; //refresh_becaretaker
    public static String UPDATE_NOTIFICATION_COUNT_TAG = "UPDATE_NOTIFICATION_COUNT_TAG";
    public static String APPO_EXTEND_TELE_APPO_TIME_TAG = "APPO_EXTEND_TELE_APPO_TIME_TAG";

    //Broadcast Events
    public static String UPDATE_PROFILEPIC_EVENT = "UPDATE_PROFILEPIC_EVENT";
    public static String UPDATE_DASHBOARD_EVENT = "UPDATE_DASHBOARD_EVENT";
    public static String REFRESH_MY_CARETAKER_EVENT = "REFRESH_MY_CARETAKER_EVENT";
    public static String REFRESH_BE_CARETAKER_EVENT = "REFRESH_BE_CARETAKER_EVENT";
    public static String UPDATE_NOTIFICATION_COUNT_EVENT = "UPDATE_NOTIFICATION_COUNT_EVENT";
    public static String APPO_EXTEND_TELE_APPO_TIME = "APPO_EXTEND_TELE_APPO_TIME";
    public static String ShowInsights = "ShowInsights";
    public static String ShowInsights_Tag = "ShowInsights_Tag";
    public static String SearchFeeds = "SearchFeeds";
    public static String SearchFeeds_Tag = "SearchFeeds_Tag";
    public static String CategoryFilter = "CategoryFilter";
    public static String CategoryFilter_Tag = "CategoryFilter_Tag";
    public static String HummReset = "HummReset";
    public static String HummReset_Tag = "HummReset_Tag";
    public static String ShowPInsights = "ShowPInsights";
    public static String ShowPInsights_Tag = "ShowPInsights_Tag";



    public static void Mobi_REFRESH_BE_CARETAKER_EVENT() {
        Intent intentNewPush = new Intent(REFRESH_BE_CARETAKER_EVENT);
        intentNewPush.putExtra(REFRESH_BE_CARETAKER_TAG, REFRESH_BE_CARETAKER_TAG);
        LocalBroadcastManager.getInstance(MyApplication.app_context).sendBroadcast(intentNewPush);
    }

    public static void Mobi_REFRESH_MY_CARETAKER_EVENT() {
        Intent intentNewPush = new Intent(REFRESH_MY_CARETAKER_EVENT);
        intentNewPush.putExtra(REFRESH_MY_CARETAKER_TAG, REFRESH_MY_CARETAKER_TAG);
        LocalBroadcastManager.getInstance(MyApplication.app_context).sendBroadcast(intentNewPush);
    }

    public static void Mobi_UPDATE_PROFILEPIC_EVENT() {
        Intent intentNewPush = new Intent(UPDATE_PROFILEPIC_EVENT);
        intentNewPush.putExtra(UPDATE_PROFILEPIC_TAG, UPDATE_PROFILEPIC_TAG);
        LocalBroadcastManager.getInstance(MyApplication.app_context).sendBroadcast(intentNewPush);
    }



    public static void Mobi_Update_Notification_count(String unreadCount, boolean isCallApi) {
        Intent intentNewPush = new Intent(UPDATE_NOTIFICATION_COUNT_EVENT);
        intentNewPush.putExtra(UPDATE_NOTIFICATION_COUNT_TAG, UPDATE_NOTIFICATION_COUNT_TAG);
        intentNewPush.putExtra("unreadCount", unreadCount);
        intentNewPush.putExtra("isCallApi", isCallApi);
        LocalBroadcastManager.getInstance(MyApplication.app_context).sendBroadcast(intentNewPush);
    }


    public static void Mobi_Extend_TeleAppointment_Time(String minutes, String appointmentId) {
        Intent intentNewPush = new Intent(APPO_EXTEND_TELE_APPO_TIME);
        intentNewPush.putExtra(APPO_EXTEND_TELE_APPO_TIME_TAG, APPO_EXTEND_TELE_APPO_TIME_TAG);
        intentNewPush.putExtra("minutes", minutes);
        intentNewPush.putExtra("appointmentId", appointmentId);
        LocalBroadcastManager.getInstance(MyApplication.app_context).sendBroadcast(intentNewPush);
    }

    public static void Mobi_ShowInsights() {
        Intent intentNewPush = new Intent(ShowInsights);
        intentNewPush.putExtra(ShowInsights_Tag, ShowInsights_Tag);
        LocalBroadcastManager.getInstance(MyApplication.app_context).sendBroadcast(intentNewPush);
    }

    public static void Mobi_HummCategoryFilter(String catName) {
        Intent intentNewPush = new Intent(CategoryFilter);
        intentNewPush.putExtra(CategoryFilter_Tag, catName);
        LocalBroadcastManager.getInstance(MyApplication.app_context).sendBroadcast(intentNewPush);
    }

    public static void Mobi_HummSearchFeed(String feedData) {
        Intent intentNewPush = new Intent(SearchFeeds);
        intentNewPush.putExtra(SearchFeeds_Tag, feedData);
        LocalBroadcastManager.getInstance(MyApplication.app_context).sendBroadcast(intentNewPush);
    }

    public static void Mobi_ResetHumm() {
        Intent intentNewPush = new Intent(HummReset);
        intentNewPush.putExtra(HummReset_Tag, HummReset_Tag);
        LocalBroadcastManager.getInstance(MyApplication.app_context).sendBroadcast(intentNewPush);
    }

    public static void Mobi_ShowParticularInsights(String Id) {
        Intent intentNewPush = new Intent(ShowPInsights);
        intentNewPush.putExtra(ShowPInsights_Tag, Id);
        LocalBroadcastManager.getInstance(MyApplication.app_context).sendBroadcast(intentNewPush);
    }

}
