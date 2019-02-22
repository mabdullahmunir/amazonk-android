package com.amazonk.android;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = PreferenceFragmentCompat.class.getSimpleName();
    private static final int REQUEST_CODE_ENABLE_ADMIN = 1;
    private ComponentName deviceAdminSample;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings_pref);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        deviceAdminSample = new ComponentName(getContext(), DeviceAdminLock.class);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.w(TAG, key);
        Boolean z = sharedPreferences.getBoolean(key, false);
        Log.w(TAG, String.valueOf(z));
        if (z) {
            Log.w(TAG, "here");
            // Launch the activity to have the user enable our admin.
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdminSample);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "Additional text explaining why we need this permission");
            startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
        }
    }

    public void onDestroy() {
        android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }
}
