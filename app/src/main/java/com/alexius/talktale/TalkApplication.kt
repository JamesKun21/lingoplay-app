package com.alexius.talktale

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TalkApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}