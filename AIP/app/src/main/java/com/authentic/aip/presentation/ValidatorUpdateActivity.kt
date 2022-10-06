package com.authentic.aip.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
    var selectedValidator : Validator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.validator_modification)
        loadValidators()
        val spinnerList = findViewById<Spinner>(R.id.spinner_validators)
        arrayAdapter = ArrayAdapter<Validator>(this, android.R.layout.simple_dropdown_item_1line, arrayListOf())
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerList.adapter = arrayAdapter

        listValidatorsViewModel.listValidatorsLiveData.observe(this){
            when{
                it.isLoading->{
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    Log.d("TLA", "STATE ERROR") }
                it.listValidators!=null ->{
                    Log.d("TLA", "STATE SUCCESS")

                    initview(it.listValidators)
                }
            }
            spinnerList.setOnScrollChangeListener(object : View.OnScrollChangeListener{
                override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {

                }

            })

            listValidatorsViewModel.updateValidatorLiveData.observe(this){
                when{
                    it.isLoading->{
                        Log.d("TLA", "STATE LOADING") }
                    it.error.isNotEmpty() -> {
                        Log.d("TLA", "STATE ERROR") }
                    it.data==null ->{
                        Log.d("TLA", "STATE SUCCESS")
                        //WEBSERVICE SUCCESS
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
                        listValidatorsViewModel.updateValidator(sessionId, cddeid, 0, validator.user, "")
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
            listValidatorsViewModel.getListValidators(sessionId, requestId, pageNumber)
        }
    }
}