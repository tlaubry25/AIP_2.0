package com.authentic.aip.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.framework.App.Companion.firebaseToken
import com.authentic.aip.framework.App.Companion.prefs
import com.authentic.aip.presentation.login.LoginViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.log

@AndroidEntryPoint
class MainActivity:AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        this.supportActionBar?.hide()

        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val loginText = findViewById<TextInputEditText>(R.id.et_identifier)
        val passwordText = findViewById<TextInputEditText>(R.id.et_password)
        val adressText = findViewById<TextInputEditText>(R.id.et_adress)
        val portText = findViewById<TextInputEditText>(R.id.et_port)
        val loginComponent = findViewById<TextInputLayout>(R.id.til_identifier)
        val passwordComponent = findViewById<TextInputLayout>(R.id.til_password)
        val adressComponent = findViewById<TextInputLayout>(R.id.til_adress)
        val portComponent = findViewById<TextInputLayout>(R.id.til_port)

        loginViewModel.loginLiveData.observe(this){

            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                    Log.d("AIP", "call login STATE LOADING")
                }
                it.error.isNotEmpty() -> {
                    progressBar.visibility = View.GONE
                    Log.d("AIP", "call login STATE ERROR")
                }
                it.loginObject!=null ->{
                    val uidEditor = prefs?.preferences?.edit()
                    Log.d("AIP", "call login STATE SUCCESS")
                    uidEditor?.putString(EnumClass.PreferencesEnum.SESSION_ID.toString(), it.loginObject.uid)
                    uidEditor?.commit()

                    val uidDevice : String? = firebaseToken
                    if(uidDevice!=null){
                        it.loginObject.uid?.let { it1 -> loginViewModel.registerDevice(this, it1, 'A', uidDevice) }
                    }else{
                        it.loginObject.uid?.let { it1 -> loginViewModel.registerDevice(this, it1, 'A', "") }
                    }

                }
            }

            loginViewModel.registerDeviceLiveData.observe(this){
                when{
                    it.isLoading->{
                        Log.d("AIP", "call registerDevice STATE LOADING")
                    }
                    it.isError!=null -> {
                        progressBar.visibility = View.GONE
                        Log.d("AIP", "call registerDevice STATE ERROR")
                        MessageManager.showToast(this, R.string.ws_error_unknown)
                    }
                    it.data!=null ->{
                        progressBar.visibility = View.GONE
                        Log.d("AIP", "call registerDevice STATE SUCCESS")
                    }
                    else->{
                        progressBar.visibility = View.GONE
                        Log.d("AIP", "call registerDevice STATE NULL")
                        MessageManager.showToast(this, R.string.login_ws_ok)
                        val toMenuActivity = Intent(this, MenuActivity::class.java)
                        toMenuActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(toMenuActivity)
                        finish()
                    }
                }
            }

            loginViewModel.verifyUrlLiveData.observe(this){
                when{
                    it.isLoading->{
                        progressBar.visibility = View.VISIBLE
                        Log.d("AIP", "call verifyUrl STATE LOADING")
                    }
                    it.isError!=null -> {
                        progressBar.visibility = View.GONE
                        Log.d("AIP", "call verifyUrl STATE ERROR")
                        MessageManager.showToast(this, R.string.login_ws_error)
                    }
                    it.data!=null ->{
                        progressBar.visibility = View.GONE
                    }
                    else->{
                        Log.d("AIP", "call verifyUrl STATE OK")
                        progressBar.visibility = View.GONE
                        MessageManager.showToast(this, R.string.login_ws_ok)
                    }
                }
            }
        }
        val urlTestButton = findViewById<Button>(R.id.button_test)
        urlTestButton.setOnClickListener {

            if(loginText.text.isNullOrEmpty()){
                loginComponent.setError(getString(R.string.login_error_login_empty))
            }else{
                loginComponent.setError(null)
            }


            if(passwordText.text.isNullOrEmpty()){
                passwordComponent.setError(getString(R.string.login_error_password_empty))
            }else{
                passwordComponent.setError(null)
            }


            if(adressText.text.isNullOrEmpty()){
                adressComponent.setError(getString(R.string.login_error_server_empty))
            }else{
                adressComponent.setError(null)
            }


            if(portText.text.isNullOrEmpty()){
                portComponent.setError(getString(R.string.login_error_port_empty))
            }else{
                portComponent.setError(null)
            }

            if(portText.text?.isNotEmpty() == true && adressText.text?.isNotEmpty() == true){
                loginViewModel.verifyUrl(this, adressText.text.toString(), portText.text.toString())
            }
        }
        val loginButton = findViewById<Button>(R.id.button_login)
        loginButton.setOnClickListener {

            if(loginText.text.isNullOrEmpty()){
                loginComponent.setError(getString(R.string.login_error_login_empty))
            }else{
                loginComponent.setError(null)
            }


            if(passwordText.text.isNullOrEmpty()){
                passwordComponent.setError(getString(R.string.login_error_password_empty))
            }else{
                passwordComponent.setError(null)
            }


            if(adressText.text.isNullOrEmpty()){
                adressComponent.setError(getString(R.string.login_error_server_empty))
            }else{
                adressComponent.setError(null)
            }


            if(portText.text.isNullOrEmpty()){
                portComponent.setError(getString(R.string.login_error_port_empty))
            }else{
                portComponent.setError(null)
            }

            var language = Locale.getDefault().language
            if(language.isNullOrEmpty()){
                language = "en"
            }else{
                if(!language.equals("en") && !language.equals("fr")) language = "en"
            }
            if(portText.text?.isNotEmpty() == true && adressText.text?.isNotEmpty() == true) {
                loginViewModel.getLogin(this, loginText.text.toString(), passwordText.text.toString(),
                    language,
                    adressText.text.toString(), portText.text.toString()
                )
            }
        }
    }
}