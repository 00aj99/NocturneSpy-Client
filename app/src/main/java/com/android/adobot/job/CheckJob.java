package com.android.adobot.job;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.android.adobot.CommandService;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

public class CheckJob extends Job {

	public static final String TAG = "job_check_tag";

	@Override
	@NonNull
	protected Result onRunJob(Params params) {
		ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null) {
			if (info.isConnected()) {
				Intent i = new Intent(getContext(), CommandService.class);
				getContext().startService(i);
				return Result.SUCCESS;
			} else return Result.RESCHEDULE;
		} else return Result.RESCHEDULE;
	}

	public static void scheduleJob() {
		new JobRequest.Builder(CheckJob.TAG)
				.setPeriodic(JobRequest.MIN_INTERVAL, JobRequest.MIN_FLEX)
				.build()
				.schedule();
	}
}