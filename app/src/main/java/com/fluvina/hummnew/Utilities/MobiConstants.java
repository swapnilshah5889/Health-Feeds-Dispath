package com.fluvina.hummnew.Utilities;

import android.os.Environment;

/**
 * Created by DeskTop on 26-Oct-15.
 */
public class MobiConstants {

    public static String USER_ID = "51216";
    public static double screensize = 0;
    public static int language = 0;

    public static String YOUTUBE_DEVELOPER_KEY = "AIzaSyDH3ZP7q6YNdNpI7osbc7X2-PN08Hh4Jig";

    //MYJSON PARAM
    public static String STUFF = "stuff";
    public static String APP_TYPE = "user_app";

    public static final String CHANNEL_ID = "com.fluvina.patient.id";
    public static final String CHANNEL_NAME = "com.fluvina.patient.name";


    // API Action
    public static String ACTION_USER_OPTION_SAVE = "USEROPTIONSAVE";
    public static String ACTION_MED_CONFIRMATION_SAVE = "MEDICINE_TAKEN_CONFIRMATION";
    public static String ACTION_PROFILE_REQUIRED_SETUP_CHECK = "PROFILE_REQUIRED_SETUP_CHECK";
    public static String ACTION_ALLOW_APP_VERSION = "ALLOW_APP_VERSION";
    public static String ACTION_get_my_feeds = "get_my-feeds";
    public static String ACTION_humm_search_screen_data = "humm_search_screen_data";
    public static String ACTION_humm_search_keyword = "humm_search_keyword";
    public static String ACTION_get_humm_insights = "get_humm_insights";


    // Recycler Item Click Event
    public static String RECYCLER_ITEM_CLICK = "item_click";

    public static String NC_NewAppoTag = "new_appointment";
    public static String NC_ResheduleAppTag = "reschedule_appointment";
    public static String NC_AppoCancelTag = "appointment_cancelled";
    public static String NC_VitalUpdateTag = "vital_added";
    public static String NC_AddCareTakerTag = "add_caretaker";
    public static String NC_BeCareTakerTag = "be_caretaker";
    public static String NC_AddCareTakerUpdateTag = "add_caretaker_status";
    public static String NC_BeCareTakerUpdateTag = "be_caretaker_status";
    public static String NC_NewDietPlanTag = "new_diet";
    public static String NC_NewLabReportsTag = "new_report";
    public static String NC_LabReportStatusTag = "lab_report_status";
    public static String NC_SetTargetTag = "targets_update";
    public static String NC_ProfileUpdateTag = "profile_edited";
    public static String NC_PriscriptionTag = "new_prescription";
    public static String NC_VaccPackageAllocatedTag = "my_package_added";
    public static String NC_HealthFeedsTag = "health_feed";
    public static String NC_BirthdayWishTag = "user_birthday_wish";
    public static String NC_SpecialDayWishTag = "special_notification";
    public static String NC_AppointmentReminderTag = "appointment_reminder";
    public static String NC_InstructionReminderTag = "instruction_reminder";
    public static String NC_VitalUpdateReminderTag = "vital_update_reminder";
    public static String NC_Dr_BirthdayWishTag = "dr_birthday_wish";
    public static String NC_EducationInstructionTag = "education_instruction";
    public static String NC_AFMorningReminderTag = "af_food_morning_reminder";
    public static String NC_BFMorningReminderTag = "bf_food_morning_reminder";
    public static String NC_AFNoonReminderTag = "af_food_noon_reminder";
    public static String NC_BFNoonReminderTag = "bf_food_noon_reminder";
    public static String NC_AFEveningReminderTag = "af_food_evening_reminder";
    public static String NC_BFEveningReminderTag = "bf_food_evening_reminder";
    public static String NC_JoinTelemedicineAppointmentTag = "join_telemedicine_appointment";
    public static String NC_EarlyTelemedicineAppointmentTag = "early_telemedicine_appointment ";
    public static String NC_extend_appointment_minutesTag = "extend_appointment_minutes";
    public static String NC_lab_report_upload = "lab_report_upload";


    // Health Inshorts
    public static int HIS_PAGE_TYPE_IMAGE_TEXT = 0;
    public static int HIS_PAGE_TYPE_VIDEO_TEXT = 1;
    public static int HIS_PAGE_TYPE_IMAGE = 2;
    public static int HIS_PAGE_TYPE_VIDEO = 3;
    public static int HIS_PAGE_TYPE_TEXT = 4;
    public static int HIS_PAGE_TYPE_QUIZ_SURVEY_IMAGE_TEXT = 5;
    public static int HIS_PAGE_TYPE_QUIZ_SURVEY_VIDEO_TEXT = 6;
    public static int HIS_PAGE_TYPE_QUIZ_SURVEY_TEXT = 7;
    public static int HIS_PAGE_TYPE_MEDICINE_CONFIRMATION = 8; // Only for Patient App
    public static int HIS_PAGE_TYPE_NORMAL_INSTRUCTION = 9;
    public static int HIS_PAGE_TYPE_ADVANCE_INSTRUCTION = 10;
    public static int HIS_PAGE_TYPE_VACCINE_CONFIRMATION = 11;
    public static int HIS_PAGE_TYPE_FOOD_REMINDER = 12;
    public static int HIS_PAGE_TYPE_GROWTH_DEVELOPMENT = 13;
    public static int HIS_PAGE_TYPE_VACCINE_REMINDER = 14;
    public static int HIS_PAGE_TYPE_INFO_SLIDER = 15;
    public static int HIS_PAGE_TYPE_INFO_SLIDER_TRANSPARENT = 16;
    public static int HIS_PAGE_TYPE_INFO_SLIDER_CARD = 17;

    public static int HIS_VIDEO_TYPE_YOUTUBE = 1;
    public static int HIS_VIDEO_TYPE_WEBVIEW = 2;
    public static int HIS_VIDEO_TYPE_OTHER = 3;

    public static String HIS_MEDIA_TYPE_VIDEO = "1";
    public static String HIS_MEDIA_TYPE_IMAGE = "2";
    public static String HIS_MEDIA_TYPE_GIF = "3";



    public static String FIREBASE_ANALYTICS_SHARE_INSHORT = "share_inshort";


    public final static int READ_PHONE_STATE = 0;
    public final static int ACCESS_FINE_LOCATION = 1;
    public static final double HUMM_FULL_IMAGE_RATIO = 1.78;
    public static final double HUMM_FULL_WIDTH_SHARE = 0.95;
    public static final int BLUR_RADIUS = 20;
    public static final int BLUR_SAMPLING = 5;
    public final static String HUMM_FETCH_MORE_FEEDS = "1";
    public final static String HUMM_ONLY_SAVE_READ_FEEDS = "0";
    public final static int HUMM_PRE_CALL_NUMBER = 5;


    public final static int DISPLAY_DATA = 0;
    public final static int DISPLAY_NO_DATA = 1;
    public final static int DISPLAY_NO_INTERNET = 2;
    public final static int DISPLAY_TRY_AGAIN = 3;
    public static final float HUMM_ICON_SCALE = 0.05f;
    public static final int HUMM_ICON_PADDING = 5;

    public static String SHARED_INSHORTS_DIRECTORY = Environment.getExternalStorageDirectory() + "/.MobiHealth/HealthInShorts/";
}
