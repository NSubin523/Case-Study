package com.target.targetcasestudy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProductApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}