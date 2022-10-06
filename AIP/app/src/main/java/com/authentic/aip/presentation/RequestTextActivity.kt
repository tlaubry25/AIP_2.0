package com.authentic.aip.presentation

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.framework.App
import com.authentic.aip.presentation.menu.MenuViewModel
import com.authentic.aip.presentation.requestText.RequestTextViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestTextActivity : AppCompatActivity() {
    private val requestTextViewModel: RequestTextViewModel by viewModels()
    var deli : Int?=0
    var textTitle : String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_text)
        val intent = intent
        textTitle = intent.getStringExtra("requestTextTitle")
        deli = intent.getIntExtra("deli", 0)
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val requestId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)
        if(!sessionId.isNullOrEmpty() && !requestId.isNullOrEmpty()){
            if(deli!=null){
                requestTextViewModel.getNbRequest(sessionId, requestId, deli!!)
            }
        }
        requestTextViewModel.requestTextLiveData.observe(this){
            when{
                it.isLoading->{
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    Log.d("TLA", "STATE ERROR") }
                it.requestText!=null ->{
                    Log.d("TLA", "STATE SUCCESS")
                    initview(it.requestText)
                }
            }
        }
    }

    private fun initview(textData:String){
        val tv_titleText = findViewById<TextView>(R.id.tv_request_text_title)
        tv_titleText.text = textTitle

        val tv_requestText = findViewById<TextView>(R.id.tv_request_text_data)
        tv_requestText.text = textData
    }
}