package com.packt.whatspackt

import android.app.Application
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.HiltAndroidApp

/**
 * An application class is crucial for the initialization tasks, such as setting up DI frameworks,
 * or initializing libraries
 * */

@HiltAndroidApp
class WhatsPacktApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }

}