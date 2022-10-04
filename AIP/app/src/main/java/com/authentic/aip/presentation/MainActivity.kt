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

@AndroidEntryPoint
class MainActivity:AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        this.supportActionBar?.hide()
        loginViewModel.loginLiveData.observe(this){
            Log.d("TLA", "RETOUR WEBSERVICE")
            val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
            when{
                it.isLoading->{
                    progressBar.visibility = View.VISIBLE
                    Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> {
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE ERROR") }
                it.loginObject!=null ->{
                    progressBar.visibility = View.GONE
                    Log.d("TLA", "STATE SUCCESS")

                    val uidEditor = prefs?.preferences?.edit()
                    uidEditor?.putString(EnumClass.PreferencesEnum.SESSION_ID.toString(), it.loginObject.uid)
                    uidEditor?.commit()

                    val toMenuActivity = Intent(this, MenuActivity::class.java)
                    startActivity(toMenuActivity)
                }
            }
        }

        val loginButton = findViewById<Button>(R.id.button_login)
        loginButton.setOnClickListener {
            val loginText = findViewById<TextInputEditText>(R.id.et_identifier)
            val passwordText = findViewById<TextInputEditText>(R.id.et_password)
            loginViewModel.getLogin(loginText.text.toString(), passwordText.text.toString())
        }
    }
}