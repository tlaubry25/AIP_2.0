package com.authentic.aip.framework

import android.app.Application
import com.authentic.aip.common.Prefs
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object{
        var prefs : Prefs? = null
        var firebaseToken : String? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            firebaseToken = task.result.toString()
        })
        val receiver = FirebaseMessageReceiver()

    }
}