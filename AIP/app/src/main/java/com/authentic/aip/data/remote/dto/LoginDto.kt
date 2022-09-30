package com.authentic.aip.data.remote.dto

import com.authentic.aip.domain.model.Login

data class LoginDto(
    val name: String,
    val uid: String,
    val user: String
)

fun LoginDto.toLogin() = Login(
    name = name,
    uid = uid,
    user = user
)
