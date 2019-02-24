package com.amazonk.android;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class DeviceAdminLock extends DeviceAdminReceiver {
    void showToast(Context context, String msg) {
//        String status = context.getString(R.string.admin_receiver_status, msg);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "on");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "off");
    }
}
