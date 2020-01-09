package com.bennyhuo.github

import android.app.Application
import android.content.ContextWrapper
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho

private lateinit var INSTANCE: Application

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        //调试代码
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}

object AppContext : ContextWrapper(INSTANCE)