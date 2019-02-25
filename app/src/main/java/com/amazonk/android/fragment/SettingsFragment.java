package com.amazonk.android.fragment;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;

import com.amazonk.android.DeviceAdminLock;
import com.amazonk.android.R;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends PreferenceFragmentCompat {
    private static final String TAG = PreferenceFragmentCompat.class.getSimpleName();
    private static final int REQUEST_CODE_ENABLE_ADMIN = 1;
    private ComponentName deviceAdminLock;
    private DevicePolicyManager dpm;
    private SwitchPreferenceCompat lockSwitch;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings_pref);

        deviceAdminLock = new ComponentName(getContext(), DeviceAdminLock.class);
        dpm = (DevicePolicyManager) getContext().getSystemService(Context.DEVICE_POLICY_SERVICE);

        lockSwitch = (SwitchPreferenceCompat) findPreference(getString(R.string.s_lock));
        lockSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if ((Boolean) o) {
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdminLock);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            "Additional text explaining why we need this permission");
                    startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
                } else {
                    if (dpm.isAdminActive(deviceAdminLock))
                        dpm.removeActiveAdmin(deviceAdminLock);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ENABLE_ADMIN) {
            if (resultCode == RESULT_OK) {
                lockSwitch.setChecked(true);
            }
        }
    }
}
