package com.robustdev.airlineexplorer

import android.app.Application
import android.content.Context

class MyApp : Application() {
    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}