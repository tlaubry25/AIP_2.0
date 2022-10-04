package com.authentic.aip.data.remote.dto.listRequest


import com.authentic.aip.data.remote.dto.FormErrors
import com.authentic.aip.data.remote.dto.Status
import com.google.gson.annotations.SerializedName

data class ListRequestResponseDto(
    @SerializedName("data")
    val `data`: ListRequestDto,
    @SerializedName("form_errors")
    val formErrors: FormErrors,
    @SerializedName("status")
    val status: Status
)