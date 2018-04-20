package com.android.adobot.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.adobot.BuildConfig;
import com.android.adobot.Constants;
import com.android.adobot.R;

public class SetupActivity extends BaseActivity {

    private static final String TAG = "SetupActivity";

    SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        prefs = this.getSharedPreferences("com.android.adobot", Context.MODE_PRIVATE);
        setServerUrl(Constants.DEVELOPMENT_SERVER);
    }

    private void setServerUrl(String url) {
        prefs.edit().putString("serverUrl", url).commit();
        if (!hasPermissions()) {
            requestPermissions();
        } else {
            done();
        }
    }

    private void done() {
        startClient();
        hideApp();
        finish();
    }
}
