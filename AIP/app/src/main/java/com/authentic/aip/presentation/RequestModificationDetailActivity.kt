package com.authentic.aip.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.domain.model.RequestDetailModification
import com.authentic.aip.framework.App
import com.authentic.aip.presentation.requestDetailModification.RequestDetailModificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestModificationDetailActivity:AppCompatActivity() {
    private val requestDetailModificationViewModel: RequestDetailModificationViewModel by viewModels()
    var deli : Int?=0
    var lineType : String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_modification_detail)
        this.supportActionBar?.hide()
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        ToolbarManager.setBackpress(this)
        val toolbarStatus = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_STATUS_CODE.toString(), null)
        ToolbarManager.setDrawableByCodeStatus(this, toolbarStatus)
        val toolbarTitle = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_TITLE.toString(), null)
        if(toolbarTitle!=null){
            ToolbarManager.setTitleText(this, toolbarTitle)
        }
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val cddeid = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)
        val intent = intent
        deli = intent.getIntExtra("deli", 0)
        lineType = intent.getStringExtra("lineType")
        if(sessionId!=null && cddeid!=null && deli!=null){
            requestDetailModificationViewModel.getRequestChanges(sessionId, cddeid, deli!!)
        }


        requestDetailModificationViewModel.requestDetailModificationLiveData.observe(this){
            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE ERROR") }
                it.requestModificationData!=null ->{
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE SUCCESS")

                    initview(it.requestModificationData)
                }
            }
        }
    }

    private fun initview(requestDetailModification : RequestDetailModification){
        val oldTitleContainer = findViewById<TextView>(R.id.tv_old_data_title)
        val newTitleContainer = findViewById<TextView>(R.id.tv_new_data_title)
        val clOldDataContainer = findViewById<ConstraintLayout>(R.id.cl_old_data_container)
        if(lineType!=null){
            if(lineType.equals("A")){
                oldTitleContainer.visibility = View.GONE
                clOldDataContainer.visibility = View.GONE
                newTitleContainer.visibility = View.GONE
            }else{
                oldTitleContainer.visibility = View.VISIBLE
                clOldDataContainer.visibility = View.VISIBLE
                newTitleContainer.visibility = View.VISIBLE
            }
        }

        val tvOldTitle = findViewById<TextView>(R.id.tv_request_detail_old_title)
        tvOldTitle.text = requestDetailModification.itdsOld
        val tvOldTotalAmount = findViewById<TextView>(R.id.tv_request_detail_old_total_amount_data)
        tvOldTotalAmount.text = " "+getString(R.string.request_detail_data_price,  requestDetailModification.lnamOld.toString())
        val tvOldUnitarydPrice = findViewById<TextView>(R.id.tv_request_detail_old_unitary_price_data)
        tvOldUnitarydPrice.text = " "+ getString(R.string.request_detail_data_price, requestDetailModification.puprOld.toString())
        val tvOldAmount = findViewById<TextView>(R.id.tv_request_detail_old_amount_data)
        tvOldAmount.text = requestDetailModification.ppqtOld.toString()

        val tvNewTitle = findViewById<TextView>(R.id.tv_request_detail_title)
        tvNewTitle.text = requestDetailModification.itds
        val tvNewTotalAmount = findViewById<TextView>(R.id.tv_request_detail_total_amount_data)
        tvNewTotalAmount.text = " "+getString(R.string.request_detail_data_price,  requestDetailModification.lnam.toString())
        val tvNewAmount = findViewById<TextView>(R.id.tv_request_detail_amount_data)
        tvNewAmount.text = requestDetailModification.ppqt.toString()
        val tvNewUnitaryPrice = findViewById<TextView>(R.id.tv_request_detail_unitary_price_data)
        tvNewUnitaryPrice.text = " "+ getString(R.string.request_detail_data_price, requestDetailModification.pupr.toString())
    }

}