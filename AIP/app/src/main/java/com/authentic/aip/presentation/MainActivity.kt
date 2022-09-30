package com.authentic.aip.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.authentic.aip.R
import com.authentic.aip.presentation.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity:AppCompatActivity() {
    private var loginViewModel : LoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        loginViewModel?.getLoginData()?.observe(this){

        }
    }
}