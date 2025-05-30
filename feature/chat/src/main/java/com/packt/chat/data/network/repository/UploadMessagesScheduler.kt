package com.packt.chat.data.network.repository

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UploadMessagesScheduler @Inject constructor(
    private val context: Context
) {

    companion object {
        val TAG = "UploadMessagesScheduler"
    }

    fun schedulePeriodicUpload() {


        // Set up the constraints to attached to the request object
        val constraints = Constraints.Builder().setRequiredNetworkType(
            NetworkType.UNMETERED
        ).build()

        // Create the work request object
        val uploadMessagesScheduler =
            PeriodicWorkRequestBuilder<UploadMessagesWorker>(7, TimeUnit.DAYS)
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
                    TimeUnit.MILLISECONDS
                ).build()

        WorkManager.getInstance(context = context).enqueueUniquePeriodicWork(
            TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            uploadMessagesScheduler
        )
    }

}