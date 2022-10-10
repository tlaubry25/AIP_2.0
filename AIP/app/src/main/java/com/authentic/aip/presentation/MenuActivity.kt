package com.authentic.aip.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.framework.App.Companion.prefs
import com.authentic.aip.presentation.menu.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : AppCompatActivity(){
    private val menuViewModel: MenuViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)
        this.supportActionBar?.hide()
        val sessionId = prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        if(!sessionId.isNullOrEmpty()){
            menuViewModel.getNbRequest(sessionId)
        }
        menuViewModel.menuLiveData.observe(this){
            when{
                it.isLoading->{
                    Log.d("TLA", "STATE LOADING")
                }
                it.error.isNotEmpty() -> {
                    Log.d("TLA", "STATE ERROR")
                }
                it.nbRequest!=null ->{
                    Log.d("TLA", "STATE SUCCESS")
                    if(it.nbRequest>0){
                        val clNotification = findViewById<ConstraintLayout>(R.id.cl_number_request_notification)
                        val numberInNotification = findViewById<TextView>(R.id.tv_notification_number)
                        numberInNotification.text = it.nbRequest.toString()
                        clNotification.visibility = View.VISIBLE
                    }

                }
            }
        }
        val clOnGoingRequest = findViewById<ConstraintLayout>(R.id.cl_progress)
        val clHistoricalRequest = findViewById<ConstraintLayout>(R.id.cl_historical)

        clOnGoingRequest.setOnClickListener {
            val newActivityIntent = Intent(this, ListRequestActivity::class.java)
            val uidEditor = prefs?.preferences?.edit()
            uidEditor?.putString(EnumClass.PreferencesEnum.REQUEST_TYPE.toString(), EnumClass.TypeRequestEnum.ONGOING.toString())
            uidEditor?.commit()
//            newActivityIntent.putExtra("typeRequest", EnumClass.TypeRequestEnum.ONGOING.toString())
            startActivity(newActivityIntent)
        }

        clHistoricalRequest.setOnClickListener {
            val newActivityIntent = Intent(this, ListRequestActivity::class.java)
            val uidEditor = prefs?.preferences?.edit()
            uidEditor?.putString(EnumClass.PreferencesEnum.REQUEST_TYPE.toString(), EnumClass.TypeRequestEnum.DONE.toString())
            uidEditor?.commit()
//            newActivityIntent.putExtra("typeRequest", EnumClass.TypeRequestEnum.DONE.toString())
            startActivity(newActivityIntent)
        }
    }
}