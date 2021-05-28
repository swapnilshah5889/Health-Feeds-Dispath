package com.fluvina.hummnew.Utilities;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

public class MobiLogger {

    public static void printmsg(String msg) {
        Log.e("TAG", msg);
    }

    public static void printMap(HashMap<String, String> map) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("API PARAMS");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.append("\n").append(entry.getKey()).append(":").append(entry.getValue());
            }
            Log.e("TAG", builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printPartMap(HashMap<String, RequestBody> map) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("API PARAMS");
            for (Map.Entry<String, RequestBody> entry : map.entrySet()) {
                builder.append("\n").append(entry.getKey()).append(":").append(entry.getValue().toString());
            }
            Log.e("TAG", builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
