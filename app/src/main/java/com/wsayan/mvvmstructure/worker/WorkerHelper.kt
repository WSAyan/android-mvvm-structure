package com.wsayan.mvvmstructure.worker

import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 * creates, starts and stops workers
 */
object WorkerHelper {
    const val WORK_NAME = "_SOME_WORK_NAME"
    const val WORK_INTERVAL = 15L

    private fun createConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .setRequiresBatteryNotLow(false)
        .build()

    private fun createPeriodicWorkRequest(timeout: Long) =
        PeriodicWorkRequestBuilder<SomeWorker>(timeout, TimeUnit.MINUTES)
            .setConstraints(createConstraints())
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

    fun startPeriodicWork(
        workName: String = WORK_NAME,
        timeout: Long = WORK_INTERVAL
    ): PeriodicWorkRequest {
        val periodicWorkRequest = createPeriodicWorkRequest(timeout)

        WorkManager
            .getInstance()
            .enqueueUniquePeriodicWork(
                workName,
                ExistingPeriodicWorkPolicy.REPLACE,
                periodicWorkRequest
            )
        return periodicWorkRequest
    }

    fun stopWork(workName: String) {
        WorkManager.getInstance().cancelUniqueWork(workName)
    }
}