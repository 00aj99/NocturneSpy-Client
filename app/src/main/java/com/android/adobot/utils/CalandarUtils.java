package com.android.adobot.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalandarUtils {

	public ArrayList<String> nameOfEvent  = new ArrayList<>();
	public ArrayList<String> startDates   = new ArrayList<>();
	public ArrayList<String> endDates     = new ArrayList<>();
	public ArrayList<String> descriptions = new ArrayList<>();

	public ArrayList<String> readCalendarEvent(Context context) {
		Cursor cursor = context.getContentResolver().query(Uri.parse("content://com.android.calendar/events"), new String[] { "calendar_id", "title", "description", "dtstart", "dtend", "eventLocation" }, null, null, null);
		cursor.moveToFirst();

		String CNames[] = new String[cursor.getCount()];

		nameOfEvent.clear();
		startDates.clear();
		endDates.clear();
		descriptions.clear();
		for (int i = 0; i < CNames.length; i++) {
			nameOfEvent.add(cursor.getString(1));
			startDates.add(getDate(Long.parseLong(cursor.getString(3))));
			endDates.add(getDate(Long.parseLong(cursor.getString(4))));
			descriptions.add(cursor.getString(2));
			CNames[i] = cursor.getString(1);
			cursor.moveToNext();
		}

		return nameOfEvent;
	}

	private String getDate(long milliSeconds) {
		@SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}
}
