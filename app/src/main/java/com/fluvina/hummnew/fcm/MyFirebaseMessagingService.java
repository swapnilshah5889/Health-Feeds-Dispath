package com.fluvina.hummnew.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.fluvina.hummnew.Activities.MainActivity;
import com.fluvina.hummnew.Activities.Splashscreen;
import com.fluvina.hummnew.R;
import com.fluvina.hummnew.Utilities.DBHelper;
import com.fluvina.hummnew.Utilities.MobiBroadcaster;
import com.fluvina.hummnew.Utilities.MobiConstants;
import com.fluvina.hummnew.Utilities.MobiLogger;
import com.fluvina.hummnew.Utilities.MobiUtility;
import com.fluvina.hummnew.Utilities.SessionManager;
import com.fluvina.hummnew.network.ApiClient;
import com.fluvina.hummnew.network.ApiService;
import com.fluvina.hummnew.network.Apis;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fluvina.hummnew.Utilities.MobiConstants.CHANNEL_ID;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    // Sets an ID for the notification, so it can be updated
    public static final int notifyID = 9002;
    NotificationCompat.Builder mNotifyBuilder;
    NotificationManager mNotificationManager;
    SessionManager session;
    DBHelper db;

    @Override
    public void onNewToken(@NonNull String refreshedToken) {
        super.onNewToken(refreshedToken);

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
//        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
//        registrationComplete.putExtra("token", refreshedToken);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {

    }

    private void storeRegIdInPref(String token) {

        //Getting registration token
        session = new SessionManager(getApplicationContext());
        session.setDeviceToken(token);

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        try {
            session = new SessionManager(getApplicationContext());
            db = new DBHelper(getApplicationContext());

            Log.e(TAG, "json From: " + remoteMessage.getFrom());

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
                handleDataMessage(remoteMessage.getNotification().getBody());
            }

            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {

                try {
                    Log.e(TAG, "json Data Payload: " + remoteMessage.getData().toString());
                    JSONObject json = new JSONObject(remoteMessage.getData());
                    //handleDataMessage(remoteMessage.getData().toString());
                    //handleDataMessage(json.toString());

                    ServerMessage(json);

                } catch (Exception e) {
                    Log.e(TAG, "json Exception: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "remoteMessage Exception: " + e.getMessage());
        }
        try {
            MobiBroadcaster.Mobi_Update_Notification_count("", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 /*   private void handleNotification(String message) {
        //if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
        // app is in foreground, broadcast the push message
        Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
        pushNotification.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

        // play notification sound
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.playNotificationSound();
        // } else {
        // If the app is in background, firebase itself handles the notification
        //}
    }*/

    private void handleDataMessage(String json) {
        Log.e(TAG, "json push json: " + json);

        try {
            //temp
            String title = getResources().getString(R.string.app_name);
            String message = json;
            String imageUrl = null;
            long timestamp = System.currentTimeMillis();


            Intent resultIntent;

            if (isJSONValid(message)) {

                JSONObject obj = new JSONObject(message);
                String data = obj.getString("data");
                JSONObject object = new JSONObject(data);

                resultIntent = new Intent(getApplicationContext(), Splashscreen.class);
                try {
                    message = object.getString("pushMessage");
                    resultIntent.putExtra("message", message);
                    resultIntent.putExtra("user_notificationId", object.getString("user_notificationId"));
                    resultIntent.putExtra("notificationType", object.getString("notificationType"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                message = json;
                resultIntent = new Intent(getApplicationContext(), Splashscreen.class);
                resultIntent.putExtra("message", message);

            }
            // app is in background, show the notification in notification tray
            // check for image attachment
            if (imageUrl == null || TextUtils.isEmpty(imageUrl)) {
                showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
            } else {
                // image is present, show notification with image
                showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
            }

        } catch (Exception e) {
            Log.e(TAG, "json Json Exception: " + e.getMessage());
        }
    }

    private void ServerMessage(JSONObject json) {
        String message = "";
        try {
            message = json.has("message") ? json.getString("message") : json.getString("m");

            String msg = message;
            MobiLogger.printmsg("sendNotification msg :" + msg);
            /// msg = "{\"tag\":\"special_notification\",\"result\":{\"message\":\"Dear VISHAL  I hope your special day will bring you lots of happiness, love and fun. You deserve them a lot. Enjoy\",\"lang\":\"en\",\"msg_in_hi\":\"à¤®à¤¾à¤¨à¤¨à¥€à¤¯ VISHAL  à¤®à¥à¤à¥‡ à¤‰à¤®à¥à¤®à¥€à¤¦ à¤¹à¥ˆ à¤•à¤¿ à¤†à¤ªà¤•à¤¾ à¤µà¤¿à¤¶à¥‡à¤· à¤¦à¤¿à¤¨ à¤†à¤ªà¤•à¥‡ à¤²à¤¿à¤ à¤¢à¥‡à¤° à¤¸à¤¾à¤°à¥€ à¤–à¥à¤¶à¤¿à¤¯à¤¾à¤‚, à¤ªà¥à¤¯à¤¾à¤° à¤”à¤° à¤®à¤¸à¥à¤¤à¥€ à¤²à¥‡à¤•à¤° à¤†à¤à¤—à¤¾à¥¤ à¤†à¤ª à¤‰à¤¨à¤•à¥‡ à¤¬à¤¹à¥à¤¤\",\"msg_in_gu\":\"àª®àª¾àª¨àª¨à«€àª¯ VISHAL  àª¹à«àª‚ àª†àª¶àª¾ àª°àª¾àª–à«àª‚ àª›à«àª‚ àª•à«‡ àª¤àª®àª¾àª°à«‹ àª–àª¾àª¸ àª¦àª¿àªµàª¸ àª¤àª®àª¨à«‡ àª˜àª£à«€ àª¬àª§à«€ àª–à«àª¶à«€àª“, àªªà«àª°à«‡àª® àª…àª¨à«‡ àª†àª¨àª‚àª¦ àª²àª¾àªµàª¶à«‡. àª¤àª®à«‡ àª¤à«‡àª®àª¨à«‡ àª–à«‚àª¬ àª²àª¾àª¯àª• àª›à«‹.\",\"msg_in_en\":\"Dear VISHAL  I hope your special day will bring you lots of happiness, love and fun. You deserve them a lot. Enjoy\",\"msg_title_en\":\"Wishing Day\",\"msg_title_gu\":\"Wishing Day\",\"msg_title_hi\":\"Wishing Day\",\"msg_title\":\"Wishing Day\",\"media_url\":\"http:\\/\\/dev.mobihealth.in\\/upload\\/special_notification_media\\/5e219dd1261291579261393156.jpg\",\"media_type\":\"1\",\"video_type\":\"0\"},\"notification_send_date_time\":\"17-01-2020 05:58 PM\"}";
            if (msg.contains("tag")) {
                try {
                    JSONObject jsonObject = new JSONObject(msg);
                    String tag = jsonObject.getString("tag");

                    String notification_header = "";
                    String notification_message = "";
                    String notification_send_date_time = "";
                    String imageUrl = "";
                    long timestamp = System.currentTimeMillis();

                    if (jsonObject.has("notification_send_date_time")) {
                        notification_send_date_time = jsonObject.getString("notification_send_date_time");
                    }

                    if (jsonObject.has("notification_header")) {
                        notification_header = jsonObject.getString("notification_header");
                    } else {
                        notification_header = getString(R.string.app_name);
                    }

                    if (jsonObject.has("notification_message")) {
                        notification_message = jsonObject.getString("notification_message");
                    } else {
                        notification_message = getString(R.string.txt_notification_new_notification);
                    }

                    if (jsonObject.has("imageUrl")) {
                        imageUrl = jsonObject.getString("imageUrl");
                    }
                    if (!MobiUtility.isStringEmpty(notification_send_date_time)) {
                        timestamp = NotificationUtils.getTimeMilliSec(notification_send_date_time);
                    }

                    if (tag.equalsIgnoreCase(MobiConstants.NC_NewAppoTag)) {

//                        JSONObject resultobj = jsonObject.getJSONObject("result");
                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notificationIntent.putExtra("isFromNotification", true);
                        notificationIntent.putExtra("tag", tag);
                        ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                    } else if (tag.equalsIgnoreCase(MobiConstants.NC_AppoCancelTag)) {

//                        JSONObject resultobj = jsonObject.getJSONObject("result");
                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notificationIntent.putExtra("isFromNotification", true);
                        notificationIntent.putExtra("tag", tag);
                        ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                    } else if (tag.equalsIgnoreCase(MobiConstants.NC_ResheduleAppTag)) {

//                        JSONObject resultobj = jsonObject.getJSONObject("result");
                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notificationIntent.putExtra("isFromNotification", true);
                        notificationIntent.putExtra("tag", tag);
                        ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                    } else if (tag.equalsIgnoreCase(MobiConstants.NC_VitalUpdateTag)) {

//                        JSONObject result = jsonObject.getJSONObject("result");
                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notificationIntent.putExtra("isFromNotification", true);
                        notificationIntent.putExtra("tag", tag);
                        ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                    } else if (tag.equalsIgnoreCase(MobiConstants.NC_AddCareTakerTag)) {
                        JSONArray result = jsonObject.getJSONArray("result");
                        if (result.length() > 0) {
                            JSONArray care_taker_data_arr = result.getJSONArray(0);
                            if (care_taker_data_arr.length() > 0) {
                                String name = care_taker_data_arr.getString(0);
                                String phone = care_taker_data_arr.getString(1);
                                String relation = care_taker_data_arr.getString(2);
                                String status = care_taker_data_arr.getString(3);
                                String shared = care_taker_data_arr.getString(4);
                                String req_date = care_taker_data_arr.getString(5);
                                String requestId = care_taker_data_arr.getString(6);
                                String profileurl = care_taker_data_arr.getString(7);


                                if (!db.ismycaretakerAvailable(requestId)) {
                                    db.insertIntoMyCaretakers(name, phone, relation, status, shared, req_date, requestId, profileurl);
                                    //ShowNotification("You Added " + name + " as your Caretaker");
                                } else {
                                    MobiLogger.printmsg("already avilable my care taker id : " + requestId);
                                }
                                Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                                notificationIntent.putExtra("isFromNotification", true);
                                notificationIntent.putExtra("tag", tag);
                                ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                            } else {
                                MobiLogger.printmsg("care_taker_data_arr length 0");
                            }
                        } else {
                            MobiLogger.printmsg("result length 0");
                        }

                        MobiBroadcaster.Mobi_REFRESH_MY_CARETAKER_EVENT();

                    } else if (tag.equalsIgnoreCase(MobiConstants.NC_AddCareTakerUpdateTag)) {

                        ShowNotification("You have update in care taker !");
                        JSONArray result = jsonObject.getJSONArray("result");
                        if (result.length() > 0) {
                            JSONArray arr = result.getJSONArray(0);
                            String requestId = arr.getString(0);
                            String status = arr.getString(1);
                            if (status.equalsIgnoreCase("3")) {
                                db.deleteMyCaretaker(requestId);
                            } else {
                                db.updateStatusOfMyCaretaker(requestId, status);
                            }
                        } else {
                            MobiLogger.printmsg("add_caretaker_status result 0");
                        }

                        MobiBroadcaster.Mobi_REFRESH_MY_CARETAKER_EVENT();

                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notificationIntent.putExtra("isFromNotification", true);
                        notificationIntent.putExtra("tag", tag);
                        ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                    } else if (tag.equalsIgnoreCase(MobiConstants.NC_BeCareTakerTag)) {
                        JSONArray result = jsonObject.getJSONArray("result");
                        if (result.length() > 0) {
                            JSONArray care_taker_data_arr = result.getJSONArray(0);
                            if (care_taker_data_arr.length() > 0) {
                                String name = care_taker_data_arr.getString(0);
                                String phone = care_taker_data_arr.getString(1);
                                String relation = care_taker_data_arr.getString(2);
                                String status = care_taker_data_arr.getString(3);
                                String shared = care_taker_data_arr.getString(4);
                                String req_date = care_taker_data_arr.getString(5);
                                String requestId = care_taker_data_arr.getString(6);
                                String profileurl = care_taker_data_arr.getString(7);

                                if (!db.isbecaretakerAvailable(requestId)) {
                                    db.insertIntoBeCaretakers(name, phone, relation, status, shared, req_date, requestId, profileurl);
                                } else {
                                    MobiLogger.printmsg("isbecaretakerAvailable available");
                                }

                                Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                                notificationIntent.putExtra("isFromNotification", true);
                                notificationIntent.putExtra("tag", tag);
                                ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);


                            } else {
                                MobiLogger.printmsg("care_taker_data_arr length 0");
                            }
                        } else {
                            MobiLogger.printmsg("result length 0");
                        }

                        MobiBroadcaster.Mobi_REFRESH_BE_CARETAKER_EVENT();

                    } else if (tag.equalsIgnoreCase(MobiConstants.NC_BeCareTakerUpdateTag)) {
                        JSONArray result = jsonObject.getJSONArray("result");
                        if (result.length() > 0) {
                            JSONArray arr = result.getJSONArray(0);
                            String requestId = arr.getString(0);
                            String status = arr.getString(1);

                            if (status.equalsIgnoreCase("3")) {
                                db.deleteBeCaretaker(requestId);
                            } else {
                                db.updateStatusOfBeCaretaker(requestId, status);
                            }
                        } else {
                            MobiLogger.printmsg("be_caretaker_status result 0");
                        }

                        MobiBroadcaster.Mobi_REFRESH_BE_CARETAKER_EVENT();

                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notificationIntent.putExtra("isFromNotification", true);
                        notificationIntent.putExtra("tag", tag);
                        ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);


                    } else if (tag.equalsIgnoreCase(MobiConstants.NC_NewDietPlanTag)) {
                        JSONObject result = jsonObject.getJSONObject("result");
                        boolean stat = result.getBoolean("stat");
                        if (stat) {
                            JSONArray diet_pt_details_data = result.getJSONArray("data");
                            if (diet_pt_details_data.length() > 0) {
                                for (int i = 0; i < diet_pt_details_data.length(); i++) {
                                    JSONObject obj = diet_pt_details_data.getJSONObject(i);
                                    String dr_name = obj.getString("dr_name");

                                    Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                                    notificationIntent.putExtra("isFromNotification", true);
                                    notificationIntent.putExtra("tag", tag);
                                    ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                                }
                            } else {
                                MobiLogger.printmsg("diet_pt_details_data length zero");
                            }
                        } else {
                            MobiLogger.printmsg("stat false new_diet");
                        }
                    } else if (tag.equalsIgnoreCase(MobiConstants.NC_NewLabReportsTag)) {

//                        JSONArray result = jsonObject.getJSONArray("result");

                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notificationIntent.putExtra("isFromNotification", true);
                        notificationIntent.putExtra("tag", tag);
                        ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);


                    } else if (tag.equalsIgnoreCase(MobiConstants.NC_LabReportStatusTag)) {

//                        JSONArray result = jsonObject.getJSONArray("result");
                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notificationIntent.putExtra("isFromNotification", true);
                        notificationIntent.putExtra("tag", tag);
                        ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                    } else if (tag.equalsIgnoreCase(MobiConstants.NC_ProfileUpdateTag)) {
                        JSONObject result = jsonObject.getJSONObject("result");
                        boolean stat = result.getBoolean("stat");
                        if (stat) {
                            JSONObject resultobj = result.getJSONObject("result");
                            String userContactNumber = resultobj.getString("userContactNumber");
                            String firstName = resultobj.getString("firstName");
                            String lastName = resultobj.getString("lastName");
                            String email = resultobj.getString("email");
                            String gender = resultobj.getString("gender");
                            String address1 = resultobj.getString("address1");
                            String address2 = resultobj.getString("address2");
                            String city = resultobj.getString("city");
                            String state = resultobj.getString("state");
                            String country = resultobj.getString("country");
                            String pincode = resultobj.getString("pincode");
                            String image = resultobj.getString("image");
                            String blood_group = resultobj.getString("blood_group");

                            session.setUserName(firstName);
                            //session.setmobileno(userContactNumber);
                            session.setGender(gender);
                            session.setUserImage(image);
                            MobiBroadcaster.Mobi_UPDATE_PROFILEPIC_EVENT();

                            db.UpdateProfile(firstName, lastName, userContactNumber, email, image, gender, address1, address2, city, blood_group, pincode, country, state);

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } else {
                            MobiLogger.printmsg("stat false in profile update");
                        }
                    } else if (tag.equalsIgnoreCase(MobiConstants.NC_SetTargetTag)) {

//                        JSONObject result = jsonObject.getJSONObject("result");
//                        JSONArray target = jsonObject.getJSONArray("target");
//                        JSONArray fnlarray = target.getJSONArray(target.length() - 1);
//                        String date_time = jsonObject.getString("date_time");
//                        fnlarray.put(date_time);

                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notificationIntent.putExtra("isFromNotification", true);
                        notificationIntent.putExtra("tag", tag);
                        ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

//                        RequestTargetData();

                    } else if (tag.equalsIgnoreCase(MobiConstants.NC_PriscriptionTag)) {

//                        JSONObject result = jsonObject.getJSONObject("result");
                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notificationIntent.putExtra("isFromNotification", true);
                        notificationIntent.putExtra("tag", tag);
                        ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);


                    } /*else if (tag.equals("vaccine_confirm")) {
                        try {
                            JSONObject result = jsonObject.getJSONObject("result");
                            String child_name = result.getString("child_name");
                            String confirm_vacine = result.getString("confirm_vacine");

                            if (!db.isNotificationAvailable(MobiHealthUserConstants.VaccConfTag, result.toString())) {
                                db.InsertIntoNotificationTable(MobiHealthUserConstants.VaccConfTag, result.toString(), new Date().getTime());

                                Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                                notificationIntent.putExtra("isFromNotification", true);
                                notificationIntent.putExtra("tag", tag);
                                ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals("name_changed")) {
                        try {
                            JSONObject result = jsonObject.getJSONObject("result");
                            String new_name = result.getString("new_name");
                            String old_name = result.getString("old_name");

                            if (!db.isNotificationAvailable(MobiHealthUserConstants.VaccChildNameChanged, result.toString())) {
                                db.InsertIntoNotificationTable(MobiHealthUserConstants.VaccChildNameChanged, result.toString(), new Date().getTime());

                                Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                                notificationIntent.putExtra("isFromNotification", true);
                                notificationIntent.putExtra("tag", tag);
                                ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals("growth_added")) {
                        try {
                            JSONObject result = jsonObject.getJSONObject("result");
                            if (!db.isNotificationAvailable(MobiHealthUserConstants.VaccGrowthAddedTag, result.toString())) {
                                db.InsertIntoNotificationTable(MobiHealthUserConstants.VaccGrowthAddedTag, result.toString(), new Date().getTime());

                                Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                                notificationIntent.putExtra("isFromNotification", true);
                                notificationIntent.putExtra("tag", tag);
                                ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals("dob_changed")) {
                        try {
                            JSONObject result = jsonObject.getJSONObject("result");
                            String old_dob = result.getString("old_dob");
                            String new_dob = result.getString("new_dob");

                            if (!db.isNotificationAvailable(MobiHealthUserConstants.VaccDOBChnagedTag, result.toString())) {
                                db.InsertIntoNotificationTable(MobiHealthUserConstants.VaccDOBChnagedTag, result.toString(), new Date().getTime());

                                Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                                notificationIntent.putExtra("isFromNotification", true);
                                notificationIntent.putExtra("tag", tag);
                                ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals("due_date_changed")) {
                        try {
                            JSONObject result = jsonObject.getJSONObject("result");
                            String name = result.getString("name");
                            String vaccines = result.getString("vaccines");

                            if (!db.isNotificationAvailable(MobiHealthUserConstants.VaccDueDateChangedTag, result.toString())) {
                                db.InsertIntoNotificationTable(MobiHealthUserConstants.VaccDueDateChangedTag, result.toString(), new Date().getTime());

                                Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                                notificationIntent.putExtra("isFromNotification", true);
                                notificationIntent.putExtra("tag", tag);
                                ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }*/ else if (tag.equals(MobiConstants.NC_VaccPackageAllocatedTag)) {
                        try {
                            JSONArray result = jsonObject.getJSONArray("result");
                            JSONObject object = result.getJSONObject(0);
//                            String pkg_nm = object.getString("pkg_nm");
//                            String child_name = object.getString("child_name");
//                            String dr_name = object.getString("dr_name");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_HealthFeedsTag)) {
                        try {

                            JSONObject result = jsonObject.getJSONObject("result");
                            if (notification_message.isEmpty()) {
                                try {
                                    if (result.has("msg")) {
                                        notification_message = result.getString("msg");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            String health_feed_id = result.getString("health_feed_id");
                            String is_survey_quiz_id = result.getString("is_survey_quiz_id");

                            if (result.has("image_url")) {
                                imageUrl = result.getString("image_url").isEmpty() ? imageUrl : result.getString("image_url");
                            }

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            notificationIntent.putExtra("health_feed_id", health_feed_id);
                            notificationIntent.putExtra("is_survey_quiz_id", is_survey_quiz_id);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_BirthdayWishTag)) {
                        try {

                            JSONObject result = jsonObject.getJSONObject("result");
                            //notification_message = result.getString("msg");
//                            String userId = result.getString("userId");
//                            String msg_for = result.getString("msg_for");
//                            String my_pkg_id = result.getString("my_pkg_id");
                            notification_message = result.getString("birthday_msg");
//                            String dob = result.getString("dob");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_Dr_BirthdayWishTag)) {
                        try {

                            JSONObject result = jsonObject.getJSONObject("result");
                            //notification_message = result.getString("msg");
//                            String userId = result.getString("userId");
//                            String msg_for = result.getString("msg_for");
//                            String my_pkg_id = result.getString("my_pkg_id");
                            notification_message = result.getString("birthday_msg");
//                            String dob = result.getString("dob");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_SpecialDayWishTag)) {
                        try {

//                            JSONObject result = jsonObject.getJSONObject("result");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_AppointmentReminderTag)) {
                        try {

                            JSONObject result = jsonObject.getJSONObject("result");
                            if (result.has("msg"))
                                notification_message = result.getString("msg");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_InstructionReminderTag)) {
                        try {

                            JSONObject result = jsonObject.getJSONObject("result");
                            if (result.has("msg"))
                                notification_message = result.getString("msg");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_VitalUpdateReminderTag)) {
                        try {

                            JSONObject result = jsonObject.getJSONObject("result");
                            if (result.has("msg"))
                                notification_message = result.getString("msg");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_EducationInstructionTag)) {
                        try {

                            JSONObject result = jsonObject.getJSONObject("result");
                            if (result.has("msg"))
                                notification_message = result.getString("msg");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_AFMorningReminderTag)) {
                        try {

                            JSONObject result = jsonObject.getJSONObject("result");
                            if (result.has("msg"))
                                notification_message = result.getString("msg");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_BFMorningReminderTag)) {
                        try {

                            JSONObject result = jsonObject.getJSONObject("result");
                            if (result.has("msg"))
                                notification_message = result.getString("msg");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_AFNoonReminderTag)) {
                        try {

                            JSONObject result = jsonObject.getJSONObject("result");
                            if (result.has("msg"))
                                notification_message = result.getString("msg");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_BFNoonReminderTag)) {
                        try {

                            JSONObject result = jsonObject.getJSONObject("result");
                            if (result.has("msg"))
                                notification_message = result.getString("msg");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_AFEveningReminderTag)) {
                        try {

                            JSONObject result = jsonObject.getJSONObject("result");
                            if (result.has("msg"))
                                notification_message = result.getString("msg");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_BFEveningReminderTag)) {
                        try {

                            JSONObject result = jsonObject.getJSONObject("result");
                            if (result.has("msg"))
                                notification_message = result.getString("msg");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_JoinTelemedicineAppointmentTag)) {
                        try {

//                            JSONObject result = jsonObject.getJSONObject("result");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_EarlyTelemedicineAppointmentTag)) {
                        try {

//                            JSONObject result = jsonObject.getJSONObject("result");

                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.putExtra("isFromNotification", true);
                            notificationIntent.putExtra("tag", tag);
                            ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (tag.equals(MobiConstants.NC_extend_appointment_minutesTag)) {
                        String minute = "0";
                        String appointment_id = "";
                        JSONObject result = jsonObject.getJSONObject("result");
                        if (result.has("minute"))
                            minute = result.getString("minute");
                        if (result.has("appointment_id"))
                            appointment_id = result.getString("appointment_id");

                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notificationIntent.putExtra("isFromNotification", true);
                        notificationIntent.putExtra("tag", tag);
                        ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);

                        MobiBroadcaster.Mobi_Extend_TeleAppointment_Time(minute, appointment_id);
                    } else {
                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        notificationIntent.putExtra("isFromNotification", true);
                        notificationIntent.putExtra("tag", tag);
                        ShowNewNotification(imageUrl, notification_header, notification_message, timestamp, notificationIntent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                MobiLogger.printmsg("simple message : " + msg);
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotifyBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setContentTitle("Mobihealth")
                        .setContentText(msg)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setSmallIcon(R.drawable.logo_new).setAutoCancel(true);
                mNotificationManager.notify(notifyID, mNotifyBuilder.build());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ShowNewNotification(String imageUrl, String title, String message, long timestamp, Intent resultIntent) {

        if (imageUrl == null || TextUtils.isEmpty(imageUrl)) {
            showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
        } else {
            // image is present, show notification with image
            showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
        }

    }


    public void ShowNotification(String Message) {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                notificationIntent, 0);
        mNotifyBuilder = new NotificationCompat.Builder(getApplicationContext(), MobiConstants.CHANNEL_ID)
                .setContentTitle("Mobi update !")
                .setContentText(Message)
                .setDefaults(Notification.DEFAULT_SOUND)
                //.setSound(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.doorbell_x))
                .setSmallIcon(R.drawable.logo_new).setAutoCancel(true);
        mNotifyBuilder.setContentIntent(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(MobiConstants.CHANNEL_ID, MobiConstants.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setSound(null, null);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
        }
        if (mNotificationManager != null) {
            mNotificationManager.notify(notifyID, mNotifyBuilder.build());
        }

    }


    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, long timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, long timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

    public void RequestTargetData() {
        try {
            LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
            hashMap.put("get-latest-target", "true");
//            hashMap.put("user_id", session.getuserid());
            hashMap.put("user_id", MobiConstants.USER_ID);
            hashMap.put("stuff", MobiConstants.STUFF);
            hashMap.put("app_type", MobiConstants.APP_TYPE);

            MobiLogger.printmsg("Api : " + Apis.BASE_URL + Apis.PILLBOX_API);
            MobiLogger.printMap(hashMap);

            ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = service.callChatApi(hashMap);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    boolean stat = false;
                    try {
                        if (response.body() != null) {
                            String strResponse = response.body().string();
                            MobiLogger.printmsg("response : " + strResponse);

//                            db.DeleteTargetData();
                            JSONObject targets = new JSONObject(strResponse);
                            boolean targets_stat = targets.getBoolean("stat");
                            if (targets_stat) {
                                JSONArray targets_result = targets.getJSONArray("result");
                                if (targets_result.length() > 0) {
                                    for (int i = 0; i < targets_result.length(); i++) {
                                        JSONObject obj = targets_result.getJSONObject(i);
                                        String name = obj.getString("name");
                                        String target = obj.getString("target");
                                        String scale = obj.getString("scale");
                                        String duration = obj.getString("duration");
                                        String duration_in = obj.getString("duration_in");
                                        String added_on = obj.getString("added_on");
                                        String dr_name = obj.getString("dr_name");
                                        String dr_id = obj.getString("dr_id");
                                        String value_1 = obj.getString("value_1");
                                        String value_2 = obj.getString("value_2");

//                                        db.InsertIntoTargetTable(dr_id, name, target, scale, duration, added_on, dr_name, value_1, value_2, duration_in);
                                    }
                                } else {
                                    MobiLogger.printmsg("targets_result length 0");
                                }
                            } else {
                                MobiLogger.printmsg("targets_stat false");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
