package com.ccelllnmiit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class User {
    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "USER";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_NAME = "name";
    public static final String KEY_EML = "email";
    public static final String KEY_IMG = "img";
    public static final String KEY_LIS = "lis";
    public static final String KEY_BUL = "bul";
    public static final String KEY_SES = "ses";
    public static final String KEY_RAT = "rat";

    // Constructor
    public User(Context context) {
        this._context = context;
        Log.d("newtag","called");
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String name, String email, String img) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putInt(KEY_SES,1);
        editor.putFloat(KEY_RAT,-1.0F);

        // Storing name in pref
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EML, email);
        editor.putString(KEY_IMG, img);

        editor.commit();
        update_prof(new ArrayList<String>());
        update_bul(new ArrayList<String>());
    }



    public void update_prof(ArrayList<String> lis) {

        Gson gson = new Gson();
        String json = gson.toJson(lis);
        editor.putString(KEY_LIS, json);
        editor.apply();
    }

    public void update_bul(ArrayList<String> lis) {

        Gson gson = new Gson();
        String json = gson.toJson(lis);
        editor.putString(KEY_BUL, json);
        editor.apply();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EML, pref.getString(KEY_EML, null));
        user.put(KEY_IMG, pref.getString(KEY_IMG, null));

        return user;
    }

    public ArrayList<String> getDetSet(){
        Gson gson = new Gson();
        String json = pref.getString(KEY_LIS, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public ArrayList<String> getBulSet(){
        Gson gson = new Gson();
        String json = pref.getString(KEY_BUL, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public int getSes() {
        return pref.getInt(KEY_SES, 1);
    }

    public void incSes() {
        int x=pref.getInt(KEY_SES, 1);
        editor.putInt(KEY_SES,x+1);
        editor.apply();
    }

    public float getRat() {
        return pref.getFloat(KEY_RAT, -1.0F);
    }

    public void setRat(float x) {
        editor.putFloat(KEY_RAT,x);
        editor.apply();
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, login_activity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }
}
