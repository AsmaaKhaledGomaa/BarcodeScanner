package com.asmaa.barcodescanner.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PermissionManager {

    private static final String PREFERENCES_FILE = "app_preferences";
    private final SharedPreferences denyPreferences;

    public PermissionManager(Context context) {
        denyPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    public int getCameraPermissionDenialCount() {
        return denyPreferences.getInt(Constants.PERMISSION_DENIED, 0);
    }

    public void incrementCameraPermissionDenialCount() {
        int count = getCameraPermissionDenialCount();
        denyPreferences.edit().putInt(Constants.PERMISSION_DENIED, count + 1).apply();
    }

    public void resetCameraPermissionDenialCount() {
        denyPreferences.edit().putInt(Constants.PERMISSION_DENIED, 0).apply();
    }
}
