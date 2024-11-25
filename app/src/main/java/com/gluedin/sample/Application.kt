package com.gluedin.sample

import android.app.Application

import com.gluedin.GluedInInitializer

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        GluedInInitializer.initSdk(this)
    }
}