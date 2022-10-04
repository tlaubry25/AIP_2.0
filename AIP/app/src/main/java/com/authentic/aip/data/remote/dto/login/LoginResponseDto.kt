package com.authentic.aip.data.remote.dto.login


import com.authentic.aip.data.remote.dto.FormErrors
import com.authentic.aip.data.remote.dto.Status
import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("data")
    val `data`: LoginDto,
    @SerializedName("form_errors")
    val formErrors: FormErrors,
    @SerializedName("status")
    val status: Status
)