package com.authentic.aip.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.authentic.aip.R
import com.authentic.aip.presentation.login.LoginViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity:AppCompatActivity() {
    private var loginViewModel : LoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        loginViewModel?.getLoginData()?.observe(this){
            Log.d("TLA", "RETOUR WEBSERVICE")
        }

        val loginButton = findViewById<Button>(R.id.button_login)
        loginButton.setOnClickListener {
            val loginText = findViewById<TextInputEditText>(R.id.et_identifier)
            val passwordText = findViewById<TextInputEditText>(R.id.et_password)
            loginViewModel?.getLogin(loginText.text.toString(), passwordText.text.toString())
        }
    }
}