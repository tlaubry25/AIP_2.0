package com.authentic.aip.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("data")
    val `data`: LoginDto,
    @SerializedName("form_errors")
    val formErrors: FormErrors,
    @SerializedName("status")
    val status: Status
)