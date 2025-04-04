package com.cricut.androidquiz

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CricutApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}