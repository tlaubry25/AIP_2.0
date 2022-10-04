package com.authentic.aip.common

import android.content.Context
import android.content.SharedPreferences
import com.authentic.aip.R

class Prefs(context:Context) {
    val preferences: SharedPreferences = context.getSharedPreferences(context.getString(R.string.sharedPreferenceId),Context.MODE_PRIVATE)

}