package com.fta.testdemo

import android.app.Application
import android.content.ContextWrapper

private lateinit var INSTANCE: Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

object AppContext:ContextWrapper(INSTANCE)