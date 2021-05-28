package com.fluvina.hummnew.Utilities;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.fluvina.hummnew.Model.HummFeedModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MOBIHEALTHUSER";
    //    public static final int DB_VERSION = 2;  //->10/01/2020
//    public static final int DB_VERSION = 3;  //->21/05/2020
    public static final int DB_VERSION = 4;  //->18/02/2021

    public final String APPOINTMENTS_TABLE = "APPOINTMENTS";
    public final String MY_DOCTORS_TABLE = "MY_DOCTORS";
    public final String MYPROFILE_TABLE = "MYPROFILE";
    public final String MyCareTakersTable = "MyCareTakers";
    public final String BeCareTakersTable = "BeCareTakers";
    public final String NChatDialogTable = "NChatDialogTable";
    public final String NChatDetailTable = "NChatDetailTable";
    public final String EmojiTable = "EmojiTable";

    public final String PRISCRIPTIONS_TABLE = "PRISCRIPTIONS";
    public final String COMPLETED_APPOINTMENTS_TABLE = "COMPLETED_APPOINTMENTS";
    public final String MY_PACKAGE_TABLE = "MY_PACKAGE";
    public final String GIFT_PACKAGE_TABLE = "GIFT_PACKAGE";
    public final String BUY_PACKAGE_TABLE = "BUY_PACKAGE";
    public final String WALLET_RECORD_TABLE = "WALLET_RECORD";
    public final String CREDIT_USAGE_TABLE = "CREDIT_USAGE";
    public final String BMI_TABLE = "BMI";
    public final String CHOLESTROL_TABLE = "CHOLESTROL";
    public final String BLOOD_PRESSURE_TABLE = "BLOOD_PRESSURE";
    public final String SUGAR_LEVEL_TABLE = "SUGAR_LEVEL";
    public final String HEART_BEAT_TABLE = "HEART_BEAT";
    public final String HAEMOGLOBIN_TABLE = "HAEMOGLOBIN";
    public final String PTInrTable = "PTINR";
    public final String HbA1CTable = "HbA1C";
    public final String LDLTable = "LDL";
    public final String ChatUsers_table = "ChatUsers";
    public final String BlogsTable = "Blogs";
    public final String offlline_chat_messages_table = "offlline_chat_messages";
    public final String ChatDialogsTable = "ChatDialogs";
    public final String labReportsTable = "labReports";
    public final String Non_HDL = "Non_HDL";
//    public final String TBTable = "TBTable";
//    public final String BALANCE_TABLE = "BALANCE";
//    public final String medi_conf_tags_table = "medi_conf_tags";
//    public final String TargetsTable = "Targets";
//    public final String InstrcutionsDashboardTable = "InstrcutionsDashboard";
//    public final String DietDataTable = "DietData";
//    public final String DashBoardDataTable = "DashBoardData";
//    public final String VaccinationMyChildTable = "VaccinationMyChild";
//    public final String AllVaccinesTable = "AllVaccines";
//    public final String searchdumbTable = "searchdumb";
//    public final String RelationTable = "RelationTable";
//    public final String EventListTable = "EventListTable";
    private final String HUMMFEEDS_TABLE = "HUMMFEEDS_READ";
