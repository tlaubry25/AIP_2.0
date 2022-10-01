package com.authentic.aip.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?
)