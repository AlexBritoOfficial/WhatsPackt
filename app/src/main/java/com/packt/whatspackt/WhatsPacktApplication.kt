package com.packt.whatspackt

import android.app.Application
import androidx.navigation.compose.rememberNavController
import com.packt.chat.data.network.repository.UploadMessagesScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * An application class is crucial for the initialization tasks, such as setting up DI frameworks,
 * or initializing libraries
 * */

@HiltAndroidApp
class WhatsPacktApplication : Application() {

    @Inject
    lateinit var uploadMessagesScheduler: UploadMessagesScheduler

    override fun onCreate() {
        super.onCreate()

       // uploadMessagesScheduler.schedulePeriodicUpload()

    }

}