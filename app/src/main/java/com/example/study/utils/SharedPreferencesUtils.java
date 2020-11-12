package com.example.study.utils;

import android.content.Context;

import com.example.study.R;

public class SharedPreferencesUtils {

    private static final String PREF_APP = "app_pref";
    private static final int MODE = Context.MODE_PRIVATE;


    private SharedPreferencesUtils() {
        throw new UnsupportedOperationException("기본 생정자를 통한 객체생성이 불가능합니다.");
    }

    public static String getStringData(Context context, String key) {
        return context.getSharedPreferences(PREF_APP, MODE).getString(key, null);
    }

    public static int getIntData(Context context, String key) {
        return context.getSharedPreferences(PREF_APP, MODE).getInt(key , 0);
    }

    public static void saveData(Context context, String key , String value) {
        context.getSharedPreferences(PREF_APP, MODE).edit().putString(key , value).apply();
    }

    public static void saveData(Context context, String key , int value) {
        context.getSharedPreferences(PREF_APP, MODE).edit().putInt(key , value).apply();
    }
}
