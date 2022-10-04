package com.authentic.aip.data.remote.dto.requestDetail


import com.authentic.aip.data.remote.dto.FormErrors
import com.authentic.aip.data.remote.dto.Status
import com.google.gson.annotations.SerializedName

data class RequestDetailResponseDto(
    @SerializedName("data")
    val `data`: RequestDetailDto,
    @SerializedName("form_errors")
    val formErrors: FormErrors,
    @SerializedName("status")
    val status: Status
)