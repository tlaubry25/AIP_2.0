package com.authentic.aip.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.domain.model.Validator
import com.authentic.aip.domain.model.ValidatorList
import com.authentic.aip.framework.App
import com.authentic.aip.presentation.validator.ListValidatorsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ValidatorUpdateActivity:AppCompatActivity() {

    private val listValidatorsViewModel: ListValidatorsViewModel by viewModels()
    private var pageNumber = 1
    private lateinit var arrayAdapter : ArrayAdapter<Validator>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.validator_modification)
        this.supportActionBar?.hide()
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        ToolbarManager.setBackpress(this)
        val toolbarStatus = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_STATUS_CODE.toString(), null)
        ToolbarManager.setDrawableByCodeStatus(this, toolbarStatus)
        val toolbarTitle = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_TITLE.toString(), null)
        if(toolbarTitle!=null){
            ToolbarManager.setTitleText(this, toolbarTitle)
        }
        loadValidators()
        val spinnerList = findViewById<Spinner>(R.id.spinner_validators)
        arrayAdapter = ArrayAdapter<Validator>(this, android.R.layout.simple_dropdown_item_1line, arrayListOf())
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerList.adapter = arrayAdapter

        listValidatorsViewModel.listValidatorsLiveData.observe(this){
            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE ERROR") }
                it.listValidators!=null ->{
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE SUCCESS")

                    initview(it.listValidators)
                }
            }
            spinnerList.setOnScrollChangeListener(object : View.OnScrollChangeListener{
                override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {
                    val size = spinnerList.size
                    if(spinnerList.lastVisiblePosition == size-1){
                        pageNumber++
                        loadValidators()
                    }
                }
            })

            listValidatorsViewModel.updateValidatorLiveData.observe(this){
                when{
                    it.isLoading->{
                        progressBar.visibility = View.VISIBLE
                        Log.d("TLA", "STATE LOADING") }
                    it.isError!=null -> {
                        progressBar.visibility = View.GONE
                        Log.d("TLA", "STATE ERROR") }
                    it.data!=null ->{
                        progressBar.visibility = View.GONE
                        Log.d("TLA", "STATE SUCCESS")
                    }
                    else->{
                        progressBar.visibility = View.GONE
                        //GOTO LISTREQUEST
                        val newActivityIntent = Intent(this, ListRequestActivity::class.java)
                        newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(newActivityIntent)
                    }
                }
            }

            val validationButton = findViewById<Button>(R.id.button_validation)
            validationButton.setOnClickListener {
                //call ws modify
                val validator = spinnerList.selectedItem as Validator

                val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
                val cddeid = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)
                if(sessionId !=null && cddeid != null && validator != null){
                    Log.d("TLA", "uid : "+sessionId+" cddeid : "+cddeid+" user : "+validator?.user)
                    if(validator.user!=null){
                        listValidatorsViewModel.updateValidator(this, sessionId, cddeid, 0, validator.user, "")
                    }
                }

            }
        }
    }
    private fun initview(listValidator : ValidatorList){
        var listValidatorToFill = mutableListOf<Validator>()
        if(!listValidator.listValidators.isNullOrEmpty()){
            for(validator in listValidator.listValidators){
                if(validator!=null){
                    listValidatorToFill.add(validator)
                }
            }
        }
        arrayAdapter.addAll(listValidatorToFill)
        arrayAdapter.notifyDataSetChanged()
    }

    private fun loadValidators(){
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val requestId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)
        if (sessionId != null && requestId != null) {
            listValidatorsViewModel.getListValidators(this, sessionId, requestId, pageNumber)
        }
    }
}