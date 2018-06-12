package com.visenze.usertrackingsdk;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by visenze on 26/2/16.
 */
public class UIDManager {
    private final static String PREF_KEY = "user_tracking_sdk";
    private final static String PREFS_NAME = "user_tracking_sdk_prefs";
    private static Context context;

    private static SharedPreferences preference;

    public static String getUid() {
        return preference.getString(PREF_KEY, null);
    }

    static void initUIDManager(Context context) {
        UIDManager.context = context;
        preference =  context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public static void updateUidFromCookie(String uid) {
        if (preference.getString(PREF_KEY, null) == null) {
            final SharedPreferences.Editor e = preference.edit();
            e.putString(PREF_KEY, uid);
            e.apply();
        }
    }
}

