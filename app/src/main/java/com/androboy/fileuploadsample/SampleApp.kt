package com.androboy.fileuploadsample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SampleApp: Application() {
    companion object{
        var INSTANCE: SampleApp = SampleApp()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}