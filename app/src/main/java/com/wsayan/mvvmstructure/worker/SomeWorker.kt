package com.wsayan.mvvmstructure.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SomeWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {


    override fun doWork(): Result {
        doSomeWork()
        return Result.success()
    }

    private fun doSomeWork() {
        Log.d("Worker", "working......")
    }
}

