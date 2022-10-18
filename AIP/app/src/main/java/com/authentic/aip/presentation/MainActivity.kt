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
import com.authentic.aip.framework.App.Companion.prefs
import com.authentic.aip.presentation.login.LoginViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity:AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        this.supportActionBar?.hide()
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        loginViewModel.loginLiveData.observe(this){

            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                }
                it.error.isNotEmpty() -> {
                    progressBar.visibility = View.GONE
                    MessageManager.showToast(this, R.string.login_ws_error)
                }
                it.loginObject!=null ->{
                    val uidEditor = prefs?.preferences?.edit()
                    uidEditor?.putString(EnumClass.PreferencesEnum.SESSION_ID.toString(), it.loginObject.uid)
                    uidEditor?.commit()

                    val uidDevice : String = ""
                    loginViewModel.registerDevice(it.loginObject.uid, 'A', uidDevice)
                }
            }

            loginViewModel.registerDeviceLiveData.observe(this){
                when{
                    it.isLoading->{

                    }
                    it.error.isNotEmpty() -> {
                        progressBar.visibility = View.GONE
                        MessageManager.showToast(this, R.string.ws_error_unknown)
                    }
                    it.data!=null ->{
                        progressBar.visibility = View.GONE
                    }
                    else->{
                        progressBar.visibility = View.GONE
                        MessageManager.showToast(this, R.string.login_ws_ok)
                        val toMenuActivity = Intent(this, MenuActivity::class.java)
                        startActivity(toMenuActivity)
                    }
                }
            }

            loginViewModel.verifyUrlLiveData.observe(this){
                when{
                    it.isLoading->{
                        progressBar.visibility = View.VISIBLE
                    }
                    it.error.isNotEmpty() -> {
                        progressBar.visibility = View.GONE
                        MessageManager.showToast(this, R.string.login_ws_error)
                    }
                    it.data!=null ->{
                        progressBar.visibility = View.GONE
                    }
                    else->{
                        progressBar.visibility = View.GONE
                        MessageManager.showToast(this, R.string.login_ws_ok)
                    }
                }
            }
        }
        val urlTestButton = findViewById<Button>(R.id.button_test)
        urlTestButton.setOnClickListener {
            val adressText = findViewById<TextInputEditText>(R.id.et_adress)
            val portText = findViewById<TextInputEditText>(R.id.et_port)
            loginViewModel.verifyUrl(adressText.text.toString(), portText.text.toString())
        }
        val loginButton = findViewById<Button>(R.id.button_login)
        loginButton.setOnClickListener {
            val loginText = findViewById<TextInputEditText>(R.id.et_identifier)
            val passwordText = findViewById<TextInputEditText>(R.id.et_password)
            var language = Locale.getDefault().language
            if(language.isNullOrEmpty()){
                language = "en"
            }
            loginViewModel.getLogin(loginText.text.toString(), passwordText.text.toString(), language)
        }
    }
}