package com.harunkor.workmanagersample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

class ForegroundWorker(appContext: Context,parameters: WorkerParameters)
    : CoroutineWorker(appContext,parameters) {

    companion object {
        const val TAG ="AKBANK"
        const val NOTIFICATION_ID = 42
        const val  CHANNEL_ID = "Job Progress"
        const  val ARG_PROGRESS = "Progress"
        const val DELAY_DURATION = 100L // ms

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val notificationManager = appContext.getSystemService(NotificationManager::class.java)

    private val notificationBuilder = NotificationCompat.Builder(appContext, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Background Worker")

    override suspend fun doWork(): Result {
        Log.v(TAG,"Worker Start")

        createNotificationChannel() // create
        val notification = notificationBuilder.build()
        val foregroundInfo = ForegroundInfo(NOTIFICATION_ID,notification)
        setForeground(foregroundInfo)

        for(i in 0..100){
            setProgress(workDataOf(ARG_PROGRESS to i))
            showProgress(i)
            delay(DELAY_DURATION)
        }

        Log.v("TAG","Worker finished.")

        return Result.success()

    }


    private suspend fun  showProgress(progres: Int) {
        val notification = notificationBuilder.setProgress(100,progres,false)
            .build()

        val foregroundInfo = ForegroundInfo(NOTIFICATION_ID,notification)
        setForeground(foregroundInfo)

    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = notificationManager.getNotificationChannel(CHANNEL_ID)
            if(notificationChannel ==null){
                notificationManager.createNotificationChannel(
                    NotificationChannel(CHANNEL_ID,TAG,NotificationManager.IMPORTANCE_LOW))
            }
        }
    }


}