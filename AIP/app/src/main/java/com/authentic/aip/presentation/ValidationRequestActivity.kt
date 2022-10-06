package com.authentic.aip.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.framework.App
import com.authentic.aip.presentation.ValidationRequest.ValidationRequestViewModel
import com.authentic.aip.presentation.request.RequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ValidationRequestActivity : AppCompatActivity() {
    var choiceValidation : EnumClass.ActionValidationEnum?=null
    private val validationRequestViewModel: ValidationRequestViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.validation_request)
        val intent = intent
        val actionType = intent.getStringExtra("actionType")
        val sessionId = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.SESSION_ID.toString(), null)
        val cddeid = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_ID.toString(), null)

        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val etCommentaire = findViewById<EditText>(R.id.et_commentary_bloc)
        val bt_validation = findViewById<Button>(R.id.button_validate)
        val titleText : String

        when(actionType){
            EnumClass.ActionValidationEnum.VALID.toString()->{
                titleText = getString(R.string.validation_validate)
                choiceValidation = EnumClass.ActionValidationEnum.VALID
            }
            EnumClass.ActionValidationEnum.EXPLAIN.toString()->{
                titleText = getString(R.string.validation_question)
                choiceValidation = EnumClass.ActionValidationEnum.EXPLAIN
            }
            EnumClass.ActionValidationEnum.REFUSE.toString()->{
                titleText = getString(R.string.validation_refuse)
                choiceValidation = EnumClass.ActionValidationEnum.REFUSE
            }
            else->{
                titleText = getString(R.string.validation_validate)
            }
        }
        tvTitle.text = titleText
        //"orderType": "Mauvais type : A (approbation), V (validation), 0 (tous) uniquement"
        bt_validation.setOnClickListener {
            when(choiceValidation){
                EnumClass.ActionValidationEnum.VALID->{
                    if(sessionId!=null && cddeid!=null)
                        validationRequestViewModel.validateRequest(sessionId, cddeid, 0, 'A', etCommentaire.text.toString())
                }
                EnumClass.ActionValidationEnum.EXPLAIN->{
                    if(sessionId!=null && cddeid!=null)
                        validationRequestViewModel.explainRequest(sessionId, cddeid, 0, 'A', etCommentaire.text.toString())
                }
                EnumClass.ActionValidationEnum.REFUSE->{
                    if(sessionId!=null && cddeid!=null)
                        validationRequestViewModel.denyRequest(sessionId, cddeid, 0, 'A', etCommentaire.text.toString())
                }
                else->{}
            }
        }

        validationRequestViewModel.validateRequestLiveData.observe(this){
            when{
                it.isLoading->{
                    Log.d("TLA", "STATE LOADING")
                }
                it.error.isNotEmpty() -> {
                    Log.d("TLA", "STATE ERROR")
                }
                it.data==null ->{
                    Log.d("TLA", "STATE SUCCESS")
                    //WEBSERVICE SUCCESS, GOTO LISTREQUEST
                }
            }
        }
        validationRequestViewModel.explainDataLiveData.observe(this){
            when{
                it.isLoading->{
                    Log.d("TLA", "STATE LOADING")
                }
                it.error.isNotEmpty() -> {
                    Log.d("TLA", "STATE ERROR")
                }
                it.data==null ->{
                    //WEBSERVICE SUCCESS, GOTO LISTREQUEST
                }
            }
        }

        validationRequestViewModel.denyRequestLiveData.observe(this){
            when{
                it.isLoading->{
                    Log.d("TLA", "STATE LOADING")
                }
                it.error.isNotEmpty() -> {
                    Log.d("TLA", "STATE ERROR")
                }
                it.data==null ->{
                    //WEBSERVICE SUCCESS, GOTO LISTREQUEST
                }
            }
        }


    }
}