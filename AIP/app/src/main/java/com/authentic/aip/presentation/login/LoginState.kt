package com.authentic.aip.presentation.login

import com.authentic.aip.domain.model.Login

data class LoginState(
    val isLoading : Boolean = false,
    val error : String = "",
    val loginObject : Login? = null
)