//    public static final String NotificationTable = "NotificationTable";
    public static final String AppoSlotsTable = "AppoSlotsTable";

    public final String create_appointments_table = "create table " +
            APPOINTMENTS_TABLE + " ( apt_id ,dr_id,dr_name,apt_on,clinic_name,reason,review,rating,description,appo_on_mili INTEGER,clinic_address ,clinic_Lang,clinic_Lat,clinic_cno ,check_in_flag );";
    public final String create_MyCareTakersTable = " create table " + MyCareTakersTable +
            " ( name , phonenumber , relation , status ,shared , time_of_request,requestId ,profileurl,sorting INTEGER)";
    public final String create_BeCareTakersTable = " create table " + BeCareTakersTable +
            " ( name , phonenumber , relation , status ,shared , time_of_request,requestId,profileurl,sorting INTEGER)";
    public final String create_mydoctors_table = " create table " + MY_DOCTORS_TABLE +
            " ( dr_id,dr_nm,number,dr_json,appcount,packcount,presccount,reportscount );";

    public final String create_MYPROFILE_TABLE = " create table " + MYPROFILE_TABLE +
            " ( firstname ,lastname,contact_num,email,user_img,gender,address1,address2,city,dob,blood_group,pincode,country,state);";
    public final String createChatGroupDialogTable = " CREATE TABLE IF NOT EXISTS " + NChatDialogTable + " ( json )";
    public final String createNChatDetailTable = " CREATE TABLE IF NOT EXISTS " + NChatDetailTable + " ( patient_id ,dr_id,json);";
    public final String createEmojiTable = " create table if not exists " + EmojiTable + " ( json )";

    private final String create_HUMMFEEDS_TABLE = "create table if not exists " + HUMMFEEDS_TABLE +
            "( feed_id ,feed_type);";
    String CreateAppoSlotsTable = " create table " + AppoSlotsTable + " ( datajson ) ";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) throws SQLException {
        try {
            db.execSQL(create_appointments_table);
            db.execSQL(create_mydoctors_table);
            db.execSQL(create_MYPROFILE_TABLE);
            db.execSQL(create_MyCareTakersTable);
            db.execSQL(create_BeCareTakersTable);
            db.execSQL(createChatGroupDialogTable);
            db.execSQL(createNChatDetailTable);
            db.execSQL(createEmojiTable);
            db.execSQL(create_HUMMFEEDS_TABLE);
            db.execSQL(CreateAppoSlotsTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        UpgradeTables(db, oldVersion);
        MobiLogger.printmsg("onUpgrade called");


    }


    public void UpgradeTables(SQLiteDatabase db, int oldVersion) {
        MobiLogger.printmsg("vesrion no : " + oldVersion);
        try {
            // Remove Old Tables from databse
            db.execSQL("DROP TABLE IF EXISTS " + PRISCRIPTIONS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + COMPLETED_APPOINTMENTS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + labReportsTable);
            db.execSQL("DROP TABLE IF EXISTS " + BlogsTable); // no need to create
            db.execSQL("DROP TABLE IF EXISTS " + ChatDialogsTable); // no need to create
            db.execSQL("DROP TABLE IF EXISTS " + ChatUsers_table); // no need to create
            db.execSQL("DROP TABLE IF EXISTS " + offlline_chat_messages_table); // no need to create
            db.execSQL("DROP TABLE IF EXISTS " + BMI_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + CHOLESTROL_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + BLOOD_PRESSURE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + SUGAR_LEVEL_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + HEART_BEAT_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + HAEMOGLOBIN_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + HbA1CTable);
            db.execSQL("DROP TABLE IF EXISTS " + PTInrTable);
            db.execSQL("DROP TABLE IF EXISTS " + LDLTable);
            db.execSQL("DROP TABLE IF EXISTS " + Non_HDL);
            db.execSQL("DROP TABLE IF EXISTS " + MY_PACKAGE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + GIFT_PACKAGE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + BUY_PACKAGE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + WALLET_RECORD_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + CREDIT_USAGE_TABLE);

            switch (oldVersion) {
                case 1:
                case 2:
                case 3:
                    db.execSQL(create_HUMMFEEDS_TABLE);
                    db.execSQL(CreateAppoSlotsTable);
                    break;
            }

            //creating table
//        db.execSQL(create_appointments_table);
//        db.execSQL(create_mydoctors_table);
//        db.execSQL(create_MyCareTakersTable);
//        db.execSQL(create_BeCareTakersTable);
//        db.execSQL(createChatGroupDialogTable);
//        db.execSQL(createNChatDetailTable);
//        db.execSQL(createEmojiTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void delete_all_record() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + APPOINTMENTS_TABLE);
        db.execSQL("delete from " + MY_DOCTORS_TABLE);
        db.execSQL("delete from " + MYPROFILE_TABLE);
        db.execSQL("delete from " + MyCareTakersTable);
        db.execSQL("delete from " + BeCareTakersTable);
        db.execSQL("delete from " + NChatDialogTable);
        db.execSQL("delete from " + NChatDetailTable);
        db.execSQL("delete from " + EmojiTable);
        db.execSQL("delete from " + HUMMFEEDS_TABLE);
        db.execSQL("delete from " + AppoSlotsTable);
        MobiLogger.printmsg("ALL RECORDS DELETED");
    }

    //nothing to do.. only for tables creation
    public void initiate() {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String val = db.getPath();
            MobiLogger.printmsg("Database path :" + val);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void insert_into_humm_feeds(String id, String type) {
        try {
            SQLiteDatabase db1 = this.getReadableDatabase();
            String query = "select * from " + HUMMFEEDS_TABLE + " where feed_id='" + id + "'";
            MobiLogger.printmsg(" get_all_HUMM_FEED query : " + query);
            Cursor cursor = db1.rawQuery(query, null);

            int rows = cursor.getCount();
            if (rows <= 0) {
                MobiLogger.printmsg("insert_into_humm_feeds In");
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("feed_id", id);
                values.put("feed_type", type);

                db.insert(HUMMFEEDS_TABLE, null, values);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllHummFeeds() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String query = " delete from " + HUMMFEEDS_TABLE;
            MobiLogger.printmsg("deleteAllHummFeeds query :" + query);
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<HummFeedModel> get_all_HUMM_FEED() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "select feed_id,feed_type from " + HUMMFEEDS_TABLE;
            MobiLogger.printmsg(" get_all_HUMM_FEED query : " + query);
            Cursor cursor = db.rawQuery(query, null);
            List<HummFeedModel> hummFeedList = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    String id = cursor.getString(0);
                    String type = cursor.getString(1);
                    if (ids.size() > 0) {
                        if (!ids.contains(id)) {
                            hummFeedList.add(new HummFeedModel(id, type));
                            ids.add(id);
                        }
                    } else {
                        hummFeedList.add(new HummFeedModel(id, type));
                        ids.add(id);
                    }
                    cursor.moveToNext();
                }
            }
            return hummFeedList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void InsertIntoEmojiTable(String json) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("json", json);

            db.insert(EmojiTable, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor getEmojis() {
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + EmojiTable;
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor;
    }

    public void updateEmojis(String json) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "update " + EmojiTable + " set json='" + json + "';";
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InsertIntoChatDetail(String patient_id, String dr_id, String json) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("patient_id", patient_id);
            values.put("dr_id", dr_id);
            values.put("json", json);

            db.insert(NChatDetailTable, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Cursor getAllChat(String patient_id, String dr_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "select * from " + NChatDetailTable + " where patient_id ='" + patient_id + "' AND dr_id='" + dr_id + "';";
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;

    }

    public void deleteChat(String patient_id, String dr_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from " + NChatDetailTable + " where patient_id ='" + patient_id + "' AND dr_id='" + dr_id + "';";
         MobiLogger.printmsg("query : " + query);
        db.execSQL(query);
    }

    public void InsertIntoChatDialogTable(String json) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("json", json);
            db.insert(NChatDialogTable, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor getChatDialogs() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String query = "select * from " + NChatDialogTable;
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor;
    }

    public void deleteChatDialogs() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from " + NChatDialogTable;
        db.execSQL(query);
    }

//    public void InsertIntoRelationTable(String datajson) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("datajson", datajson);
//
//        try {
//            db.insert(RelationTable, null, values);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void DeleteRelationTable() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = " delete from " + RelationTable;
//        db.execSQL(query);
//    }


    public void DeleteAllMyCaretaker() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " delete from " + MyCareTakersTable;
        db.execSQL(query);


    }

    public void DeleteAllBeCaretaker() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " delete from " + BeCareTakersTable;
        db.execSQL(query);


    }


    public void updateStatusOfMyCaretaker(String id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "update " + MyCareTakersTable + " set status ='" + status + "' where requestId ='" + id + "';";
         MobiLogger.printmsg("updateStatusOfMyCaretaker query : " + query);
        db.execSQL(query);

    }

    public void deleteMyCaretaker(String id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "delete from " + MyCareTakersTable + " where requestId ='" + id + "';";
             MobiLogger.printmsg("deleteMyCaretaker query : " + query);
            db.execSQL(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBeCaretaker(String id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "delete from " + BeCareTakersTable + " where requestId ='" + id + "';";
             MobiLogger.printmsg("deleteBeCaretaker query : " + query);
            db.execSQL(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStatusOfBeCaretaker(String id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "update " + BeCareTakersTable + " set status ='" + status + "' where requestId ='" + id + "';";
         MobiLogger.printmsg("updateStatusOfMyCaretaker query : " + query);
        db.execSQL(query);

    }

    public boolean ismycaretakerAvailable(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select * from " + MyCareTakersTable + " where requestId ='" + id + "';";
         MobiLogger.printmsg("ismycaretakerAvailable query : " + query);
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        boolean b = c.getCount() > 0;
        c.close();
        return b;
    }

    public boolean isbecaretakerAvailable(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select * from " + BeCareTakersTable + " where requestId ='" + id + "';";
         MobiLogger.printmsg("isbecaretakerAvailable query : " + query);
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        boolean b = c.getCount() > 0;
        c.close();
        return b;
    }


    public void insertIntoMyCaretakers(String name, String phonenumber, String relation, String status,
                                       String shared, String time_of_request, String requestId, String profileurl) {
         MobiLogger.printmsg("insertIntoMyCaretakers");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phonenumber", phonenumber);
        values.put("relation", relation);
        values.put("status", status);
        values.put("shared", shared);
        values.put("time_of_request", time_of_request);
        values.put("requestId", requestId);
        values.put("profileurl", profileurl);

        //0 Remove Button
        //1 Request Sent
        //2 Rejected
        //3 Removed

        String sortingval = "";
        if (status.equalsIgnoreCase("Remove Button")) {
            sortingval = "0";
        } else if (status.equalsIgnoreCase("Request Sent")) {
            sortingval = "1";
        } else if (status.equalsIgnoreCase("Rejected")) {
            sortingval = "2";
        } else if (status.equalsIgnoreCase("Removed")) {
            sortingval = "3";
        }

        values.put("sorting", sortingval);


        try {
            db.insert(MyCareTakersTable, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void insertIntoBeCaretakers(String name, String phonenumber, String relation, String status,
                                       String shared, String time_of_request, String requestId, String profileurl) {
         MobiLogger.printmsg("insertIntoMyCaretakers");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phonenumber", phonenumber);
        values.put("relation", relation);
        values.put("status", status);
        values.put("shared", shared);
        values.put("time_of_request", time_of_request);
        values.put("requestId", requestId);
        values.put("profileurl", profileurl);
        values.put("profileurl", profileurl);

        //0 Remove Button
        //1 Request Sent
        //2 Rejected
        //3 Removed

        String sortingval = "";
        if (status.equalsIgnoreCase("Remove Button")) {
            sortingval = "0";
        } else if (status.equalsIgnoreCase("Request Sent")) {
            sortingval = "1";
        } else if (status.equalsIgnoreCase("Rejected")) {
            sortingval = "2";
        } else if (status.equalsIgnoreCase("Removed")) {
            sortingval = "3";
        }

        values.put("sorting", sortingval);


        try {
            db.insert(BeCareTakersTable, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Cursor getMyCaretakerFromMobileno(String Mobileno) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select * from " + MyCareTakersTable + " where phonenumber ='" + Mobileno + "';";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c;
    }

    public Cursor getBeCaretakerFromMobileno(String Mobileno) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select * from " + BeCareTakersTable + " where phonenumber ='" + Mobileno + "';";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c;
    }


    public Cursor getALLMYCaretaker() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select * from " + MyCareTakersTable + " order by sorting ";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c;
    }

    public Cursor getALLBeCaretaker() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select * from " + BeCareTakersTable + " order by sorting ";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c;
    }

    public void insert_into_appointments_table(String apt_id, String dr_id, String dr_name, String apt_on, String clinic_name,
                                               String reason, String review, String rating, String description, String clinic_address, String clinic_Lang,
                                               String clinic_Lat, String clinic_cno, String check_in_flag) {
        try {
             MobiLogger.printmsg("insert_into_active_appointments_table In");
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("apt_id", apt_id);
            values.put("dr_id", dr_id);
            values.put("dr_name", dr_name);
            values.put("apt_on", apt_on);
            values.put("clinic_name", clinic_name);
            values.put("reason", reason);
            values.put("review", review);
            values.put("rating", rating);
            values.put("description", description);

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                Date dt = sdf.parse(apt_on);
                values.put("appo_on_mili", dt.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }

            values.put("clinic_address", clinic_address);
            values.put("clinic_Lang", clinic_Lang);
            values.put("clinic_Lat", clinic_Lat);
            values.put("clinic_cno", clinic_cno);
            values.put("check_in_flag", check_in_flag);

            db.insert(APPOINTMENTS_TABLE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateActiveAppointment(String apt_id, String dr_id, String dr_name, String apt_on, String clinic_name,
                                        String reason, String review, String rating, String description, String clinic_address, String clinic_Lang,
                                        String clinic_Lat, String clinic_cno, String check_in_flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "update " + APPOINTMENTS_TABLE + " set dr_id='" + dr_id + "',dr_name='" + dr_name + "',apt_on='" + apt_on + "',clinic_name='" + clinic_name + "',reason='" + reason + "',review='" + review + "',rating='" + rating
                + "',description='" + description + "',clinic_address='" + clinic_address + "',clinic_Lang='" + clinic_Lang + "',clinic_Lat='" + clinic_Lat + "',clinic_cno='" + clinic_cno + "',check_in_flag='" + check_in_flag + "' where apt_id='" + apt_id + "'";
        db.execSQL(query);
    }

    public void updateReschedule(String apt_id, String clinic_name, String apt_on, long appo_on_mili) {
         MobiLogger.printmsg("updateReschedule In");
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String query = " update " + APPOINTMENTS_TABLE + " set  clinic_name ='" + clinic_name + "',apt_on ='" + apt_on + "',appo_on_mili =" + appo_on_mili + " where apt_id ='" + apt_id + "'";
             MobiLogger.printmsg("updateReschedule query : " + query);
            db.execSQL(query);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateReviews(String apt_id, String review, String ratting) {
         MobiLogger.printmsg("updateReviews In");
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String query = " update " + APPOINTMENTS_TABLE + " set  review ='" + review + "',rating ='" + ratting + "' where apt_id ='" + apt_id + "'";
             MobiLogger.printmsg("updateReviews query : " + query);
            db.execSQL(query);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean is_appointment_in_active(String apt_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + APPOINTMENTS_TABLE + " where apt_id ='" + apt_id + "'";
         MobiLogger.printmsg(" is_appointment_in_active query : " + query);
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        boolean b = c.getCount() > 0;
        c.close();
        return b;
    }

    public void delete_appointment(String apt_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " delete from " + APPOINTMENTS_TABLE + " where apt_id ='" + apt_id + "'";
         MobiLogger.printmsg("delete_appointment_from_active qyery :" + query);
        db.execSQL(query);

    }


    public void deleteAllAppointment() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " delete from " + APPOINTMENTS_TABLE;
         MobiLogger.printmsg("deleteAllAppointment qyery :" + query);
        db.execSQL(query);

    }

    public void insert_into_mydoctors_table(String dr_id, String dr_nm, String number,
                                            String dr_json, String appcount, String packcount, String presccount, String reportscount) {
         MobiLogger.printmsg("insert_into_mydoctors_table In");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("dr_id", dr_id);
        values.put("dr_nm", dr_nm);
        values.put("number", number);
        values.put("dr_json", dr_json);
        values.put("appcount", appcount);
        values.put("packcount", packcount);
        values.put("presccount", presccount);
        values.put("reportscount", reportscount);

        try {
            db.insert(MY_DOCTORS_TABLE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void insert_into_my_profile_table(String firstname, String lastname, String contact_num, String email, String user_img, String gender, String address1, String address2,
                                             String city, String dob, String blood_group, String pincode, String country,
                                             String state) {
         MobiLogger.printmsg("insert_into_user_profile_table In");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("firstname", firstname);
        values.put("lastname", lastname);
        values.put("contact_num", contact_num);
        values.put("email", email);
        values.put("user_img", user_img);
        values.put("gender", gender);
        values.put("address1", address1);
        values.put("address2", address2);
        values.put("city", city);
        values.put("dob", dob);
        values.put("blood_group", blood_group);
        values.put("pincode", pincode);
        values.put("country", country);
        values.put("state", state);

        try {
            db.insert(MYPROFILE_TABLE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateUserProfile(LinkedHashMap hasmap, String mobile) {
        try {
            StringBuilder setValues = new StringBuilder();
            for (Object o : hasmap.entrySet()) {
                Map.Entry pair = (Map.Entry) o;
                //System.out.println(pair.getKey() + " = " + pair.getValue());
                setValues.append(pair.getKey()).append("=").append("'").append(pair.getValue()).append("'").append(",");

                //it.remove(); // avoids a ConcurrentModificationException
            }

            String values = "";
            if (!setValues.toString().isEmpty()) {
                values = setValues.substring(0, setValues.lastIndexOf(","));
            }
            SQLiteDatabase db = this.getWritableDatabase();
            String query = " update " + MYPROFILE_TABLE + " set " + values + " where contact_num='" + mobile + "'";
        /*String query = " update " + MYPROFILE_TABLE + " set firstname ='" + firstname + "',email='" + email +
                "',gender='" + gender + "',address1='" + address1 + "',city='" + city + "',blood_group='" + blood_group
                + "',pincode='" + pincode + "',state ='" + state + "' where contact_num='" + mobile + "'";*/
             MobiLogger.printmsg("UpdateProfile query : " + query);
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateProfilepic(String profilepicpath) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String query = " update " + MYPROFILE_TABLE + " set user_img='" + profilepicpath + "'";
             MobiLogger.printmsg("UpdateProfile query : " + query);
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateProfile(String firstname, String lastname, String contact_num, String email, String user_img,
                              String gender, String address1, String address2,
                              String city, String blood_group, String pincode, String country,
                              String state) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String query = " update " + MYPROFILE_TABLE + " set firstname ='" + firstname + "',lastname ='" + lastname + "',email='" + email + "',user_img ='" + user_img +
                    "',gender='" + gender + "',address1='" + address1 + "',address2='" + address2 + "',city='" + city + "',blood_group='" + blood_group
                    + "',pincode='" + pincode + "',country='" + country + "',state ='" + state + "' where contact_num='" + contact_num + "'";
             MobiLogger.printmsg("UpdateProfile query : " + query);
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void delete_my_profile_data() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "delete from " + MYPROFILE_TABLE;
             MobiLogger.printmsg("delete_my_profile_data query : " + query);
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor getDoctorfromId(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + MY_DOCTORS_TABLE + " where dr_id = '" + id + "'";
         MobiLogger.printmsg("getDoctorfromId query : " + query);
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public Cursor get_my_profile_records() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + MYPROFILE_TABLE;
         MobiLogger.printmsg("get_my_profile_records query : " + query);
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public boolean is_doctor_profile_avilable(String dr_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + MY_DOCTORS_TABLE + " where dr_id ='" + dr_id + "'";
         MobiLogger.printmsg("is_doctor_profile_avilable query : " + query);
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        boolean b = c.getCount() > 0;
        c.close();
        return b;
    }

    public Cursor get_doctor_profile_records(String dr_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + MY_DOCTORS_TABLE + " where dr_id ='" + dr_id + "'";
         MobiLogger.printmsg("get_doctor_profile_records query : " + query);
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public void update_into_mydoctors_table(String dr_id, String dr_nm, String number, String dr_json, String appcount, String packcount, String presccount, String reportscount) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "update " + MY_DOCTORS_TABLE + " set dr_nm ='" + dr_nm + "',number ='" + number + "',dr_json ='" + dr_json + "',appcount ='" + appcount + "',packcount ='" + packcount + "',presccount ='" + presccount + "',reportscount='" + reportscount + "' where  dr_id ='" + dr_id + "';";
             MobiLogger.printmsg("update_into_mydoctors_table query : " + query);
            db.execSQL(query);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public boolean is_mydocor_avilable(String dr_id) {
        boolean status = false;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = " select * from " + MY_DOCTORS_TABLE + " where dr_id ='" + dr_id + "';";
             MobiLogger.printmsg("is_mydocor_avilable query : " + query);
            Cursor c = db.rawQuery(query, null);
            status = c.getCount() > 0;
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public Cursor getappointmentDetailfromAppid(String appid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select * from " + APPOINTMENTS_TABLE + " where apt_id ='" + appid + "';";
         MobiLogger.printmsg("getappointmentDetailfromAppid query : " + query);
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public String getDoc_idFromApp_id(String appid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select dr_id from " + APPOINTMENTS_TABLE + " where apt_id ='" + appid + "';";
         MobiLogger.printmsg("getDoc_idFromApp_id query : " + query);
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String str;
        if (c.getCount() > 0) {
            str = c.getString(0);
        } else {
            str = "---";
        }
        c.close();
        return str;
    }


    public void InsertIntoAppoSlotsTable(String datajson) {
        try {
             MobiLogger.printmsg("InsertIntoAppoSlotsTable");
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("datajson", datajson);
            db.insert(AppoSlotsTable, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor GetAppoSlotsTable() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = " select * from " + AppoSlotsTable;
            return db.rawQuery(query, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void DeleteAppoSlotsTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " delete from " + AppoSlotsTable;
        db.execSQL(query);
    }

}




	


