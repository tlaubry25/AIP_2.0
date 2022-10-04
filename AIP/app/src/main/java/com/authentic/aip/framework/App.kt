package com.authentic.aip.framework

import android.app.Application
import com.authentic.aip.common.Prefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object{
        var prefs : Prefs? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}