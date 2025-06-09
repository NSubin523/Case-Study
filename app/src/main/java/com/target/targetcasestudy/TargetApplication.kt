package com.target.targetcasestudy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TargetApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}