package com.authentic.aip.presentation

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.authentic.aip.R
import com.authentic.aip.framework.App
import java.util.*


object MessageManager {


    fun showToast(context: Context, idString:Int){
        val toast = Toast.makeText(context, context.getString(idString), Toast.LENGTH_SHORT)
        toast.show()
    }
    fun showNotification(context: Context){
        val contentIntent = PendingIntent.getActivity(context,0,Intent(),PendingIntent.FLAG_UPDATE_CURRENT)
        var builder = NotificationCompat.Builder(context, App.firebaseToken!!)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.notification_new_data))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(0, builder.build())
        }
    }
}