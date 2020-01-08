package com.fta.testdemo

import android.app.Application
import android.content.ContextWrapper
import com.facebook.stetho.Stetho

private lateinit var INSTANCE: Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Stetho.initializeWithDefaults(this)
    }
}

object AppContext:ContextWrapper(INSTANCE)