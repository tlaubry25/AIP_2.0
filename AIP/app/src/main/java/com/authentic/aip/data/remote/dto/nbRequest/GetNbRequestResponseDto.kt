package com.authentic.aip.data.remote.dto.nbRequest


import com.authentic.aip.data.remote.dto.FormErrors
import com.authentic.aip.data.remote.dto.Status
import com.google.gson.annotations.SerializedName

data class GetNbRequestResponseDto(
    @SerializedName("data")
    val `data`: Int,
    @SerializedName("form_errors")
    val formErrors: FormErrors,
    @SerializedName("status")
    val status: Status
)