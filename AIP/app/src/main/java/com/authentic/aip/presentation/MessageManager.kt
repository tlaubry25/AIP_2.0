package com.authentic.aip.presentation

import android.content.Context
import android.widget.Toast

object MessageManager {

    fun showToast(context: Context, idString:Int){
        val toast = Toast.makeText(context, context.getString(idString), Toast.LENGTH_SHORT)
        toast.show()
    }
    fun showNotification(){

    }
}