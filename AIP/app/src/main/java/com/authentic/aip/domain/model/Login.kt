package com.authentic.aip.domain.model

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("name")
    val name: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("user")
    val user: String
)