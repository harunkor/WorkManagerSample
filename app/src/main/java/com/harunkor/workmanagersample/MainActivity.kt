package com.harunkor.workmanagersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.work.*
import com.harunkor.workmanagersample.ForegroundWorker.Companion.ARG_PROGRESS
import com.harunkor.workmanagersample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        //setContentView(R.layout.activity_main)

       // val logWorkRequest : WorkRequest = OneTimeWorkRequestBuilder<LogWorker>().build()
       // WorkManager.getInstance(this).enqueue(logWorkRequest)

        val workManager = WorkManager.getInstance(this)

        binding.button.setOnClickListener {

            val workRequest = OneTimeWorkRequest.from(ForegroundWorker::class.java)


            workManager.getWorkInfoByIdLiveData(workRequest.id)
                .observe(this) { workInfo ->
                    if(workInfo !=null){
                        val progress = workInfo.progress
                        val value = progress.getInt(ARG_PROGRESS,0)
                        binding.progressBar.progress = value
                        if(workInfo.state == WorkInfo.State.SUCCEEDED){
                            binding.button.isEnabled = true
                        }
                    }

                }

            workManager.enqueue(workRequest)

            binding.button.isEnabled = false

        }



    }
}