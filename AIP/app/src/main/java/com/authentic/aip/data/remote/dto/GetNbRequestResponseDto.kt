package com.authentic.aip.data.remote.dto


import com.google.gson.annotations.SerializedName

data class GetNbRequestResponseDto(
    @SerializedName("data")
    val `data`: Int,
    @SerializedName("form_errors")
    val formErrors: FormErrors,
    @SerializedName("status")
    val status: Status
)