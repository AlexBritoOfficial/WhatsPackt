package com.packt.chat.data.network.repository

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.packt.chat.domain.usecases.UploadMessagesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope

@HiltWorker
class UploadMessagesWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val uploadMessagesUseCase: UploadMessagesUseCase
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val MAX_TRIES = 3
    }

    // Work to be done in the background
    override suspend fun doWork(): Result = coroutineScope {

        try {
            uploadMessagesUseCase()
            Result.success()
        } catch (e: Exception) {

            if (runAttemptCount < MAX_TRIES) {
                Result.retry()
            } else {
                Result.failure()
            }
        }

    }
}