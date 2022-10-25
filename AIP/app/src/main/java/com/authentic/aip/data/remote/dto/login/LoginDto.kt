package com.authentic.aip.data.remote.dto.login


import com.authentic.aip.domain.model.Login
import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("uid")
    val uid: String?,
    @SerializedName("user")
    val user: String?
)


fun LoginDto.toLogin() = Login(
    name = name,
    uid = uid,
    user = user
)