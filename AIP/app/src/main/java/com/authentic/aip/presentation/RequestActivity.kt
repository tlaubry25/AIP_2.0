package com.authentic.aip.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.domain.model.Request
import com.authentic.aip.framework.App

import com.authentic.aip.presentation.request.RequestViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class RequestActivity:AppCompatActivity() {
    private val requestViewModel: RequestViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request)
        this.supportActionBar?.hide()
        ToolbarManager.setBackpress(this)
        val toolbarStatus = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_STATUS_CODE.toString(), null)
        ToolbarManager.setDrawableByCodeStatus(this, toolbarStatus)
        val toolbarTitle = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_TITLE.toString(), null)
        if(toolbarTitle!=null){
            ToolbarManager.setTitleText(this, toolbarTitle)
        }


        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val cddeid = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)

        if(sessionId!=null && cddeid!=null){
            requestViewModel.request(sessionId, cddeid, '0')
        }
        requestViewModel.requestDetailLiveData.observe(this){
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

/*
        val notesTextview = findViewById<TextView>(R.id.tv_section_notes)
        notesTextview.setOnClickListener {
            val newActivityIntent = Intent(this, NotesListActivity::class.java)
            startActivity(newActivityIntent)
        }
*/

/*        val attachmentsTextview = findViewById<TextView>(R.id.tv_section_attachement)
        attachmentsTextview.setOnClickListener {
            val newActivityIntent = Intent(this, ListAttachmentsActivity::class.java)
            startActivity(newActivityIntent)
        }*/

        val textTextview = findViewById<TextView>(R.id.tv_section_detail)
        textTextview.setOnClickListener {
            val newActivityIntent = Intent(this, RequestDetailActivity::class.java)
            startActivity(newActivityIntent)
        }

        val validButton = findViewById<Button>(R.id.button_valide)
        validButton.setOnClickListener {
            launchValidationActivity(EnumClass.ActionValidationEnum.VALID)
        }
        val explainButton = findViewById<Button>(R.id.button_question)
        explainButton.setOnClickListener {
            launchValidationActivity(EnumClass.ActionValidationEnum.EXPLAIN)
        }

        val deniedButton = findViewById<Button>(R.id.button_refuse)
        deniedButton.setOnClickListener {
            launchValidationActivity(EnumClass.ActionValidationEnum.REFUSE)
        }
        val changeValidatorButton = findViewById<Button>(R.id.button_transfer)
        changeValidatorButton.setOnClickListener {
            val newActivityIntent = Intent(this, ValidatorUpdateActivity::class.java)
            startActivity(newActivityIntent)
        }

    }
    private fun launchValidationActivity(actionType : EnumClass.ActionValidationEnum){
        val newActivityIntent = Intent(this, ValidationRequestActivity::class.java)
        newActivityIntent.putExtra("actionType", actionType.toString())
        startActivity(newActivityIntent)
    }

    private fun initView(request : Request){
        val tvBeneficiary = findViewById<TextView>(R.id.tv_beneficiary_data)
        tvBeneficiary.text = request.bfidName

        val tvCenter = findViewById<TextView>(R.id.tv_center_data)
        tvCenter.text = request.coce

        val tvSite = findViewById<TextView>(R.id.tv_site_data)
        tvSite.text = request.faci

        val tvProject = findViewById<TextView>(R.id.tv_project_data)
        tvProject.text = request.proj

        val date = Date(request.dwdt.toLong())
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val tvDate = findViewById<TextView>(R.id.tv_date_data)
        tvDate.text = dateFormat.format(date)

        val tvSupplier = findViewById<TextView>(R.id.tv_supplier_data)
        tvSupplier.text = request.sunm

        val tvAmountLocal = findViewById<TextView>(R.id.tv_amount_local_data)
        tvAmountLocal.text = request.dedAmtLoc.toString() + " EUR"

        val tvAmountBought = findViewById<TextView>(R.id.tv_amount_bought_data)
        tvAmountBought.text = request.dedAmtPur.toString() + " EUR"

        val tvBudgetEstimated = findViewById<TextView>(R.id.tv_budget_estimated_data)
        tvBudgetEstimated.text = request.budgAmtLoc.toString() + " EUR"

        val tvBudgetUsed = findViewById<TextView>(R.id.tv_budget_used_data)
        tvBudgetUsed.text = request.engaAmtLoc.toString() + " EUR"

        val tvSectionAttachement = findViewById<TextView>(R.id.tv_section_attachement)
        if(request.nbDocs<=0){
            tvSectionAttachement.isClickable = false
            tvSectionAttachement.setTextColor(this.getColor(R.color.grey))
        }else{
            tvSectionAttachement.setOnClickListener {
                val newActivityIntent = Intent(this, ListAttachmentsActivity::class.java)
                startActivity(newActivityIntent)
            }
        }
        tvSectionAttachement.text = String.format(getString(R.string.section_attachement), request.nbDocs)

        val tvSectionNotes = findViewById<TextView>(R.id.tv_section_notes)
        if(request.nbNotes<=0){
            tvSectionNotes.isClickable = false
            tvSectionNotes.setTextColor(this.getColor(R.color.grey))
        }else{
            tvSectionNotes.setOnClickListener {
                val newActivityIntent = Intent(this, NotesListActivity::class.java)
                startActivity(newActivityIntent)
            }
        }
        tvSectionNotes.text = String.format(getString(R.string.section_notes), request.nbNotes)

        val tvSectionDetail = findViewById<TextView>(R.id.tv_section_detail)
        tvSectionDetail.text = getString(R.string.section_detail)
    }
}