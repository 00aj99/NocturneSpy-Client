package com.android.adobot.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class CheckJobCreator implements JobCreator {

	@Override
	@Nullable
	public Job create(@NonNull String tag) {
		switch (tag) {
			case CheckJob.TAG:
				return new CheckJob();
			default:
				return null;
		}
	}
}
