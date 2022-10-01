package com.authentic.aip.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.authentic.aip.R
import com.authentic.aip.presentation.login.LoginViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity:AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)


        loginViewModel.loginLiveData.observe(this){
            Log.d("TLA", "RETOUR WEBSERVICE")
            when{
                it.isLoading->{ Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> { Log.d("TLA", "STATE ERROR") }
                it.loginObject!=null ->{ Log.d("TLA", "STATE SUCCESS") }
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