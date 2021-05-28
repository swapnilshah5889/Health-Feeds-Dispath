package com.fluvina.hummnew.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private final String PREF_NAME = "MobihealthPref";

    // All Shared Preferences Keys
    private final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public final String KEY_ID = "user_id";
    public final String KEY_NAME = "user_name";
    public final String KEY_EMAIL = "user_email";
    public final String KEY_MOBILE = "user_mobile";
    public final String KEY_GENDER = "user_gender";
    public final String KEY_IMAGE = "user_image";
    public final String KEY_BDate = "birth_date";
    public final String ONGOING_APPO_ID = "ongoing_appointment";

    public final String REG_ID = "regId";
    public final String DEVICE_TOKEN = "deviceToken";

    public final String AllMedType = "AllMedType";
    public final String MedFriend = "MedFriend";
    public final String PillDose = "PillDose";
    public final String PillDoctor = "PillDoctor";
    public final String AddedMedicine = "AddedMedicine";
    public final String MypillJson = "MypillJson";
    public final String PillDocSpeciality = "PillDocSpeciality";
    public final String SelectedPillId = "SelectedPillId";


    String CVChildName = "CVChildName";
    String CVChildAge = "CVChildAge";
    String CVChildGender = "CVChildGender";
    String CVChildImage = "CVChildImage";
    String CVNextVaccine = "CVNextVaccine";
    String CVDueDate = "CVDueDate";
    String CVMobileno = "CVMobileno";
    String CVBirthdate = "CVBirthdate";
    String CVParentname = "CVParentname";
    String CVRelation = "CVRelation";
    String only_child_name = "only_child_name";
    String my_pkg_id = "my_pkg_id";



    String Activecardjson = "Activecardjson";


    String this_dr = "this_dr";
    String this_patient = "this_patient";
    String InsDate = "InsDate";


    //New
    String ReadFeedIds = "ReadFeedIds";
    String Login_setup_req = "required_setup";
    String Login_profile_completed = "profile_completed";
    String Login_disease_selected = "disease_selected";
    String Login_setup_json = "setup_json";

    public final String DEVICE_LANGUAGE = "deviceLanguage";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String id, String username, String mobile,
                                   String dev_id, String birthdate, String password, String regid,
                                   String gender, String image, String email) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing id in pref
        editor.putString(KEY_ID, id);

        // Storing Name in pref
        editor.putString(KEY_NAME, username);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        //Stroring mobile
        editor.putString(KEY_MOBILE, mobile);


        // Storing Name in pref
        editor.putString(KEY_GENDER, gender);

        // Storing Name in pref
        editor.putString(KEY_IMAGE, image);

        editor.putString("DEV_ID", dev_id);
        editor.putString("PASSWORD", password);
        editor.putString("REG_ID", regid);

        if (birthdate.equalsIgnoreCase("")) {
            editor.putString(KEY_BDate, "00-00-0000");
        } else {
            editor.putString(KEY_BDate, birthdate);
        }

        // commit changes
        editor.commit();
    }

    public void setIsLogIn(boolean value) {

        // Storing login value
        editor.putBoolean(IS_LOGIN, value);
        editor.commit();
    }


    public boolean isLogIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setUserId(String value) {

        // Storing login value
        editor.putString(KEY_ID, value);
        editor.commit();
    }

    public void setUserName(String value) {

        // Storing login value
        editor.putString(KEY_NAME, value);
        editor.commit();
    }

    public void setUserEmail(String value) {

        // Storing login value
        editor.putString(KEY_EMAIL, value);
        editor.commit();
    }

    public void setGender(String value) {

        // Storing login value
        editor.putString(KEY_GENDER, value);
        editor.commit();
    }

    public void setUserImage(String value) {

        // Storing login value
        editor.putString(KEY_IMAGE, value);
        editor.commit();
    }

    public void setUserBDate(String value) {

        // Storing login value
        editor.putString(KEY_BDate, value);
        editor.commit();
    }

    public void setReadFeedIds(ArrayList<Integer> arrayIds) {

        try {
            Gson gson = new Gson();
            String listIds = gson.toJson(
                    arrayIds,
                    new TypeToken<ArrayList<Integer>>() {
                    }.getType());
            JSONArray jArray = new JSONArray(listIds);
            editor.putString(ReadFeedIds, jArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public ArrayList<Integer> getReadFeedIds() {

        ArrayList<Integer> array = new ArrayList<Integer>();
        String ids = pref.getString(ReadFeedIds, "");

        if (ids.equals("")) {
            return array;
        } else {
            try {
                JSONArray jArray = new JSONArray(ids);
                for (int i = 0; i < jArray.length(); i++) {
                    array.add(jArray.getInt(i));
                }
                return array;
            } catch (JSONException e) {
                return array;
            }
        }
    }

    public void setReqSetup(int value) {
        editor.putInt(Login_setup_req, value);
        editor.commit();
    }


    public int getReqSetup() {
        return pref.getInt(Login_setup_req, 1);
    }

    public void setDisease_profile_completed(boolean value) {
        editor.putBoolean(Login_profile_completed, value);
        editor.commit();
    }

    public void setLogin_setup_json(String json) {
        editor.putString(Login_setup_json, json);
        editor.commit();
    }

    public String getLogin_setup_json() {
        return pref.getString(Login_setup_json, new JSONObject().toString());
    }


    public boolean getDisease_profile_completed() {
        return pref.getBoolean(Login_profile_completed, false);
    }

    public void setInsDate(String value) {
        editor.putString(InsDate, value);
        editor.commit();
    }


    public String getInsDate() {
        return pref.getString(InsDate, "");
    }


    public void serPatientIdChat(String value) {
        editor.putString(this_patient, value);
        editor.commit();
    }

    public String getPatientIdChat() {
        return pref.getString(this_patient, "");
    }

    public void setDrIdOfChat(String value) {
        editor.putString(this_dr, value);
        editor.commit();
    }

    public String getDrIdChat() {
        return pref.getString(this_dr, "");
    }


    public String getSelectedPillId() {
        return pref.getString(SelectedPillId, "");
    }

    public void setSelectedPillId(String value) {
        editor.putString(SelectedPillId, value);
        editor.commit();
    }


    public String getPillDocSpeciality() {
        return pref.getString(PillDocSpeciality, "");
    }

    public void setPillDocSpeciality(String value) {
        editor.putString(PillDocSpeciality, value);
        editor.commit();
    }


    public String getActivecardjson() {
        return pref.getString(Activecardjson, "");
    }

    public void setActivecardjson(String value) {
        editor.putString(Activecardjson, value);
        editor.commit();
    }

    public String getonly_child_name() {
        return pref.getString(only_child_name, "N.A");
    }

    public void setonly_child_name(String value) {
        editor.putString(only_child_name, value);
        editor.commit();
    }

    public String getCVRelation() {
        return pref.getString(CVRelation, "N.A");
    }

    public void setCVRelation(String value) {
        editor.putString("CVRelation", value);
        editor.commit();
    }

    public String getCVParentname() {
        return pref.getString(CVParentname, "N.A");
    }

    public void setCVParentname(String value) {
        editor.putString(CVParentname, value);
        editor.commit();
    }

    public void Setmy_pkg_id(String my_pkg_id) {
        editor.putString(this.my_pkg_id, my_pkg_id);
        editor.commit();
    }

    public String Getmy_pkg_id() {
        return pref.getString(my_pkg_id, "");
    }

    //---
    public void setMypillJson(String value) {
        editor.putString(MypillJson, value);
        editor.commit();
    }

    public String getMypillJson() {
        return pref.getString(MypillJson, "");
    }
    //---

    //---
    public String getCVChildName() {
        return pref.getString(CVChildName, "");
    }

    public String getCVChildAge() {
        return pref.getString(CVChildAge, "");
    }

    public String getCVChildGender() {
        return pref.getString(CVChildGender, "");
    }

    public String getCVChildImage() {
        return pref.getString(CVChildImage, "");
    }

    public String getCVNextVaccine() {
        return pref.getString(CVNextVaccine, "");
    }

    public String getCVDueDate() {
        return pref.getString(CVDueDate, "");
    }

    public String getCVMobileno() {
        return pref.getString(CVMobileno, "");
    }

    public String getCVBirthdate() {
        return pref.getString(CVBirthdate, "");
    }

    public void SetCVBirthdate(String value) {
        editor.putString(CVBirthdate, value);
        editor.commit();
    }

    public void SetCVMobileno(String value) {
        editor.putString(CVMobileno, value);
        editor.commit();
    }

    public void SetCVChildName(String value) {
        editor.putString(CVChildName, value);
        editor.commit();
    }

    public void SetCVChildAge(String value) {
        editor.putString(CVChildAge, value);
        editor.commit();
    }

    public void SetCVChildGender(String value) {
        editor.putString(CVChildGender, value);
        editor.commit();
    }

    public void SetCVChildImage(String value) {
        editor.putString(CVChildImage, value);
        editor.commit();
    }

    public void SetCVNextVaccine(String value) {
        editor.putString(CVNextVaccine, value);
        editor.commit();
    }

    public void SetCVDueDate(String value) {
        editor.putString(CVDueDate, value);
        editor.commit();
    }

    //---

    public void ClearPillStuff() {

        setAddedMedicine("");
        setPillDoctor("");
        setPillDose("");
        setMedFriend("");

    }

    //---
    public void setAddedMedicine(String value) {
        editor.putString(AddedMedicine, value);
        editor.commit();
    }

    public String getAddedMedicine() {
        return pref.getString(AddedMedicine, "");
    }
    //---


    //---
    public void setPillDoctor(String value) {
        editor.putString(PillDoctor, value);
        editor.commit();
    }

    public String getPillDoctor() {
        return pref.getString(PillDoctor, "");
    }
    //---


    //---
    public void setPillDose(String value) {
        editor.putString(PillDose, value);
        editor.commit();
    }

    public String getPillDose() {
        return pref.getString(PillDose, "");
    }
    //---


    //---
    public void setMedFriend(String value) {
        editor.putString(MedFriend, value);
        editor.commit();
    }

    public String getMedFriend() {
        return pref.getString(MedFriend, "");
    }
    //---

    //---
    public void setAllMedType(String value) {
        editor.putString(AllMedType, value);
        editor.commit();
    }

    public String getAllMedType() {
        return pref.getString(AllMedType, "[]");
    }
    //---

    public void setOngoingTeleAppoId(String value) {
        editor.putString(ONGOING_APPO_ID, value);
        editor.commit();
    }

    public String getOngoingTeleAppoId() {
        return pref.getString(ONGOING_APPO_ID, "");
    }


    public void setlastskiptime(String longmili) {
        editor.putString("lastskiptime", longmili);
        editor.commit();
    }

    public void setOTPmobileno(String mobileno) {
        editor.putString("OTPmobileno", mobileno);
        editor.commit();
    }

    public String getOTPmobileno() {
        return pref.getString("OTPmobileno", "---");
    }


    public String getlastskiptime() {
        return pref.getString("lastskiptime", "0");
    }


    public void setDashboardNoticount(String count) {
        editor.putString("DashNotiCount", count);
        editor.commit();
    }

    public String getDashboardNoticount() {
        return pref.getString("DashNotiCount", "0");
    }

    public void setHeight(String heightvalue) {
        editor.putString("bmi_height", heightvalue);
        editor.commit();
    }

    public String getheight() {
        return pref.getString("bmi_height", "");
    }


    public void setlastselectedtiles(int tilesno) {
        editor.putInt("last_selected_tiles", tilesno);
        editor.commit();
    }

    public int getlastselectedtiles() {
        return pref.getInt("last_selected_tiles", 0);
    }


    public void setLatestSugarTarget(String LatestSugarTargetvalue) {
        editor.putString("LatestSugarTarget", LatestSugarTargetvalue);
        editor.commit();
    }

    public String getLatestSugarTarget() {
        return pref.getString("LatestSugarTarget", "0");
    }

    public void setLatestBMITarget(String LatestBMITargetvalue) {
        editor.putString("LatestBMITarget", LatestBMITargetvalue);
        editor.commit();
    }

    public String getLatestBMITarget() {
        return pref.getString("LatestBMITarget", "0");
    }


    public void SetStepsOfMobiFit(String steps) {
        editor.putString("Steps", steps);
        editor.commit();
    }

    public String getStepsOfMobiFit() {
        return pref.getString("Steps", "0");
    }

    public void SetCaloriesOfMobiFit(String Calories) {
        editor.putString("Calories", Calories);
        editor.commit();
    }

    public void SetDistanceofMobifit(String Distance) {
        editor.putString("Distance", Distance);
        editor.commit();
    }

    public String getDistanceofMobifit() {
        return pref.getString("Distance", "0");
    }

    public String getCaloriesOfMobiFit() {
        return pref.getString("Calories", "0");
    }

    public String getDeviceId() {
        return pref.getString("DEV_ID", null);
    }

    public String getuserid() {
        return pref.getString(KEY_ID, null);
    }

    public String getuserName() {
        return pref.getString(KEY_NAME, "");
    }

    public String getUserEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public String getuserGender() {
        return pref.getString(KEY_GENDER, "");
    }

    public String getuserImage() {
        return pref.getString(KEY_IMAGE, "");
    }

    public String getmobileNo() {
        return pref.getString(KEY_MOBILE, null);
    }

    public void setmobileno(String mobileno) {
        editor.putString(KEY_MOBILE, mobileno);
        editor.commit();
    }

    public Boolean checkLogin() {
        // Check login status
        // get user data from session
        return this.isLoggedIn();

    }

    public void StoreAddCaretaker(String json) {
        editor.putString("AddCaretaker", json);
        editor.commit();
    }

    public String getAddCaretaker() {
        return pref.getString("AddCaretaker", "");
    }

    public void StoreBeCaretaker(String json) {
        editor.putString("BeCaretaker", json);
        editor.commit();
    }

    public String getBeCaretaker() {
        return pref.getString("BeCaretaker", "");
    }

    public void logoutUser() {
        MobiLogger.printmsg("in logout user of session manager");
        // Clearing all data from Shared Preferences
        String token = getDeviceToken();
        editor.clear();
        editor.commit();
        setDeviceToken(token);
    }


    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setDeviceToken(String value) {
        editor.putString(DEVICE_TOKEN, value);
        editor.commit();
    }


    public String getDeviceToken() {
        return pref.getString(DEVICE_TOKEN, "");
    }

    public String getDeviceLanguage() {
        return pref.getString(DEVICE_LANGUAGE, "en");
    }

    public String getUserBdate(){
        try {
            return pref.getString(KEY_BDate, "");
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
