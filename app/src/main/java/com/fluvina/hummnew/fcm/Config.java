package com.fluvina.hummnew.fcm;


public class Config {

    // global topic to receive app wide push notifications
    public  final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public  final String REGISTRATION_COMPLETE = "registrationComplete";
    public  final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public  final int NOTIFICATION_ID = 100;
    public  final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public  final String SHARED_PREF = "ah_firebase";
}