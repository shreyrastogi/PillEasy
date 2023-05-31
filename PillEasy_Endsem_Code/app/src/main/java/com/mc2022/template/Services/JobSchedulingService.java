package com.mc2022.template.Services;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.mc2022.template.Database.SQLiteHelper;
import com.mc2022.template.Utilities.Utilities;

public class JobSchedulingService extends JobService {
    private static final String TAG = "ExampleJobService";
    SQLiteHelper helper;
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        helper = new SQLiteHelper(this);
        setAlarms(params);
        return true;
    }

    @SuppressLint("LongLogTag")
    private void setAlarms(JobParameters params) {
        Utilities.setAlarms(this, 0);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }
}