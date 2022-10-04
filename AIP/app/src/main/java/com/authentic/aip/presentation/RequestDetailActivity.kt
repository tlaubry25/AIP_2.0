package com.authentic.aip.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.domain.model.RequestDetail
import com.authentic.aip.framework.App

import com.authentic.aip.presentation.requestDetail.RequestDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class RequestDetailActivity:AppCompatActivity() {
    private val requestDetailViewModel: RequestDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_detail)
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val cddeid = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)
        if(sessionId!=null && cddeid!=null){
            requestDetailViewModel.requestDetail(sessionId, cddeid, '0')
        }
        requestDetailViewModel.requestDetailLiveData.observe(this){
            when{
                it.isLoading->{
                    Log.d("TLA", "STATE LOADING")
                }
                it.error.isNotEmpty() -> {
                    Log.d("TLA", "STATE ERROR")
                }
                it.requestData!=null ->{
                    Log.d("TLA", "STATE SUCCESS")
                    initView(it.requestData)
                }
            }
        }

    }

    fun initView(requestDetail : RequestDetail){
        val tvBeneficiary = findViewById<TextView>(R.id.tv_beneficiary_data)
        tvBeneficiary.text = requestDetail.bfidName

        val tvCenter = findViewById<TextView>(R.id.tv_center_data)
        tvCenter.text = requestDetail.coce

        val tvSite = findViewById<TextView>(R.id.tv_site_data)
        tvSite.text = requestDetail.faci

        val tvProject = findViewById<TextView>(R.id.tv_project_data)
        tvProject.text = requestDetail.proj

        val date = Date(requestDetail.dwdt.toLong())
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val tvDate = findViewById<TextView>(R.id.tv_date_data)
        tvDate.text = dateFormat.format(date)

        val tvSupplier = findViewById<TextView>(R.id.tv_supplier_data)
        tvSupplier.text = requestDetail.sunm

        val tvAmountLocal = findViewById<TextView>(R.id.tv_amount_local_data)
        tvAmountLocal.text = requestDetail.dedAmtLoc.toString() + " EUR"

        val tvAmountBought = findViewById<TextView>(R.id.tv_amount_bought_data)
        tvAmountBought.text = requestDetail.dedAmtPur.toString() + " EUR"

        val tvBudgetEstimated = findViewById<TextView>(R.id.tv_budget_estimated_data)
        tvBudgetEstimated.text = requestDetail.budgAmtLoc.toString() + " EUR"

        val tvBudgetUsed = findViewById<TextView>(R.id.tv_budget_used_data)
        tvBudgetUsed.text = requestDetail.engaAmtLoc.toString() + " EUR"

        val tvSectionAttachement = findViewById<TextView>(R.id.tv_section_attachement)
        tvSectionAttachement.isClickable = requestDetail.nbDocs>0
        tvSectionAttachement.text = String.format(getString(R.string.section_attachement), requestDetail.nbDocs)

        val tvSectionNotes = findViewById<TextView>(R.id.tv_section_notes)
        tvSectionNotes.text = String.format(getString(R.string.section_notes), requestDetail.nbNotes)

        val tvSectionDetail = findViewById<TextView>(R.id.tv_section_detail)
        tvSectionDetail.text = getString(R.string.section_detail)
    }
}