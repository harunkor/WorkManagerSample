package com.harunkor.workmanagersample

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class LogWorker(appContext: Context,workerParameters: WorkerParameters)
    : Worker(appContext,workerParameters) {
    override fun doWork(): Result {

        Log.v("AKBANK","Wormanager called.")

        return Result.success()
    }

}