package com.authentic.aip.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.authentic.aip.R
import com.authentic.aip.common.EnumClass
import com.authentic.aip.presentation.login.LoginViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity:AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        val sharedPreferences = this?.getSharedPreferences(getString(R.string.sharedPreferenceId), Context.MODE_PRIVATE)

        loginViewModel.loginLiveData.observe(this){
            Log.d("TLA", "RETOUR WEBSERVICE")
            when{
                it.isLoading->{ Log.d("TLA", "STATE LOADING") }
                it.error.isNotEmpty() -> { Log.d("TLA", "STATE ERROR") }
                it.loginObject!=null ->{
                    Log.d("TLA", "STATE SUCCESS")

                    val uidEditor = sharedPreferences?.edit()
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