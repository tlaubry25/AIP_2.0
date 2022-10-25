package com.authentic.aip.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
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
        this.supportActionBar?.hide()
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        ToolbarManager.setBackpress(this)
        val toolbarStatus = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_STATUS_CODE.toString(), null)
        ToolbarManager.setDrawableByCodeStatus(this, toolbarStatus)
        val toolbarTitle = App.prefs?.preferences?.getString(EnumClass.PreferencesEnum.REQUEST_TITLE.toString(), null)
        if(toolbarTitle!=null){
            ToolbarManager.setTitleText(this, toolbarTitle)
        }
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
                        validationRequestViewModel.validateRequest(this, sessionId, cddeid, 0, 'A', etCommentaire.text.toString())
                }
                EnumClass.ActionValidationEnum.EXPLAIN->{
                    if(sessionId!=null && cddeid!=null)
                        validationRequestViewModel.explainRequest(this, sessionId, cddeid, 0, 'A', etCommentaire.text.toString())
                }
                EnumClass.ActionValidationEnum.REFUSE->{
                    if(sessionId!=null && cddeid!=null)
                        validationRequestViewModel.denyRequest(this, sessionId, cddeid, 0, 'A', etCommentaire.text.toString())
                }
                else->{}
            }
        }

        validationRequestViewModel.validateRequestLiveData.observe(this){
            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                    Log.d("AIP", "call validateRequest STATE LOADING")
                }
                it.isError!=null -> {
                    progressBar.visibility = View.GONE
                    Log.d("AIP", "call validateRequest STATE ERROR")
                }
                it.data!=null ->{
                    progressBar.visibility = View.GONE
                    Log.d("AIP", "call validateRequest STATE SUCCESS")
                    //WEBSERVICE SUCCESS, GOTO LISTREQUEST
                }
                else->{
                    progressBar.visibility = View.GONE
                    Log.d("AIP", "call validateRequest STATE ELSE")
                    //GOTO LISTREQUEST
                    val newActivityIntent = Intent(this, ListRequestActivity::class.java)
                    newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(newActivityIntent)
                }
            }
        }
        validationRequestViewModel.explainDataLiveData.observe(this){
            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                    Log.d("AIP", "call explainRequest STATE LOADING")
                }
                it.isError!=null -> {
                    progressBar.visibility = View.GONE
                    Log.d("AIP", "call explainRequest STATE ERROR")
                }
                it.data!=null ->{
                    progressBar.visibility = View.GONE
                    Log.d("AIP", "call explainRequest STATE SUCCESS")
                    //WEBSERVICE SUCCESS, GOTO LISTREQUEST
                }
                else->{
                    Log.d("AIP", "call explainRequest STATE NULL")
                    progressBar.visibility = View.GONE
                    //GOTO LISTREQUEST
                    val newActivityIntent = Intent(this, ListRequestActivity::class.java)
                    newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(newActivityIntent)
                }
            }
        }

        validationRequestViewModel.denyRequestLiveData.observe(this){
            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                    Log.d("AIP", "call denyRequest STATE LOADING")
                }
                it.isError!=null -> {
                    progressBar.visibility = View.GONE
                    Log.d("AIP", "call denyRequest STATE ERROR")
                }
                it.data!=null ->{
                    progressBar.visibility = View.GONE
                    Log.d("AIP", "call denyRequest STATE SUCCESS")
                    //WEBSERVICE SUCCESS, GOTO LISTREQUEST
                }
                else->{
                    progressBar.visibility = View.GONE
                    Log.d("AIP", "call denyRequest STATE NULL")
                    //GOTO LISTREQUEST
                    val newActivityIntent = Intent(this, ListRequestActivity::class.java)
                    newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(newActivityIntent)
                }
            }
        }


    }
}