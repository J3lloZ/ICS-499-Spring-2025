package com.example.ics449app;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DeviceAdminHelper {

    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mAdminComponent;

    public DeviceAdminHelper(Context context) {
        mDevicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        mAdminComponent = new ComponentName(context, MyDeviceAdminReceiver.class);
    }

    // Lock the device if device admin is active
    public void lockDevice(Context context) {
        if (mDevicePolicyManager.isAdminActive(mAdminComponent)) {
            mDevicePolicyManager.lockNow();
            Toast.makeText(context, "Device Locked", Toast.LENGTH_SHORT).show();
        } else {
            // If Device Admin is not active, prompt user to enable it
            promptToEnableDeviceAdmin(context);
        }
    }

    // Unlock the device
    public void unlockDevice(Context context) {
        if (mDevicePolicyManager.isAdminActive(mAdminComponent)) {
            mDevicePolicyManager.lockNow();  // Locking the device (there's no unlock equivalent in DevicePolicyManager)
            Toast.makeText(context, "Device Unlocked", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Device Admin is not active", Toast.LENGTH_SHORT).show();
        }
    }
    public void startLockTask(Context context) {
        if (mDevicePolicyManager.isAdminActive(mAdminComponent)) {
            // Start locking the task (pinning the app to the foreground)
            ((Activity) context).startLockTask();  // This will lock the task to the current app
            Toast.makeText(context, "App is now locked", Toast.LENGTH_SHORT).show();
        } else {
            promptToEnableDeviceAdmin(context);
        }
    }

    // Stop Lock Task Mode
    public void stopLockTask(Context context) {
        if (mDevicePolicyManager.isAdminActive(mAdminComponent)) {
            // Stop locking the task (unpinning the app)
            ((Activity) context).stopLockTask();
            Toast.makeText(context, "App is no longer locked", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Device Admin is not active", Toast.LENGTH_SHORT).show();
        }
    }
    // Prompt user to enable Device Admin
    public void promptToEnableDeviceAdmin(Context context) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminComponent);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Enable Device Admin to lock the device.");
        context.startActivity(intent);
    }

}